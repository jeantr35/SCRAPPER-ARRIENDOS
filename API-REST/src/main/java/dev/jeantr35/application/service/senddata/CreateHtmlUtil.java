package dev.jeantr35.application.service.senddata;

import dev.jeantr35.domain.dto.SendDataDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateHtmlUtil {

    @CheckedTemplate
    private static class Templates {
        public static native TemplateInstance hello(SendDataDTO sendDataDTO);
    }

    public String getHtmlRendered(SendDataDTO sendDataDTO){
        return Templates.hello(sendDataDTO).render();
    }

}
