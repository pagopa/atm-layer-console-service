package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import java.io.File;

@Data
@NoArgsConstructor
public class ResourceCreationDto {

  @FormParam("file")
  @NotNull(message = "resource file is required")
  @Schema(format = "binary", maxLength = 5000)
  private File file;

  @FormParam("filename")
  @NotNull(message = "filename  is required")
  @Pattern(regexp = "^[a-zA-Z0-9_-]+\\.[a-zA-Z]+$", message = "it must be of form ${regexp}")
  @Schema(description = "Description of the filename parameter: example_filename.txt",
      required = true, pattern = "^[a-zA-Z0-9_-]+\\.[a-zA-Z]+$", format = "byte", maxLength = 60)
  @Length(max = 60)
  private String filename;

  @FormParam("resourceType")
  @NotNull(message = "resource type is required")
  private NoDeployableResourceType resourceType;

  @FormParam("path")
  @Pattern(regexp = "(^$)|(^(?!/)[a-zA-Z0-9/]+(?<!/)$)", message = "String must not start or end with '/' and must not contain white spaces and special characters")
  @DefaultValue("")
  @Schema(description = "Description of the path parameter: example/path",
      pattern = "(^$)|(^(?!/)[a-zA-Z0-9/]+(?<!/)$)", format = "byte", maxLength = 255)
  private String path;
  @FormParam("description")
  @Schema(format = "byte", maxLength = 255)
  private String description;
}
