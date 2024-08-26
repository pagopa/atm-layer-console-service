package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.PartType;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
public class ResourceMultipleCreationDto {

    @FormParam("file")
    @NotNull(message = "resource file is required")
    @PartType("application/octet-stream")
    @Schema(type = SchemaType.ARRAY, maxItems = 100)
    private List<File> fileList;

    @FormParam("filename")
    @NotNull(message = "filename  is required")
    @PartType(MediaType.APPLICATION_JSON)
    @Schema(type = SchemaType.ARRAY, maxItems = 100)
    private List<String> filenameList;

    @FormParam("resourceType")
    @NotNull(message = "resource type is required")
    @PartType("text/plain")
    private NoDeployableResourceType resourceType;

    @FormParam("path")
    @Pattern(regexp = "(^$)|(^(?!/)[a-zA-Z0-9/]+(?<!/)$)", message = "String must not start or end with '/' and must not contain white spaces and special characters")
    @DefaultValue("")
    @Schema(description = "Description of the path parameter: example/path",
            pattern = "(^$)|(^(?!/)[a-zA-Z0-9/]+(?<!/)$)", format = "byte", maxLength = 255)
    @PartType("text/plain")
    private String path;
    @FormParam("description")
    @PartType("text/plain")
    @Schema(format = "byte", maxLength = 255)
    private String description;
}
