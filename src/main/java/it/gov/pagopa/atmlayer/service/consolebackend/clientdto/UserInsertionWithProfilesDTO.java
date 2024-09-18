package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserInsertionWithProfilesDTO {
    @NotBlank
    @Email(message = "must be an email address in the correct format")
    @Schema(required = true, example = "email@domain.com", maxLength = 255)
    private String userId;
    @Schema(format = "byte", maxLength = 255)
    private String name;
    @Schema(format = "byte", maxLength = 255)
    private String surname;
    @NotNull
    @Size(min = 1)
    @Schema(type = SchemaType.ARRAY, maxItems = 30)
    private List<@Range(min=1) Integer> profileIds;
}
