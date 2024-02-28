package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.PathParam;

import java.io.File;
import java.util.UUID;

public interface WorkflowService {

    Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(int page, int pageSize, StatusEnum status, UUID workflowResourceId, String deployedFileName, String definitionKey,
                                                                          DeployableResourceType resourceType, String sha256, String definitionVersionCamunda, String camundaDefinitionId,
                                                                          String description, String resource, UUID deploymentId, String fileName);

    Uni<FileS3Dto> downloadFrontEnd (UUID workflowResourceId);

    Uni<WorkflowResourceFrontEndDTO> create(WorkflowResourceCreationDto workflowResourceCreationDto);

    Uni<WorkflowResourceFrontEndDTO> deploy(UUID uuid);

    Uni<WorkflowResourceFrontEndDTO> rollback(UUID uuid);

    Uni<WorkflowResourceFrontEndDTO> update(File file, UUID uuid);

    Uni<Void> disable( UUID uuid);
}
