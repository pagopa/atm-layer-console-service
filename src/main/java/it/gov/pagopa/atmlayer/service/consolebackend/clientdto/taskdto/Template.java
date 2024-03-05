package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RegisterForReflection
@Schema(description = "Oggetto che rappresenta il template da visualizzare")
public class Template {

    @JsonView
//            (ObscureView.class)
    @Schema(description = "Rappresenta il Base64 della pagina HTML da visualizzare")
    private String content;

    @Schema(description = "Tipo di visualizzazione della schermata HTML", example = "FULL_SCREEN")
    private String type;

}
