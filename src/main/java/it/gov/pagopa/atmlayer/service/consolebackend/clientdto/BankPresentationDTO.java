package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.QuotaPeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankPresentationDTO {

    private String acquirerId;

    private String denomination;

    private String clientId;

    private String clientSecret;

    private String apiKeyId;

    private String apiKeySecret;

    private String usagePlanId;

    private Integer limit;

    private QuotaPeriodType period;

    private Integer burstLimit;

    private Double rateLimit;

    private Timestamp createdAt;

    private Timestamp lastUpdatedAt;

}