package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Data
public class UserProfilesDTO {
    @Schema(format = "byte", maxLength = 255)
    private String userId;
    @Schema(example = "5", minimum = "1", maximum = "30")
    private int profileId;
    @Schema(description = "Creation Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;
}
