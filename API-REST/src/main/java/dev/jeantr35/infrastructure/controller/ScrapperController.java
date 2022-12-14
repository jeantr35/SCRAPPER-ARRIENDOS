package dev.jeantr35.infrastructure.controller;

import dev.jeantr35.application.service.ApartmentsInfoService;
import dev.jeantr35.domain.dto.CityToScrapDto;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Path("/scrap")
public class ScrapperController {

    @Inject
    public ApartmentsInfoService apartmentsInfoService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response scrapCity(CityToScrapDto cityToScrapDto) throws IOException, TimeoutException { //TODO: Add body input validations
        return apartmentsInfoService.sendNewCityToScrap(cityToScrapDto);
    }
}