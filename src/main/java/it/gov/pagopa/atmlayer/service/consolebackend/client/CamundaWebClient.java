package it.gov.pagopa.atmlayer.service.consolebackend.client;

import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.VerifyResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.File;

@RegisterRestClient(configKey = "camunda-client")
public interface CamundaWebClient {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/camunda/verify/bpmn")
    VerifyResponse verifyBpmn(@FormParam("file") File file);


}
