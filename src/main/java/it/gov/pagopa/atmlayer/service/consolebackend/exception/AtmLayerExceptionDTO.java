package it.gov.pagopa.atmlayer.service.consolebackend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtmLayerExceptionDTO {
    private String type;
    private String errorCode;
    private String message;
    private String statusCode;
}
