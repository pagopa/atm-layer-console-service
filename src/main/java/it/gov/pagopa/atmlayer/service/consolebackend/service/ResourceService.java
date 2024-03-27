package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;

import java.io.File;
import java.util.UUID;

public interface ResourceService {
    Uni<PageInfo<ResourceFrontEndDTO>> getResourceFiltered(int pageIndex, int pageSize, UUID resourceId, String sha256, NoDeployableResourceType noDeployableResourceType, String fileName, String storageKey, String extension);
    Uni<ResourceDTO> createResource(ResourceCreationDto resourceCreationDto);
    Uni<ResourceDTO> updateResource(File file, UUID uuid);
    Uni<Void> disable(UUID uuid);
}
