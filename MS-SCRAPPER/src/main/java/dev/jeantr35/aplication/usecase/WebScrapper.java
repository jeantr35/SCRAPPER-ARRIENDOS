package dev.jeantr35.aplication.usecase;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import dev.jeantr35.aplication.usecase.webscrap.ScrapApartmentInfoUseCase;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.enums.ScrappingStatus;
import dev.jeantr35.domain.models.ApartmentInfo;
import dev.jeantr35.domain.models.CityToScrapCommand;
import dev.jeantr35.infrastructure.repositories.CityToSrapCommandRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WebScrapper {


    @Inject
    CityToSrapCommandRepository cityToSrapCommandRepository;

    private static final String BASE_URL = "https://www.metrocuadrado.com";
    private static final String SEARCH_PATH = "/apartamento/arriendo/";
    public static final String SELECTOR_URL_APARTMENT = "//div[@class='card-header']/a";
    public static final String SELECTOR_URL_NEXT_PAGE = "(//a[contains(@aria-label,\"Next\")])[1]";
    public static final String SELECTOR_URL_NEXT_PAGE_DISABLED = "//li[@class=\"item-icon-next page-item disabled\"]";
    public static final String PLACEHOLDER_INPUT_MIN_PRICE = "Desde";
    public static final String PLACEHOLDER_INPUT_MAX_PRICE = "Hasta";
    public static final String TEXT_SEARCH_BUTTON = "Filtrar precios";

    public void getInfoFrom(CityToScrapDto cityToScrapDto){
        CityToScrapCommand cityToScrapCommand = new CityToScrapCommand(cityToScrapDto);
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.webkit().launch();
            Page page = browser.newPage();
            page.navigate(BASE_URL + SEARCH_PATH + cityToScrapDto.getCity());
            page.getByPlaceholder(PLACEHOLDER_INPUT_MIN_PRICE).fill(String.valueOf(cityToScrapDto.getMinPrice()));
            page.getByPlaceholder(PLACEHOLDER_INPUT_MAX_PRICE).fill(String.valueOf(cityToScrapDto.getMaxPrice()));
            page.getByText(TEXT_SEARCH_BUTTON).click();
            Thread.sleep(2000);
            scrapEachPage(cityToScrapDto, browser, page, cityToScrapCommand);
            page.close();
            browser.close();
            cityToScrapCommand.setStatus(ScrappingStatus.COMPLETED);
            cityToSrapCommandRepository.persistOrUpdate(cityToScrapCommand);
        } catch (InterruptedException e) {
            cityToSrapCommandRepository.persistOrUpdate(cityToScrapCommand);
            cityToScrapCommand.setStatus(ScrappingStatus.FAILED);
            throw new RuntimeException(e);
        }
    }

    private void scrapEachPage(CityToScrapDto cityToScrapDto, Browser browser, Page page, CityToScrapCommand cityToScrapCommand) throws InterruptedException {
        do {
            Locator locator = page.locator(SELECTOR_URL_APARTMENT);
            int numberOfItems = locator.count();
            scrapDataFromPage(cityToScrapDto, browser, numberOfItems, locator, cityToScrapCommand);
            if (!page.locator(SELECTOR_URL_NEXT_PAGE_DISABLED).isVisible()){
                page.locator(SELECTOR_URL_NEXT_PAGE).click();
            }
            cityToSrapCommandRepository.persistOrUpdate(cityToScrapCommand);
            Thread.sleep(2000);
        }
        while (!page.locator(SELECTOR_URL_NEXT_PAGE_DISABLED).isVisible());
    }

    private void scrapDataFromPage(CityToScrapDto cityToScrapDto, Browser browser, int numberOfItems, Locator locator, CityToScrapCommand cityToScrapCommand) {
        for (int i = 0; i < numberOfItems; i++) {
            String apartmentPath = BASE_URL + locator.nth(i).getAttribute("href");
            ApartmentInfo apartmentInfo = ScrapApartmentInfoUseCase.execute(browser.newPage(), apartmentPath);
            if (apartmentInfo == null)
                continue;
            apartmentInfo.setCity(cityToScrapDto.getCity());
            cityToScrapCommand.addApartmentInfo(apartmentInfo);
        }
    }

}
