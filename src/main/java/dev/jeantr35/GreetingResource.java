package dev.jeantr35;

import dev.jeantr35.application.service.ApartmentsInfoService;
import dev.jeantr35.domain.dto.CityToScrapDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @Inject
    public ApartmentsInfoService apartmentsInfoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(CityToScrapDto cityToScrapDto) {
        return apartmentsInfoService.sendNewCityToScrap(cityToScrapDto);
    }
}