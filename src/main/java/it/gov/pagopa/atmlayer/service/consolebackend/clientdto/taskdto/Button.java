package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

@Getter
@Setter
@Schema(description = "Oggetto bottone")
public class Button {

    @Schema(description = "Id del bottone", format = "byte", maxLength = 255)
    private String id;

    @Schema(description = "Mappa delle variabili del bottone", maxProperties = 100)
    private Map<String, Object> data;
}
