package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum S3ResourceTypeEnum implements Serializable {
    BPMN("bpmn","application/bpmn"),
    DMN("dmn", "application/dmn"),
    FORM("json", "application/json"),
    HTML("html", "application/html"),
    OTHER("other", null);

    final String extension;
    final String mimetype;
}
