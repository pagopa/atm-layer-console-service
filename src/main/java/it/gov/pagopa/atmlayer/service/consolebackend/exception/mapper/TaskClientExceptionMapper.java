package it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper;

import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerErrorResponse;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.LinkedHashMap;


public class TaskClientExceptionMapper {

    private final String STATUS = "status";
    private final String ERROR_CODE = "errorCode";
    private final String DESCRIPTION = "description";

    @ServerExceptionMapper
    public RestResponse<ATMLayerErrorResponse> buildErrorResponse(ClientWebApplicationException exception) {
        LinkedHashMap hashMap = exception.getResponse().readEntity(LinkedHashMap.class);
        ATMLayerErrorResponse errorResponse = ATMLayerErrorResponse.builder()
                .errorCode(hashMap.get(ERROR_CODE).toString())
                .statusCode(Integer.parseInt(hashMap.get(STATUS).toString()))
                .message(hashMap.get(DESCRIPTION).toString())
                .build();
        return RestResponse.status(Response.Status.fromStatusCode(exception.getResponse().getStatus()), errorResponse);
    }

}
