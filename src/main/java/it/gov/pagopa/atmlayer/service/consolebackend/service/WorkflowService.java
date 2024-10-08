package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.io.File;
import java.util.UUID;

public interface WorkflowService {

    Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(int page, int pageSize, StatusEnum status, UUID workflowResourceId, String deployedFileName, String definitionKey,
                                                                          DeployableResourceType resourceType, String sha256, String definitionVersionCamunda, String camundaDefinitionId,
                                                                          String description, String resource, UUID deploymentId, String fileName);

    Uni<FileS3Dto> downloadFrontEnd (UUID workflowResourceId);

    Uni<WorkflowResourceDTO> create(WorkflowResourceCreationDto workflowResourceCreationDto, ContainerRequestContext containerRequestContext);

    Uni<WorkflowResourceDTO> deploy(UUID uuid, ContainerRequestContext containerRequestContext);

    Uni<WorkflowResourceDTO> rollback(UUID uuid, ContainerRequestContext containerRequestContext);

    Uni<WorkflowResourceDTO> update(File file, UUID uuid, ContainerRequestContext containerRequestContext);

    Uni<Void> disable( UUID uuid, ContainerRequestContext containerRequestContext);
}
