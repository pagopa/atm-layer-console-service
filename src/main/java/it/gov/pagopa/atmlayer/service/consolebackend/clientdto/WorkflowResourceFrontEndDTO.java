package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class WorkflowResourceFrontEndDTO {
    private UUID workflowResourceId;
    private String deployedFileName;
    private String definitionKey;
    private StatusEnum status;
    private String sha256;
    private Integer definitionVersionCamunda;
    private String camundaDefinitionId;
    private String description;
    private UUID resourceId;
    private S3ResourceTypeEnum resourceS3Type;
    private String storageKey;
    private String fileName;
    private String extension;
    @Schema(description = "Creation Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp resourceCreatedAt;
    @Schema(description = "Last Update Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp resourceLastUpdatedAt;
    private String resourceCreatedBy;
    private String resourceLastUpdatedBy;
    private String resource;
    private DeployableResourceType resourceType;
    private UUID deploymentId;
    @Schema(description = "Creation Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;
    private String createdBy;
    private String lastUpdatedBy;
}
