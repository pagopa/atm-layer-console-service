package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.jboss.resteasy.reactive.PartType;

import java.io.File;

@Data
@NoArgsConstructor
public class WorkflowResourceCreationDto {
    @FormParam("file")
    @PartType(MediaType.APPLICATION_XML)
    @NotNull(message = "Workflow Resource file is required")
    @Schema(format = "binary", maxLength = 5000)
    private File file;

    @FormParam("filename")
    @NotNull(message = "filename  is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "deve essere della forma ${regexp} e non contenere l'estensione del file")
    @Schema(format = "byte", maxLength = 60)
    @Length(max = 60)
    private String filename;

    @FormParam("resourceType")
    @NotNull(message = "resource type is required")
    private DeployableResourceType resourceType;

    @FormParam("description")
    @Nullable
    @Schema(format = "byte", maxLength = 255)
    private String description;
}
