package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ModelWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Inject
    @RestClient
    ModelWebClient modelWebClient;

    @Override
    public Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String status, String acquirerId, String filename) {
        return modelWebClient.getBpmnFiltered(pageIndex, pageSize, functionType, modelVersion, status, acquirerId, filename);
    }
}
