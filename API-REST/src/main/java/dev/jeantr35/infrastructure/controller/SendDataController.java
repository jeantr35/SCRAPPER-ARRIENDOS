package dev.jeantr35.infrastructure.controller;

import com.itextpdf.text.DocumentException;
import dev.jeantr35.application.service.senddata.SendDataService;
import dev.jeantr35.domain.dto.SendDataDTO;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/send-data-to")
public class SendDataController {

    @Inject
    public SendDataService sendDataService;

    @POST
    public Response sendDataTo(SendDataDTO sendDataDTO) throws DocumentException, IOException {
        return sendDataService.sendDataTo(sendDataDTO);
    }

}
