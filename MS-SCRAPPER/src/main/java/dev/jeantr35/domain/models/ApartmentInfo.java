package dev.jeantr35.domain.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity
public class ApartmentInfo {

    private String sector;
    private String url;
    private String city;
    private int stratum;
    private int price;
    private int administrationPrice;
    private float area;
    private int bedrooms;
    private int toilets;

    @Override
    public String toString() {
        return "ApartmentInfo{" +
                "sector='" + sector + '\'' +
                ", url='" + url + '\'' +
                ", city='" + city + '\'' +
                ", stratum=" + stratum +
                ", price=" + price +
                ", administrationPrice=" + administrationPrice +
                ", area=" + area +
                ", bedrooms=" + bedrooms +
                ", toilets=" + toilets +
                '}';
    }
}
