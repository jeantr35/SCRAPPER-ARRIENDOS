package dev.jeantr35.aplication.usecase;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.mongodb.client.MongoCollection;
import dev.jeantr35.aplication.usecase.webscrap.ScrapApartmentInfoUseCase;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.models.ApartmentInfo;
import dev.jeantr35.infrastructure.repositories.ApartmentInfoRepository;

import java.nio.file.Paths;

public class WebScrapper {

    private static final MongoCollection<ApartmentInfo> apartmentInfoCollection = ApartmentInfoRepository.instanciate();
    private static final String BASE_URL = "https://www.metrocuadrado.com";
    private static final String SEARCH_PATH = "/apartamento/arriendo/";
    public static final String SELECTOR_URL_APARTMENT = "//div[@class='card-header']/a";
    public static final String SELECTOR_URL_NEXT_PAGE = "(//a[contains(@aria-label,\"Next\")])[1]";
    public static final String SELECTOR_URL_NEXT_PAGE_DISABLED = "//li[@class=\"item-icon-next page-item disabled\"]";
    public static final String PLACEHOLDER_INPUT_MIN_PRICE = "Desde";
    public static final String PLACEHOLDER_INPUT_MAX_PRICE = "Hasta";
    public static final String TEXT_SEARCH_BUTTON = "Filtrar precios";

    public static void getInfoFrom(CityToScrapDto cityToScrapDto){
        //TODO: Hacer que solo scrapee entre los rangos que no han sido scrapeados ese día
        //TODO: Guardar los comandos con su fecha y estado para no repetir datos
        //TODO: Guardar el con el/los id(s) del otro comando que debe esperar para completar su rango
        //TODO: Enviar notificaciones cuando se termine de scrapear un comando -> Para el MS-DATA -> Para los comandos que deben esperar para completar el rango
        try (Playwright playwright = Playwright.create()) {
            int minPrice = ApartmentInfoRepository.getMinPrice(apartmentInfoCollection, cityToScrapDto.getCity());
            int maxPrice = ApartmentInfoRepository.getMaxPrice(apartmentInfoCollection, cityToScrapDto.getCity());
            Browser browser = playwright.webkit().launch();
            Page page = browser.newPage();
            page.navigate(BASE_URL + SEARCH_PATH + cityToScrapDto.getCity());
            page.getByPlaceholder(PLACEHOLDER_INPUT_MIN_PRICE).fill(String.valueOf(cityToScrapDto.getMinPrice()));
            page.getByPlaceholder(PLACEHOLDER_INPUT_MAX_PRICE).fill(String.valueOf(cityToScrapDto.getMaxPrice()));
            page.getByText(TEXT_SEARCH_BUTTON).click();
            Thread.sleep(2000);
            scrapEachPage(cityToScrapDto, browser, page);
            page.close();
            browser.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void scrapEachPage(CityToScrapDto cityToScrapDto, Browser browser, Page page) throws InterruptedException {
        do {
            Locator locator = page.locator(SELECTOR_URL_APARTMENT);
            int numberOfItems = locator.count();
            scrapDataFromPage(cityToScrapDto, browser, numberOfItems, locator);
            if (!page.locator(SELECTOR_URL_NEXT_PAGE_DISABLED).isVisible()){
                page.locator(SELECTOR_URL_NEXT_PAGE).click();
            }
            Thread.sleep(2000);
        }
        while (!page.locator(SELECTOR_URL_NEXT_PAGE_DISABLED).isVisible());
    }

    private static void scrapDataFromPage(CityToScrapDto cityToScrapDto, Browser browser, int numberOfItems, Locator locator) {
        for (int i = 0; i < numberOfItems; i++) {
            String apartmentPath = BASE_URL + locator.nth(i).getAttribute("href");
            ApartmentInfo apartmentInfo = ScrapApartmentInfoUseCase.execute(browser.newPage(), apartmentPath);
            if (apartmentInfo == null)
                continue;
            apartmentInfo.setCity(cityToScrapDto.getCity());
            ApartmentInfoRepository.saveApartment(apartmentInfoCollection, apartmentInfo);
            System.out.println("Apartment number: " + i);
            System.out.println(apartmentInfo);
        }
    }

}
