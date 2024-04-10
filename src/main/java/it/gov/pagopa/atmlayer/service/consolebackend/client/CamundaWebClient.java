package it.gov.pagopa.atmlayer.service.consolebackend.client;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.File;

@RegisterRestClient(configKey = "camunda-client")
public interface CamundaWebClient {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/camunda/verify/bpmn")
    Boolean verifyBpmn(@FormParam("file") File file);


}
