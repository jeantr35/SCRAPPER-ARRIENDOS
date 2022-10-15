package dev.jeantr35.aplication.usecase.webscrap;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import dev.jeantr35.domain.models.ApartmentInfo;

import java.util.HashMap;
import java.util.Map;

public class ScrapApartmentInfoUseCase {

    private static final String XPATH_TO_APARTMENT_SIZE = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[1]//h2";
    private static final String XPATH_TO_APARTMENT_BEDROOMS = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[2]//h2";
    private static final String XPATH_TO_APARTMENT_TOILETS = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[3]//h2";
    private static final String XPATH_TO_APARTMENT_STRATUM = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[4]//h2";
    private static final String XPATH_TO_APARTMENT_PRICE = "//*[@id=\"__next\"]/div/div/div/div[2]/div[3]/div[2]/div/div[2]/div/h3";
    private static final String XPATH_TO_APARTMENT_ADMINISTRATION_PRICE = "//*[@id=\"__next\"]/div/div/div/div[2]/div[4]/div[1]/div[8]/div/div[7]/p";
    public static final String AREA = "Area";
    public static final String BEDROOMS = "Bedrooms";
    public static final String TOILETS = "Toilets";
    public static final String STRATUM = "Stratum";

    public static ApartmentInfo execute(Page page, String apartmentUrl){
        page.navigate(apartmentUrl);
        String apartmentSize = page.locator(XPATH_TO_APARTMENT_SIZE).innerText(); // Área construida\n 79.4 m²
        String apartmentBedrooms = page.locator(XPATH_TO_APARTMENT_BEDROOMS).innerText(); //2\nHabitaciones
        String apartmentToilets = page.locator(XPATH_TO_APARTMENT_TOILETS).innerText();
        String apartmentStratum = page.locator(XPATH_TO_APARTMENT_STRATUM).innerText();
        String apartmentPrice = page.locator(XPATH_TO_APARTMENT_PRICE).innerText(); // $1.700.000
        String apartmentAdministrationPrice = page.locator(XPATH_TO_APARTMENT_ADMINISTRATION_PRICE).innerText(); // $400.000 o 1 o 0
        Map<String, String> cleanedData = cleanApartmentInfo(apartmentSize, apartmentBedrooms, apartmentToilets, apartmentStratum,
                                                             apartmentPrice, apartmentAdministrationPrice);

        //TODO: Cambiar el xpath de administración por texto -> ir al padre -> entrar al precio
        //TODO: Añadir el xpath de parqueaderos por texto -> ir al padre -> entrar al precio
        page.close();
        return new ApartmentInfo();
    }

    private static Map<String, String> cleanApartmentInfo(String apartmentSize, String apartmentBedrooms, String apartmentToilets,
                                                           String apartmentStratum, String apartmentPrice, String apartmentAdministrationPrice){

        Map<String, String> results = new HashMap<>();

        String newApartmentSize = apartmentSize.substring(apartmentSize.indexOf("\n") + 1);
        newApartmentSize = newApartmentSize.split(" m")[0];

        results.put(AREA, newApartmentSize);
        results.put(BEDROOMS, cleanNonFloatNonPriceData(apartmentBedrooms));
        results.put(TOILETS, cleanNonFloatNonPriceData(apartmentToilets));
        results.put(STRATUM, cleanNonFloatNonPriceData(apartmentStratum));
        //TODO: Completar la limpieza de los datos para el costo y la administracion
        return results;

    }

    private static String cleanNonFloatNonPriceData(String data){
        return data.split("\n")[0];
    }

    private static ApartmentInfo createApartmentInfo(Map<String, String> data){
        ApartmentInfo apartmentInfo = new ApartmentInfo();
        return apartmentInfo; //TODO: Completar este metodo
    }

}
