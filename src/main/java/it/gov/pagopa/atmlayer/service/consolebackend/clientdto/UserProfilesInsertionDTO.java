package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class UserProfilesInsertionDTO {
    @NotBlank
    @Schema(format = "byte", maxLength = 255)
    private String userId;
    @NotNull
    @Size(min = 1)
    @Schema(type = SchemaType.ARRAY, maxItems = 30)
    private List<@Range(min=1) Integer> profileIds;
}
