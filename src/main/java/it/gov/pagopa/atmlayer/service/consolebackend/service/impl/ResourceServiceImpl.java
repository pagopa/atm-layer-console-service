package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ResourceWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceMultipleCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ResourceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.client.impl.multipart.QuarkusMultipartForm;
import org.jboss.resteasy.reactive.server.core.multipart.FormData;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.io.File;
import java.util.List;
import java.util.UUID;

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
    public Uni<ResourceDTO> createResource(ResourceCreationDto resourceCreationDto) {
        return resoureWebClient.createResource(resourceCreationDto);
    }

    @Override
    public Uni<List<String>> createResourceMultiple(ResourceMultipleCreationDto multipartFormDataInput){
        return resoureWebClient.createResourceMultiple(multipartFormDataInput);
    }

    @Override
    public Uni<ResourceDTO> updateResource(File file, UUID uuid) {
        return resoureWebClient.updateResource(file, uuid);
    }

    @Override
    public Uni<Void> disable(UUID uuid) {
        return resoureWebClient.disable(uuid);
    }
}
