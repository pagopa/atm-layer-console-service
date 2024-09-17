package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceMultipleCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface ResourceService {
    Uni<PageInfo<ResourceFrontEndDTO>> getResourceFiltered(int pageIndex, int pageSize, UUID resourceId, String sha256, NoDeployableResourceType noDeployableResourceType, String fileName, String storageKey, String extension);
    Uni<ResourceDTO> createResource(ResourceCreationDto resourceCreationDto, ContainerRequestContext containerRequestContext);
    Uni<List<String>> createResourceMultiple(ResourceMultipleCreationDto multipartFormDataInput, ContainerRequestContext containerRequestContext);
    Uni<ResourceDTO> updateResource(File file, UUID uuid, ContainerRequestContext containerRequestContext);
    Uni<Void> disable(UUID uuid, ContainerRequestContext containerRequestContext);
}
