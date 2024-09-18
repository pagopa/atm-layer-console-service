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
import it.gov.pagopa.atmlayer.service.consolebackend.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.File;
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
    public Uni<WorkflowResourceDTO> deploy(UUID uuid, ContainerRequestContext containerRequestContext) {
        return workflowWebClient.deploy(uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Rilascio risorsa aggiuntiva per processo"));
    }

    @Override
    public Uni<WorkflowResourceDTO> rollback(UUID uuid, ContainerRequestContext containerRequestContext) {
        return workflowWebClient.rollback(uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Rollback risorsa aggiuntiva per processo"));
    }

    @Override
    public Uni<WorkflowResourceDTO> update(File file, UUID uuid, ContainerRequestContext containerRequestContext) {
        return workflowWebClient.update(file, uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Aggiornamento risorsa aggiuntiva per processo"));
    }

    @Override
    public Uni<Void> disable(UUID uuid, ContainerRequestContext containerRequestContext) {
        return workflowWebClient.disable(uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Disabilita risorsa aggiuntiva per processo"));
    }

    @Override
    public Uni<WorkflowResourceDTO> create(WorkflowResourceCreationDto workflowResourceCreationDto, ContainerRequestContext containerRequestContext) {
        return workflowWebClient.create(workflowResourceCreationDto)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento risorsa aggiuntiva per processo"));
    }
}
