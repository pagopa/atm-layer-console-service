package it.gov.pagopa.atmlayer.service.consolebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtmLayerExceptionDTO {
    private String type;
    private String errorCode;
    private String message;
    private String statusCode;
}
