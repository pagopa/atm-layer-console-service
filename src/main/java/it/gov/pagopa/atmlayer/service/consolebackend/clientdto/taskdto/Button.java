package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

@Getter
@Setter
@Schema(description = "Oggetto bottone")
public class Button {

    @Schema(description = "Id del bottone")
    private String id;

    @Schema(description = "Mappa delle variabili del bottone")
    private Map<String, Object> data;
}
