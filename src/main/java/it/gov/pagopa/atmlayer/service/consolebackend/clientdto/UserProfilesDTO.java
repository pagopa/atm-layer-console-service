package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserProfilesDTO {
    private String userId;
    private int profileId;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
