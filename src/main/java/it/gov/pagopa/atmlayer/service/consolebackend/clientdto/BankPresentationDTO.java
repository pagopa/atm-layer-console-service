package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.QuotaPeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankPresentationDTO {
    @Schema(format = "byte", maxLength = 255)
    private String acquirerId;
    @Schema(format = "byte", maxLength = 255)
    private String denomination;
    @Schema(format = "byte", maxLength = 255)
    private String clientId;
    @Schema(format = "byte", maxLength = 255)
    private String clientSecret;
    @Schema(format = "byte", maxLength = 255)
    private String apiKeyId;
    @Schema(format = "byte", maxLength = 255)
    private String apiKeySecret;
    @Schema(format = "byte", maxLength = 255)
    private String usagePlanId;
    @Schema(minimum = "1", maximum = "10000")
    private Integer limit;
    private QuotaPeriodType period;
    @Schema(minimum = "1", maximum = "10000")
    private Integer burstLimit;
    private Double rateLimit;
    @Schema(description = "Creation Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "date-time", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;

}