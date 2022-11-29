package dev.jeantr35.infrastructure.controller;

import dev.jeantr35.application.service.SortService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/sort-and-notify")
public class SortController {

    @Inject
    public SortService sortService;

    @POST
    @Path("/{id}")
    public Response sortAndNotify(@PathParam("id") String id){
        return sortService.executeSortAndThenNotify(id);
    }

}
