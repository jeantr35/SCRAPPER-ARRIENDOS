package dev.jeantr35.aplication.usecase;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import dev.jeantr35.aplication.usecase.webscrap.ScrapApartmentInfoUseCase;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.models.ApartmentInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WebScrapper {

    private static final String BASE_URL = "https://www.metrocuadrado.com";
    private static final String SEARCH_PATH = "/apartamento/arriendo/";
    public static final String SELECTOR_URL_APARTMENT = "//div[@class='card-header']/a";
    public static final String SELECTOR_URL_NEXT_PAGE = "(//a[contains(@aria-label,\"Next\")])[1]";
    public static final String SELECTOR_URL_NEXT_PAGE_DISABLED = "//li[@class=\"item-icon-next page-item disabled\"]";

    public static void getInfoFrom(CityToScrapDto cityToScrapDto){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.webkit().launch();
            Page page = browser.newPage();
            page.navigate(BASE_URL + SEARCH_PATH + cityToScrapDto.getCity());
            while (!page.locator(SELECTOR_URL_NEXT_PAGE_DISABLED).isVisible()){
                Locator locator = page.locator(SELECTOR_URL_APARTMENT);
                int numberOfItems = locator.count();
                scrapDataFromPage(cityToScrapDto, browser, numberOfItems, locator);
                page.locator(SELECTOR_URL_NEXT_PAGE).click();
                Thread.sleep(2000);
            }
            page.close();
            browser.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void scrapDataFromPage(CityToScrapDto cityToScrapDto, Browser browser, int numberOfItems, Locator locator) {
        for (int i = 0; i < numberOfItems; i++) {
            String apartmentPath = BASE_URL + locator.nth(i).getAttribute("href");
            ApartmentInfo apartmentInfo = ScrapApartmentInfoUseCase.execute(browser.newPage(), apartmentPath);
            if (apartmentInfo == null)
                continue;
            apartmentInfo.setCity(cityToScrapDto.getCity());
            System.out.println("Apartment number: " + i);
            System.out.println(apartmentInfo);
        }
    }

}
