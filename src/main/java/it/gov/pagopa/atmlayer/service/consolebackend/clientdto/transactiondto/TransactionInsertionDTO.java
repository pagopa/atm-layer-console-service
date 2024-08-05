package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionInsertionDTO {
    @NotBlank
    private String transactionId;
    @NotBlank
    private String functionType;
    @NotBlank
    private String acquirerId;
    @NotBlank
    private String branchId;
    @NotBlank
    private String terminalId;
    @NotBlank
    private String transactionStatus;
}
