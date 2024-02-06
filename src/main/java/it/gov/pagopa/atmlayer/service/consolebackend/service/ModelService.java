package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;

public interface ModelService {

    Uni<PageInfo<BpmnVersionFrontEndDTO>> getBpmnFiltered(int pageIndex, int pageSize, String functionType, String modelVersion, String status, String acquirerId, String filename);
}
