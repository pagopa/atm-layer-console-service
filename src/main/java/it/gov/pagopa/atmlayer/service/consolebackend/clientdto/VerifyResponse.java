package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import lombok.Data;

@Data
public class VerifyResponse {
    private Boolean isVerified;
    private String message;
}
