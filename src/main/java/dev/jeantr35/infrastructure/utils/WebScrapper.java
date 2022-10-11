package dev.jeantr35.infrastructure.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import dev.jeantr35.domain.dto.CityToScrapDto;

import java.nio.file.Paths;

public class WebScrapper {

    private static final String BASE_URL = "https://www.metrocuadrado.com";
    private static final String SEARCH_PATH = "/apartamento/arriendo/";

    public static void getInfoFrom(CityToScrapDto cityToScrapDto){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.webkit().launch();
            Page page = browser.newPage();
            page.navigate(BASE_URL + SEARCH_PATH + cityToScrapDto.getCity());
            Locator locator = page.locator("//div[@class='card-header']/a");
            int numberOfItems = locator.count();
            for (int i = 0; i < numberOfItems; i++) {
                Page apartmentPage = browser.newPage();
                String apartmentPath = locator.nth(i).getAttribute("href");
                apartmentPage.navigate(BASE_URL + apartmentPath);
                apartmentPage.close();
                System.out.println(i);
            }
            page.close();
            browser.close();
        }
    }

}
