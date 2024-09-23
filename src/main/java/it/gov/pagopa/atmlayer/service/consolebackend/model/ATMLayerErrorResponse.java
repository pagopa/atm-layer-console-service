package it.gov.pagopa.atmlayer.service.consolebackend.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Jacksonized
@JsonPropertyOrder({"type", "errorCode", "status", "message"})
@RegisterForReflection
@SuperBuilder
public class ATMLayerErrorResponse {
    @Schema(format = "byte", maxLength = 255)
    private String errorCode;

    @Schema(example = "Validation Error", maxLength = 255)
    private String type;

    @Schema(example = "500", minimum = "1", maximum = "999")
    private int statusCode;

    @Schema(format = "byte", maxLength = 255)
    private String message;
}