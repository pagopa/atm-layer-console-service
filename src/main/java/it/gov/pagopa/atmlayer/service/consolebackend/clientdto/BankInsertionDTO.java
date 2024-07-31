package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.QuotaPeriodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class BankInsertionDTO {

    @NotBlank
    private String acquirerId;
    @NotBlank
    private String denomination;

    private Integer limit;

    private QuotaPeriodType period;

    private Integer burstLimit;

    private Double rateLimit;

}
