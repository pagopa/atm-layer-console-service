package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionInsertionDTO {
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String transactionId;
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String functionType;
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String acquirerId;
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String branchId;
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String terminalId;
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String transactionStatus;
}
