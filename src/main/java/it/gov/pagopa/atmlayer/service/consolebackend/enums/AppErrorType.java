package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.Getter;

@Getter
public enum AppErrorType {
    GENERIC,
    VALIDATION,
    CONSTRAINT_VIOLATION,
    NOT_EXISTING_REFERENCED_ENTITY,
    NOT_VALID_REFERENCED_ENTITY,
    NOT_DEPLOYABLE_STATUS,
    NOT_DEPLOYED_STATUS,
    INVALID_FUNCTION_TYPE,
    NOT_DELETABLE,
    INTERNAL,
    NOT_UPGRADABLE,
    NOT_VALID_FILE,
    INVALID_DEPLOY,
    ID_NOT_FOUND,
    INVALID_ARGUMENT,
    NOT_UPDATABLE,
    NOT_UPLOADABLE,
    CANNOT_ROLLBACK,
    CANNOT_DISABLE,
    UNAUTHORIZED,
    CANNOT_ASSOCIATE
}