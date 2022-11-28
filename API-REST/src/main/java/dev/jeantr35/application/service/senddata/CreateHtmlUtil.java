package dev.jeantr35.application.service.senddata;

import dev.jeantr35.domain.dto.SendDataDTO;
import dev.jeantr35.domain.models.ApartmentInfo;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CreateHtmlUtil {

    @CheckedTemplate
    private static class Templates {
        public static native TemplateInstance mainHtml(String subject, String to, List<ApartmentInfo> apartmentInfoList);
    }

    public String getHtmlRendered(SendDataDTO sendDataDTO){
        return Templates.mainHtml(sendDataDTO.getSubject(), sendDataDTO.getTo(), sendDataDTO.getApartmentInfoList()).render();
    }

    private String renderEveryOption(List<ApartmentInfo> apartmentInfoList){

        return "";
    }

}
