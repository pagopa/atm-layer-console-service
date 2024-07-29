package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ProfileDTO {

    private int profileId;

    private String description;

    private Timestamp createdAt;

    private Timestamp lastUpdatedAt;
}
