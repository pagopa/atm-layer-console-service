package it.gov.pagopa.atmlayer.service.consolebackend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtmLayerExceptionDTO implements Serializable {
    @JsonProperty
    private String type;
    @JsonProperty
    private String errorCode;
    @JsonProperty
    private String message;
    @JsonProperty
    private String statusCode;
}
