package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ResourceWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ResourceService;
import it.gov.pagopa.atmlayer.service.consolebackend.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.fromFileListToStringList;

@ApplicationScoped
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Inject
    @RestClient
    ResourceWebClient resoureWebClient;

    @Override
    public Uni<PageInfo<ResourceFrontEndDTO>> getResourceFiltered(int pageIndex, int pageSize, UUID resourceId, String sha256, NoDeployableResourceType noDeployableResourceType, String fileName, String storageKey, String extension) {
        return resoureWebClient.getResourceFiltered(pageIndex, pageSize, resourceId, sha256, noDeployableResourceType, fileName, storageKey, extension);
    }

    @Override
    public Uni<ResourceDTO> createResource(ResourceCreationDto resourceCreationDto, ContainerRequestContext containerRequestContext) {
        return resoureWebClient.createResource(resourceCreationDto)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento risorsa statica"));
    }

    @Override
    public Uni<List<String>> createResourceMultiple(ResourceMultipleCreationDto resourceMultipleCreationDto, ContainerRequestContext containerRequestContext){
        ResourceMultipleCreationDtoJSON request = new ResourceMultipleCreationDtoJSON();

        request.setResourceType(resourceMultipleCreationDto.getResourceType());
        request.setDescription(resourceMultipleCreationDto.getDescription());
        request.setPath(resourceMultipleCreationDto.getPath());
        request.setFileList(fromFileListToStringList(resourceMultipleCreationDto.getFileList()));
        request.setFilenameList(resourceMultipleCreationDto.getFilenameList());

        return resoureWebClient.createResourceMultiple(request)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento multiple risorse statiche"));
    }

    @Override
    public Uni<ResourceDTO> updateResource(File file, UUID uuid, ContainerRequestContext containerRequestContext) {
        return resoureWebClient.updateResource(file, uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Aggiornamento risorsa statica"));
    }

    @Override
    public Uni<Void> disable(UUID uuid, ContainerRequestContext containerRequestContext) {
        return resoureWebClient.disable(uuid)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Disabilita risorsa statica"));
    }
}
