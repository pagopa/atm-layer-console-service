package it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper;

import it.gov.pagopa.atmlayer.service.consolebackend.model.ATMLayerErrorResponse;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.LinkedHashMap;

public class ModelClientExceptionMapper {

    private final String EXCEPTION_TYPE="type";
    private final String EXCEPTION_ERROR_CODE="errorCode";
    private final String EXCEPTION_MESSAGE="message";
    private final String EXCEPTION_STATUS_CODE="statusCode";

    @ServerExceptionMapper
    public RestResponse<ATMLayerErrorResponse> buildErrorResponse(ClientWebApplicationException exception) {
        LinkedHashMap hashMap = exception.getResponse().readEntity(LinkedHashMap.class);
        ATMLayerErrorResponse errorResponse = ATMLayerErrorResponse.builder()
                .type(hashMap.get(EXCEPTION_TYPE).toString())
                .errorCode(hashMap.get(EXCEPTION_ERROR_CODE).toString())
                .statusCode(Integer.parseInt(hashMap.get(EXCEPTION_STATUS_CODE).toString()))
                .message(hashMap.get(EXCEPTION_MESSAGE).toString())
                .build();
        return RestResponse.status(Response.Status.fromStatusCode(exception.getResponse().getStatus()), errorResponse);
    }

}
