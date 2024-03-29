package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDto {
    private String userId;
    private UserProfileEnum profile;
    private Boolean visible;
    private Boolean editable;
    private Boolean admin;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}