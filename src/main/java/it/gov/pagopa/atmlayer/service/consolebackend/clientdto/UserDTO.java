package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
public class UserDTO {
    @Schema(format = "byte", maxLength = 255)
    private String userId;
    @Schema(format = "byte", maxLength = 255)
    private String name;
    @Schema(format = "byte", maxLength = 255)
    private String surname;
    @Schema(type = SchemaType.ARRAY, maxItems = 30)
    private List<ProfileDTO> profiles;
    @Schema(description = "Creation Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;
}
