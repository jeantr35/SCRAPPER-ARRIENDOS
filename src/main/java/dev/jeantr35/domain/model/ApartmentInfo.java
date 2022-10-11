package dev.jeantr35.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentInfo {

    private String sector;
    private String url;
    private int stratum;
    private int price;
    private int area;
    private int bedrooms;
    private int toilets;

}
