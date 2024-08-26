package it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionUpdateDTO {
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String transactionId;
    @NotNull
    @Schema(format = "byte", maxLength = 255)
    private String transactionStatus;
    @NotNull
    @Schema(format = "byte", maxLength = 255)
    private String functionType;
}
