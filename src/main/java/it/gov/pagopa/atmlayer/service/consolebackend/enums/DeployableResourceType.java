package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeployableResourceType {
    BPMN("bpmn:process", "id"),
    DMN("decision", "id"),
    FORM("id", null);

    final String tagName;
    final String attribute;
}
