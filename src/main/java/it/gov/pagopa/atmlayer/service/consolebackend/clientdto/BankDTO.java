package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BankDTO {
    private String acquirerId;
    private String denomination;
    private String clientId;
    private String clientSecret;
    private int rateLimit;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
