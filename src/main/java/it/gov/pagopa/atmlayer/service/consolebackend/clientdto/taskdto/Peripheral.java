package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.PeripheralStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Oggetto che rappresenta una periferica del Device e il suo stato")
public class Peripheral {

    @Schema(required = true, description = "Label che identifica una specifica periferica", example = "PRINTER", maxLength = 255)
    private String id;

    @Schema(description = "Nome testuale della periferica", example = "Receipt printer", maxLength = 255)
    private String name;

    @Schema(required = true, description = "Stato della periferica", implementation = PeripheralStatus.class)
    private PeripheralStatus status;
}
