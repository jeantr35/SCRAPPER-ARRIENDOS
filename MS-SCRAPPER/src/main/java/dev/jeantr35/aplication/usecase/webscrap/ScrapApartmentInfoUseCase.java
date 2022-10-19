package dev.jeantr35.aplication.usecase.webscrap;

import com.microsoft.playwright.Page;
import dev.jeantr35.domain.models.ApartmentInfo;

import java.util.HashMap;
import java.util.Map;

public class ScrapApartmentInfoUseCase {

    private static final String XPATH_TO_APARTMENT_SIZE = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[1]//h2";
    private static final String XPATH_TO_APARTMENT_BEDROOMS = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[2]//h2";
    private static final String XPATH_TO_APARTMENT_TOILETS = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[3]//h2";
    private static final String XPATH_TO_APARTMENT_STRATUM = "//div[@class=\"d-none d-lg-block card-block\"]/div/div/ul/li[4]//h2";
    private static final String XPATH_TO_APARTMENT_PRICE = "//h3[contains(text(),\"Valor arriendo\")]/parent::*/p";
    private static final String XPATH_TO_APARTMENT_ADMINISTRATION_PRICE = "//h3[contains(text(),\"administración\")]/parent::*/p";
    private static final String XPATH_TO_APARTMENT_SECTOR = "(//h1[contains(text(),\" Arriendo,\")])[1]";
    private static final String AREA = "Area";
    private static final String BEDROOMS = "Bedrooms";
    private static final String TOILETS = "Toilets";
    private static final String STRATUM = "Stratum";
    private static final String PRICE = "Price";
    private static final String ADMINISTRATION_PRICE = "Administration Price";
    public static final String URL = "URL";
    public static final String SECTOR = "Apartment Sector";
    public static final String STRING_TO_CLEAN = " Arriendo, ";

    public static ApartmentInfo execute(Page page, String apartmentUrl){
        try {
            page.navigate(apartmentUrl);
            String apartmentSize = page.locator(XPATH_TO_APARTMENT_SIZE).innerText(); // Área construida\n 79.4 m²
            String apartmentBedrooms = page.locator(XPATH_TO_APARTMENT_BEDROOMS).innerText(); //2\nHabitaciones
            String apartmentToilets = page.locator(XPATH_TO_APARTMENT_TOILETS).innerText();
            String apartmentStratum = page.locator(XPATH_TO_APARTMENT_STRATUM).innerText();
            String apartmentPrice = page.locator(XPATH_TO_APARTMENT_PRICE).first().innerText(); // $1.700.000
            String apartmentSector = page.locator(XPATH_TO_APARTMENT_SECTOR).innerText();
            String apartmentAdministrationPrice = null;
            if (page.locator(XPATH_TO_APARTMENT_ADMINISTRATION_PRICE).isVisible()){
                apartmentAdministrationPrice = page.locator(XPATH_TO_APARTMENT_ADMINISTRATION_PRICE).innerText(); // $400.000 o 1 o 0
            }
            Map<String, String> cleanedData = cleanApartmentInfo(apartmentSize, apartmentBedrooms, apartmentToilets, apartmentStratum,
                    apartmentPrice, apartmentAdministrationPrice, apartmentSector);
            cleanedData.put(URL, apartmentUrl);
            return createApartmentInfo(cleanedData);
        }
        catch (Exception e){
            return null;
        }
        finally {
            page.close();
        }
    }

    private static Map<String, String> cleanApartmentInfo(String apartmentSize, String apartmentBedrooms, String apartmentToilets,
                                                          String apartmentStratum, String apartmentPrice, String apartmentAdministrationPrice,String apartmentSector){

        Map<String, String> results = new HashMap<>();

        String newApartmentSize = apartmentSize.substring(apartmentSize.indexOf("\n") + 1);
        newApartmentSize = newApartmentSize.split(" m")[0];

        results.put(AREA, newApartmentSize);
        results.put(BEDROOMS, cleanNonFloatNonPriceData(apartmentBedrooms));
        results.put(TOILETS, cleanNonFloatNonPriceData(apartmentToilets));
        results.put(STRATUM, cleanNonFloatNonPriceData(apartmentStratum));
        results.put(PRICE, cleanPrices(apartmentPrice, false));
        results.put(ADMINISTRATION_PRICE, cleanPrices(apartmentAdministrationPrice, true));
        results.put(SECTOR, cleanSector(apartmentSector));
        return results;
    }

    private static String cleanPrices(String price, Boolean checkNull){
        if (checkNull && price == null){
            return "0";
        }
        return price.replace("$","").replace(".","");
    }

    private static String cleanNonFloatNonPriceData(String data){
        return data.split("\n")[0];
    }

    private static String cleanSector(String data){
        return data.split(STRING_TO_CLEAN)[1];
    }
    private static ApartmentInfo createApartmentInfo(Map<String, String> data){
        ApartmentInfo apartmentInfo = new ApartmentInfo();
        apartmentInfo.setArea(Float.parseFloat(data.get(AREA)));
        apartmentInfo.setBedrooms(Integer.parseInt(data.get(BEDROOMS)));
        apartmentInfo.setToilets(Integer.parseInt(data.get(TOILETS)));
        apartmentInfo.setStratum(Integer.parseInt(data.get(STRATUM)));
        apartmentInfo.setPrice(Integer.parseInt(data.get(PRICE)));
        apartmentInfo.setAdministrationPrice(Integer.parseInt(data.get(ADMINISTRATION_PRICE)));
        apartmentInfo.setUrl(data.get(URL));
        apartmentInfo.setSector(data.get(SECTOR));
        return apartmentInfo;
    }

}
