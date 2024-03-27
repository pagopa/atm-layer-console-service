package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ResourceWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@QuarkusTest
public class ResourceServiceImplTest {

    @Mock
    private ResourceWebClient resourceWebClient;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getResourceFilteredTest(){
        NoDeployableResourceType noDeployableResourceType = mock();
        UUID uuid = UUID.randomUUID();
        when(resourceWebClient.getResourceFiltered(anyInt(), anyInt(), eq(uuid), anyString(), any(NoDeployableResourceType.class), anyString(), anyString(), anyString())).thenReturn(Uni.createFrom().nullItem());
        Uni<PageInfo<ResourceFrontEndDTO>> result = resourceService.getResourceFiltered(0,1, uuid, "sha256", noDeployableResourceType, "filename", "storageKey", "extension");

        assertNotNull(result);
        assertNull(result.await().indefinitely());

    }

    @Test
    void createResourceTest(){
        ResourceCreationDto resourceCreationDto = mock();
        when(resourceWebClient.createResource(any(ResourceCreationDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<ResourceDTO> result = resourceService.createResource(resourceCreationDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }


    @Test
    void updateResourceTest(){
        File file = mock();
        UUID uuid = UUID.randomUUID();
        when(resourceWebClient.updateResource(any(File.class), eq(uuid))).thenReturn(Uni.createFrom().nullItem());
        Uni<ResourceDTO> result = resourceService.updateResource(file, uuid);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }


    @Test
    void disableTest(){
        UUID uuid = UUID.randomUUID();
        when(resourceWebClient.disable(eq(uuid))).thenReturn(Uni.createFrom().nullItem());
        Uni<Void> result = resourceService.disable(uuid);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

}
