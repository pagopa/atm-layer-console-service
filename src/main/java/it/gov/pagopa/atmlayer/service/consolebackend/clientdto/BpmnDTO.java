package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;


import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BpmnDTO implements Serializable {

    private UUID bpmnId;
    private Long modelVersion;
    private String deployedFileName;
    private String definitionKey;
    private String functionType;
    private StatusEnum status;
    private String sha256;
    private Boolean enabled;
    private Integer definitionVersionCamunda;
    private String camundaDefinitionId;
    private String description;
    private ResourceFileDTO resourceFile;
    private String resource;
    private UUID deploymentId;
    @Schema(description = "Creation Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "2023-11-03T14:18:36.635+00:00")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "2023-11-03T14:18:36.635+00:00")
    private Timestamp lastUpdatedAt;
    private String createdBy;
    private String lastUpdatedBy;
}