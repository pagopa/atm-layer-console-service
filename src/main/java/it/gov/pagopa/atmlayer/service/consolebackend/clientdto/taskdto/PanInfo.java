package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.taskdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Oggetto che rappresenta le informazioni del pan")
@RegisterForReflection
public class PanInfo {

    @ToString.Exclude
    @Schema(description = "Pan (dato sensibile)", maxLength = 19)
    private String pan;

    @Schema(description = "Circuito del pan", type= SchemaType.ARRAY, maxItems = 2)
    private List<String> circuits;

    @Schema(description = "Nome della banca associata al pan", maxLength = 255)
    private String bankName;

}
