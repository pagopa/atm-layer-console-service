package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.*;

@AllArgsConstructor
public enum QuotaPeriodType {
    DAY("DAY"),

    WEEK("WEEK"),

    MONTH("MONTH"),

    UNKNOWN_TO_SDK_VERSION(null);

    private final String value;
}
