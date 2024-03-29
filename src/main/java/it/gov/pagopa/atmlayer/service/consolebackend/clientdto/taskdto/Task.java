package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.Command;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.EppMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Oggetto che rappresenta un task")
public class Task {

    @Schema(required = true, description = "Identificativo del Task")
    private String id;

    @Schema(description = "Mappa delle variabili generiche")
    private Map<String, Object> data;

    @Schema(description = "Mappa delle variabili da consultare in caso di errore")
    private Map<String, Object> onError;

    @Schema(description = "Valore di durata prima di andare in timeout")
    private int timeout;

    @Schema(description = "Mappa delle variabili da consultare in caso di timeout")
    private Map<String, Object> onTimeout;

    @Schema(description = "Template html")
    private Template template;

    @Schema(description = "Comando da eseguire")
    private Command command;

    @Schema(description = "Template dello scontrino")
    private String receiptTemplate;

    @Schema(description = "Nome della variabile in cui il Device setterà l'esito del Command")
    private String outcomeVarName;

    @Schema(description = "Modalità dell'epp")
    private EppMode eppMode;

    @Schema(description = "Lista dei bottoni")
    private List<Button> buttons;
}
