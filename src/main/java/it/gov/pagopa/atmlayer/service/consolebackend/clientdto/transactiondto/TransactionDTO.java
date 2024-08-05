package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TransactionDTO {
    private String transactionId;
    private String functionType;
    private String acquirerId;
    private String branchId;
    private String terminalId;
    private String transactionStatus;
    @Schema(example = "2023-11-03T14:18:36.635+02:00")
    @JsonFormat(timezone = "Europe/Rome")
    private Timestamp createdAt;
    @Schema(example = "2023-11-03T14:18:36.635+02:00")
    @JsonFormat(timezone = "Europe/Rome")
    private Timestamp lastUpdatedAt;
}
