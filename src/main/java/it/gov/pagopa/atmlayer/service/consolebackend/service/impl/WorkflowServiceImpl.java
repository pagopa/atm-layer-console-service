package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.WorkflowWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.WorkflowService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {

    @Inject
    @RestClient
    WorkflowWebClient workflowWebClient;

    @Override
    public Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(int pageIndex, int pageSize, StatusEnum status, UUID workflowResourceId, String deployedFileName, String definitionKey,
                                                                                 DeployableResourceType resourceType, String sha256, String definitionVersionCamunda, String camundaDefinitionId, String description, String resource, UUID deploymentId, String fileName) {
        return workflowWebClient.getWorkflowResourceFiltered(pageIndex, pageSize, status, workflowResourceId, deployedFileName, definitionKey,
                resourceType, sha256, definitionVersionCamunda, camundaDefinitionId, description, resource, deploymentId, fileName);
    }

    @Override
    public Uni<FileS3Dto> downloadFrontEnd(UUID workflowResourceId) {
        return workflowWebClient.downloadFrontEnd(workflowResourceId);
    }

    @Override
    public Uni<WorkflowResourceDTO> deploy(UUID uuid) {
        return workflowWebClient.deploy(uuid);
    }

    @Override
    public Uni<WorkflowResourceDTO> rollback(UUID uuid) {
        return workflowWebClient.rollback(uuid);
    }

    @Override
    public Uni<WorkflowResourceDTO> create(WorkflowResourceCreationDto workflowResourceCreationDto) {
        return workflowWebClient.create(workflowResourceCreationDto);
    }
}
