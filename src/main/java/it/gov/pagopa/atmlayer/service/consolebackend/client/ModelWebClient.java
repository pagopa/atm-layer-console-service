package it.gov.pagopa.atmlayer.service.consolebackend.client;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "model-path-api")
public interface ModelWebClient {


    @GET
    @Path("/api/v1/model/bpmn/filter")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(@QueryParam("pageIndex") @DefaultValue("0")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "0")) int pageIndex,
                                                          @QueryParam("pageSize") @DefaultValue("10")
                                                          @Parameter(required = true, schema = @Schema(type = SchemaType.INTEGER, minimum = "1")) int pageSize,
                                                          @QueryParam("functionType") String functionType,
                                                          @QueryParam("modelVersion") String modelVersion,
                                                          @QueryParam("status") String status,
                                                          @QueryParam("acquirerId") String acquirerId,
                                                          @QueryParam("fileName") String fileName);



}
