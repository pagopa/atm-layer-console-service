package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ResourceWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.NoDeployableResourceType;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@QuarkusTest
class ResourceServiceImplTest {

    @Mock
    private ResourceWebClient resourceWebClient;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getResourceFilteredTest() {
        NoDeployableResourceType noDeployableResourceType = mock();
        UUID uuid = UUID.randomUUID();
        when(resourceWebClient.getResourceFiltered(anyInt(), anyInt(), eq(uuid), anyString(), any(NoDeployableResourceType.class), anyString(), anyString(), anyString())).thenReturn(Uni.createFrom().nullItem());
        Uni<PageInfo<ResourceFrontEndDTO>> result = resourceService.getResourceFiltered(0, 1, uuid, "sha256", noDeployableResourceType, "filename", "storageKey", "extension");

        assertNotNull(result);
        assertNull(result.await().indefinitely());

    }

    @Test
    void createResourceTest() {
        ResourceCreationDto resourceCreationDto = mock();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(resourceWebClient.createResource(any(ResourceCreationDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<ResourceDTO> result = resourceService.createResource(resourceCreationDto, containerRequestContext);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void testCreateResourceMultipleImplementation() {
        ResourceMultipleCreationDto resourceMultipleCreationDto = new ResourceMultipleCreationDto();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");
        resourceMultipleCreationDto.setResourceType(NoDeployableResourceType.OTHER);
        resourceMultipleCreationDto.setDescription("description");
        resourceMultipleCreationDto.setPath("path");
        resourceMultipleCreationDto.setFileList(List.of(new File("src/test/resources/Test.bpmn"), new File("src/test/resources/TestMalformed.bpmn")));
        resourceMultipleCreationDto.setFilenameList(List.of("name1.bpmn", "name2.bpmn"));

        when(resourceWebClient.createResourceMultiple(any()))
                .thenReturn(Uni.createFrom().item(List.of("resource1", "resource2")));

        Uni<List<String>> resultUni = resourceService.createResourceMultiple(resourceMultipleCreationDto, containerRequestContext);
        List<String> result = resultUni.await().indefinitely();

        verify(resourceWebClient).createResourceMultiple(argThat(request ->
                request.getResourceType().equals(NoDeployableResourceType.OTHER) &&
                        request.getDescription().equals("description") &&
                        request.getPath().equals("path") &&
                        request.getFilenameList().get(0).equals("name1.bpmn")  &&
                        request.getFilenameList().get(1).equals("name2.bpmn")
        ));

        assertEquals(List.of("resource1", "resource2"), result);
    }


    @Test
    void updateResourceTest() {
        File file = mock();
        UUID uuid = UUID.randomUUID();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(resourceWebClient.updateResource(any(File.class), eq(uuid))).thenReturn(Uni.createFrom().nullItem());
        Uni<ResourceDTO> result = resourceService.updateResource(file, uuid, containerRequestContext);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }


    @Test
    void disableTest() {
        UUID uuid = UUID.randomUUID();
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");

        when(resourceWebClient.disable(eq(uuid))).thenReturn(Uni.createFrom().nullItem());
        Uni<Void> result = resourceService.disable(uuid, containerRequestContext);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

}
