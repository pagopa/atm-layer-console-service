package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.runtime.annotations.RegisterForReflection;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.OutcomeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Data
@AllArgsConstructor
@RegisterForReflection
public class OutcomeResponse {

    @Schema(required = true, description = "Risultato dell'operazione")
    private String result;

    @Schema(required = true, description = "Descrizione dell'esito dell'operazione")
    private String description;

    @JsonIgnore
    private OutcomeEnum outcomeEnum;
}
