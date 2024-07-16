package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Oggetto che rappresenta lo stato di una transazione")
@RegisterForReflection
public class State {

    @NotNull(message = "Device non può essere null")
    @Schema(required = true)
    private Device device;

    @Schema(description = "ID del task che da completato", maxLength = 255)
    private String taskId;

    @Schema(description = "Mappa delle variabili inviate dal Device", maxProperties = 100)
    private Map<String, Object> data;

    @Schema(hidden = true)
    private String transactionId;


    @Schema(description = "Codice Fiscale dell'utente (dato sensibile)", maxLength = 16)
    private String fiscalCode;

    @Schema(description = "Informazioni del pan (dato sensibile)", type = SchemaType.ARRAY, maxItems = 2)
    private List<PanInfo> panInfo;

}
