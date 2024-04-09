package it.gov.pagopa.atmlayer.service.consolebackend.utils;

import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.Bpmn;

import java.io.File;


@Slf4j
public class InternalUtils {

    public static void validateBPMN(File file) {
        try {
//            InputStream inputStream = new FileInputStream(file);
//            Bpmn.readModelFromStream(inputStream);
            Bpmn.readModelFromFile(file);
        } catch (Exception e) {
            log.error("Il file non  è un valido BPMN ",e);
            throw new AtmLayerException("Il file non  è un valido BPMN " + (e.getMessage()), Response.Status.NOT_ACCEPTABLE, AppErrorCodeEnum.FILE_NOT_VALID);
        }
    }
}
