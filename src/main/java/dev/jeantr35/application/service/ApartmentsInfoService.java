package dev.jeantr35.application.service;

import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.infrastructure.utils.WebScrapper;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class ApartmentsInfoService {

    public Response sendNewCityToScrap(CityToScrapDto cityToScrapDto){
        WebScrapper.getInfoFrom(cityToScrapDto);
        return Response.ok().build();
    }

}
