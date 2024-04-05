package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import jakarta.ws.rs.core.Response;
import org.camunda.bpm.model.bpmn.Bpmn;

import java.io.File;

public class InternalUtils {

    public static void validateBPMN(File file){
        try {
            Bpmn.readModelFromFile(file);
        } catch (Exception e) {
            throw new AtmLayerException("Il file non  è un valido BPMN "+ e.getCause().getMessage(), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID);
        }
    }
}
