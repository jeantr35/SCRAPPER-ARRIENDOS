package dev.jeantr35.aplication.usecase;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import dev.jeantr35.aplication.usecase.webscrap.ScrapApartmentInfoUseCase;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.models.ApartmentInfo;

public class WebScrapper {

    private static final String BASE_URL = "https://www.metrocuadrado.com";
    private static final String SEARCH_PATH = "/apartamento/arriendo/";
    public static final String SELECTOR_URL_APARTMENT = "//div[@class='card-header']/a";
    public static final String SELECTOR_URL_NEXT_PAGE = "//a[contains(@aria-label,\"Next\")]"; //No funciona
    //TODO: Encontrar el <a> y evaluar si está habilitado

    public static void getInfoFrom(CityToScrapDto cityToScrapDto){
        try (Playwright playwright = Playwright.create()) { //TODO: Hacer que el scrapper ande por todas las paginas
            Browser browser = playwright.webkit().launch();
            Page page = browser.newPage();
            page.navigate(BASE_URL + SEARCH_PATH + cityToScrapDto.getCity());
            Locator locator = page.locator(SELECTOR_URL_APARTMENT);
            int numberOfItems = locator.count();
            for (int i = 0; i < numberOfItems; i++) {
                String apartmentPath = BASE_URL + locator.nth(i).getAttribute("href");
                ApartmentInfo apartmentInfo = ScrapApartmentInfoUseCase.execute(browser.newPage(), apartmentPath);
                System.out.println(i);
            }
            page.close();
            browser.close();
        }
    }

}
