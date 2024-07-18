package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProfileDTO {

    private int profileId;

    private String description;

    private Timestamp createdAt;

    private Timestamp lastUpdatedAt;
}
