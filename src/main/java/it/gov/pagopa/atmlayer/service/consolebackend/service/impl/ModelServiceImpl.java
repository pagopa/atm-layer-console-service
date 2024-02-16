package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ModelWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Inject
    @RestClient
    ModelWebClient modelWebClient;

    @Override
    public Uni<PageInfo<WorkflowResourceFrontEndDTO>> getWorkflowResourceFiltered(int pageIndex, int pageSize, StatusEnum status, UUID workflowResourceId, String deployedFileName, String definitionKey,
                                                                                 DeployableResourceType resourceType, String sha256, String definitionVersionCamunda, String camundaDefinitionId, String description, String resource, UUID deploymentId, String fileName) {
        return modelWebClient.getWorkflowResourceFiltered(pageIndex, pageSize, status, workflowResourceId, deployedFileName, definitionKey,
                resourceType, sha256, definitionVersionCamunda, camundaDefinitionId, description, resource, deploymentId, fileName);
    }
}
