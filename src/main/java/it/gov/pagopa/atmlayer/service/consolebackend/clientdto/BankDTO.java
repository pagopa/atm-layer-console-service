package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BankDTO {
    @Schema(format = "byte", maxLength = 255)
    private String acquirerId;
    @Schema(format = "byte", maxLength = 255)
    private String denomination;
    @Schema(format = "byte", maxLength = 255)
    private String clientId;
    @Schema(format = "byte", maxLength = 255)
    private String apiKeyId;
    @Schema(format = "byte", maxLength = 255)
    private String usagePlanId;
    @Schema(description = "Creation Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;
}
