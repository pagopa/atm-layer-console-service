package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;

import java.util.UUID;

public interface WorkflowService {

    Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(int page, int pageSize, StatusEnum status, UUID workflowResourceId, String deployedFileName, String definitionKey,
                                                                          DeployableResourceType resourceType, String sha256, String definitionVersionCamunda, String camundaDefinitionId,
                                                                          String description, String resource, UUID deploymentId, String fileName);

    Uni<FileS3Dto> downloadFrontEnd (UUID workflowResourceId);
}
