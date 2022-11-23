package dev.jeantr35.infrastructure.controller;

import dev.jeantr35.application.service.SortService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/sort")
public class SortController {

    @Inject
    public SortService sortService;

    @GET
    @Path("/{id}")
    public Response getSortedValues(@PathParam("id") String id){
        return sortService.executeSort(id);
    }

}
