package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.QuotaPeriodType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
public class BankUpdateDTO {
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String acquirerId;
    @Schema(format = "byte", maxLength = 255)
    private String denomination;
    @Schema(minimum = "1", maximum = "10000")
    private Integer limit;
    private QuotaPeriodType period;
    private Double rateLimit;
}