package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
public class UserDTO {
    private String userId;
    private String name;
    private String surname;
    private List<ProfileDTO> profiles;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
