package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceMultipleCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.client.impl.multipart.QuarkusMultipartForm;
import org.jboss.resteasy.reactive.server.core.multipart.FormData;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.io.File;
import java.util.List;
import java.util.UUID;
@RegisterRestClient(configKey = "resource-client")
public interface ResourceWebClient {
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<ResourceFrontEndDTO>> getResourceFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                           @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) Integer pageIndex,
                                                           @QueryParam("pageSize") @DefaultValue("10")
                                                           @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) Integer pageSize,
                                                           @QueryParam("resourceId") UUID resourceId,
                                                           @QueryParam("sha256") String sha256,
                                                           @QueryParam("noDeployableResourceType") NoDeployableResourceType noDeployableResourceType,
                                                           @QueryParam("fileName") String fileName,
                                                           @QueryParam("storageKey") String storageKey,
                                                           @QueryParam("extension") String extension);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<ResourceDTO> createResource(@RequestBody(required = true) @Valid ResourceCreationDto resourceCreationDto);

    @POST
    @Path("/multiple")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<String>> createResourceMultiple(@RequestBody(required = true) ResourceMultipleCreationDto multipartFormDataInput);

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<ResourceDTO> updateResource(@RequestBody(required = true) @FormParam("file") File file, @PathParam("uuid") UUID uuid);

    @POST
    @Path("/disable/{uuid}")
    Uni<Void> disable(@PathParam("uuid") UUID uuid);
}
