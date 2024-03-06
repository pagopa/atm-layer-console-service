package it.gov.pagopa.atmlayer.service.consolebackend.exception.mapper;

import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import static it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum.ATMLCB_500;

public class TaskClientExceptionMapper implements ResponseExceptionMapper<AtmLayerException> {


    @Override
    public AtmLayerException toThrowable(Response response) {
        return new AtmLayerException("Task Error", Response.Status.fromStatusCode(response.getStatus()), ATMLCB_500);
    }

}
