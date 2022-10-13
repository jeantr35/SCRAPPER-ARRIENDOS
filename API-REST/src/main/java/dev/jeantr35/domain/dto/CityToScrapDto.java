package dev.jeantr35.domain.dto;

import dev.jeantr35.domain.enums.WebSitesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityToScrapDto {

    private String city;
    private String emailToNotify;
    private WebSitesEnum website;

}
