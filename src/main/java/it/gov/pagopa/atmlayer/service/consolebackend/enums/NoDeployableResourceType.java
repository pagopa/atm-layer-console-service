package it.gov.pagopa.atmlayer.service.consolebackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoDeployableResourceType {

    HTML("html", "application/html"),
    OTHER(null, null);

    final String extension;
    final String mimetype;
}
