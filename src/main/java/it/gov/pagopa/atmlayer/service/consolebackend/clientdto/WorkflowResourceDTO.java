package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
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
public class WorkflowResourceDTO {
    private UUID workflowResourceId;
    @Schema(format = "byte", maxLength = 255)
    private String deployedFileName;
    @Schema(format = "byte", maxLength = 255)
    private String definitionKey;
    private StatusEnum status;
    @Schema(format = "byte", maxLength = 255)
    private String sha256;
    @Schema(minimum = "1", maximum = "10000")
    private Integer definitionVersionCamunda;
    @Schema(format = "byte", maxLength = 255)
    private String camundaDefinitionId;
    @Schema(format = "byte", maxLength = 255)
    private String description;
    private ResourceFileDTO resourceFile;
    @Schema(format = "byte", maxLength = 255)
    private String resource;
    private DeployableResourceType resourceType;
    private UUID deploymentId;
    @Schema(description = "Creation Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp createdAt;
    @Schema(description = "Last Update Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "{\"date\":\"2023-11-03T14:18:36.635+00:00\"}")
    private Timestamp lastUpdatedAt;
    @Schema(format = "byte", maxLength = 255)
    private String createdBy;
    @Schema(format = "byte", maxLength = 255)
    private String lastUpdatedBy;

}
