package it.gov.pagopa.atmlayer.service.consolebackend.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;


@Getter
@Jacksonized
@JsonPropertyOrder({"type", "errorCode", "status", "message", "errors"})
@RegisterForReflection
@SuperBuilder
public class ATMLayerValidationErrorResponse extends ATMLayerErrorResponse {

    @Schema(example = "Field xxx must be not null")
    private List<String> errors;
}