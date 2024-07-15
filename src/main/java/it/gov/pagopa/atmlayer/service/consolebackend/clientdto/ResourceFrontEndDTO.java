package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.S3ResourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFrontEndDTO {
    private UUID resourceId;
    @Schema(format = "byte", maxLength = 255)
    private String sha256;
    private Boolean enabled;
    NoDeployableResourceType noDeployableResourceType;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
    @Schema(format = "byte", maxLength = 255)
    private String createdBy;
    @Schema(format = "byte", maxLength = 255)
    private String lastUpdatedBy;
    @Schema(format = "byte", maxLength = 255)
    private String cdnUrl;
    private UUID resourceFileId;
    private S3ResourceTypeEnum resourceType;
    @Schema(format = "byte", maxLength = 255)
    private String storageKey;
    @Schema(format = "byte", maxLength = 255)
    private String fileName;
    @Schema(format = "byte", maxLength = 255)
    private String extension;
    @Schema(description = "Creation Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "2023-11-03T14:18:36.635+00:00")
    private Timestamp resourceFileCreatedAt;
    @Schema(description = "Last Update Timestamp", format = "timestamp", pattern = "DD/MM/YYYY", example = "2023-11-03T14:18:36.635+00:00")
    private Timestamp resourceFileLastUpdatedAt;
    @Schema(format = "byte", maxLength = 255)
    private String resourceFileCreatedBy;
    @Schema(format = "byte", maxLength = 255)
    private String resourceFileLastUpdatedBy;
}
