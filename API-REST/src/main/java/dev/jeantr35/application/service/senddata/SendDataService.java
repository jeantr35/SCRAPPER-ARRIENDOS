package dev.jeantr35.application.service.senddata;

import com.itextpdf.text.DocumentException;
import dev.jeantr35.domain.dto.SendDataDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;

@ApplicationScoped
public class SendDataService {

    @Inject
    public CreateHtmlUtil createHtmlUtil;

    public Response sendDataTo(SendDataDTO sendDataDTO) throws DocumentException, IOException {
        CreatePDFUtil.generatePDFFromHTML("src/main/resources/templates/CreateHtmlUtil/hello.html");
        return Response.ok(createHtmlUtil.getHtmlRendered(sendDataDTO)).build();
    }

}
