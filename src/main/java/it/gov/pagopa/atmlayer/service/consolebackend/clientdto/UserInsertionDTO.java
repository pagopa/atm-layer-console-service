package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class UserInsertionDTO {
    @NotBlank
    @Email(message = "must be an email address in the correct format")
    @Schema(required = true, example = "email@domain.com")
    private String userId;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}
