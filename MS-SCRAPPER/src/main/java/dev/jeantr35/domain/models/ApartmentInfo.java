package dev.jeantr35.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentInfo {

    private String sector;
    private String url;
    private String city;
    private int stratum;
    private int price;
    private float area;
    private int bedrooms;
    private int toilets;

}
