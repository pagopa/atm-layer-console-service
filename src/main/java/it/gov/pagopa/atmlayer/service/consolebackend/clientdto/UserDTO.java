package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserDTO {
    private String userId;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
    private List<ProfileDTO> userProfiles;
}
