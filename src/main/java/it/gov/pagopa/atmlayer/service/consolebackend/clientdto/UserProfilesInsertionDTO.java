package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class UserProfilesInsertionDTO {
    @NotBlank
    private String userId;
    @NotNull
    @Size(min = 1)
    private List<@Range(min=1) Integer> profileIds;
}
