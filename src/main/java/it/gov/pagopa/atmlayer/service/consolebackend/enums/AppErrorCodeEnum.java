package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.Getter;

import static it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorType.*;

/**
 * Enumeration for application error codes and messages
 */
@Getter
public enum AppErrorCodeEnum {

    ATMLCB_500("ATMLCB_500", "An unexpected error has occurred, see logs for more info", GENERIC),
    ATMLCB_401("ATMLCB_401", "Unauthorized", UNAUTHORIZED);

    private final String errorCode;
    private final String errorMessage;
    private final AppErrorType type;

    AppErrorCodeEnum(String errorCode, String errorMessage, AppErrorType type) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.type = type;
    }
}
