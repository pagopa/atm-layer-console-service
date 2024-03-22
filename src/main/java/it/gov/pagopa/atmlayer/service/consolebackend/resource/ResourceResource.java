package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ResourceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.File;
import java.util.UUID;

@Path("/resources")
@Tag(name = "Resource", description = "Resource proxy")
@Slf4j
@ApplicationScoped
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT")
})
@SecurityRequirement(name = "bearerAuth")
public class ResourceResource {

    @Inject
    ResourceService resourceService;

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PageInfo<ResourceFrontEndDTO>> getResourceFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                                  @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                                  @QueryParam("pageSize") @DefaultValue("10")
                                                                  @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                                  @QueryParam("resourceId") UUID resourceId,
                                                                  @QueryParam("sha256") String sha256,
                                                                  @QueryParam("noDeployableResourceType") NoDeployableResourceType noDeployableResourceType,
                                                                  @QueryParam("fileName") String fileName,
                                                                  @QueryParam("storageKey") String storageKey,
                                                                  @QueryParam("extension") String extension){

        return this.resourceService.getResourceFiltered(pageIndex, pageSize, resourceId, sha256, noDeployableResourceType, fileName, storageKey, extension)
                .onItem()
                .transform(Unchecked.function(pagedList -> {
                    if (pagedList.getResults().isEmpty()) {
                        log.info("No Resources meets the applied filters");
                    }
                    return pagedList;
                }));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ResourceDTO> createResource(@RequestBody(required = true) @Valid ResourceCreationDto resourceCreationDto){
         return this.resourceService.createResource(resourceCreationDto);
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ResourceDTO> updateResource(@RequestBody(required = true) @FormParam("file") File file,
                                           @PathParam("uuid") UUID uuid) {
        return resourceService.updateResource(file, uuid);
    }

    @POST
    @Path("/disable/{uuid}")
    public Uni<Void> disable(@PathParam("uuid") UUID uuid) {
        return resourceService.disable(uuid);
    }
}
