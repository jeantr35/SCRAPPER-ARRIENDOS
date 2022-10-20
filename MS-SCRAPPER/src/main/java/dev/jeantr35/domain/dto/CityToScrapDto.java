package dev.jeantr35.domain.dto;

import dev.jeantr35.domain.enums.WebSitesEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CityToScrapDto {

    private String city;
    private String emailToNotify;
    private WebSitesEnum website;

}
