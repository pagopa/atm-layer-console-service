package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.client.CamundaWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum.CREATED;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@QuarkusTest
public class BpmnServiceImplTest {

    @Mock
    private BpmnWebClient bpmnWebClient;

    @Mock
    private CamundaWebClient camundaWebClient;

    @InjectMocks
    private BpmnServiceImpl bpmnService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBpmnFilteredTest() {
        UUID uuid = UUID.randomUUID();
        BpmnVersionFrontEndDTO bpmnVersionFrontEndDTO = new BpmnVersionFrontEndDTO();
        List<BpmnVersionFrontEndDTO> dtoList = new ArrayList<>();
        dtoList.add(bpmnVersionFrontEndDTO);
        PageInfo<BpmnVersionFrontEndDTO> response = new PageInfo<>(0, 1, 1, 1, dtoList);
        when(bpmnWebClient.getBpmnFiltered(anyInt(), anyInt(), anyString(), anyString(), anyString(), eq(uuid), eq(uuid), anyString(), anyString(), anyString(), anyString(), anyString(), eq(CREATED), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Uni.createFrom().item(response));
        bpmnService.getBpmnFiltered(0, 1, "functionType", "modelVersion", "1", uuid, uuid, "camundaDefinitionId", "definitionKey", "deployedFileName", "resource", "sha256", CREATED, "acquirerId", "branchId", "terminalId", "fileName")
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }

    @Test
    void createBpmnTest() {
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getHeaderString("Authorization")).thenReturn("eyJraWQiOiJ5Uk92cjRsa2dmb3U1UURCZGM1RzI5cEtSVWxyRHVONW9oSTN2a0padXFVPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoielVwVE1mQ1BtdDloOURkWnd6MnNUQSIsInN1YiI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsImNvZ25pdG86Z3JvdXBzIjpbImV1LXNvdXRoLTFfV2ROc2hXVVNWX0dvb2dsZSJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1zb3V0aC0xLmFtYXpvbmF3cy5jb21cL2V1LXNvdXRoLTFfV2ROc2hXVVNWIiwiY29nbml0bzp1c2VybmFtZSI6Ijc2Y2U5MjYwLTMwOTEtNzAyMC00ZTMyLTU4ZmE4NDA5ZDUyYSIsIm5vbmNlIjoiZVB6MDQ3MWx0UEp3MDkwZXZzOUI2RXBiVURyS2ZrLUxrRDN0WjRBNVR3ZERKU2ZwbWc2NnBTQTNRM3o4NWpZcFN6Q1NuQmJVYi1CZjYzMXRLM3lLZkgwcEViSDBNUEdTWGNfVXdRLXpOWEpwSmVZLUNUNTJwOUMweFluMXQ0cUVaa004TUgtQm1DUzlJN1Y0akYyazUtQ1gzaTlTR29Rc0RoUFhSczdISUV3IiwiYXVkIjoiMzMxbHNkaXVzNDlwZDk1N245Nmdqc2wyb2siLCJpZGVudGl0aWVzIjpbeyJkYXRlQ3JlYXRlZCI6IjE3MDYxNzkxODQ4NzQiLCJ1c2VySWQiOiIxMDc4ODgxMDAyMzY3MzcwMzQ4OTUiLCJwcm92aWRlck5hbWUiOiJHb29nbGUiLCJwcm92aWRlclR5cGUiOiJHb29nbGUiLCJpc3N1ZXIiOm51bGwsInByaW1hcnkiOiJ0cnVlIn1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcwOTkwNDYzNiwiZXhwIjoxNzA5OTkxMDM2LCJpYXQiOjE3MDk5MDQ2MzYsImp0aSI6ImE3OTI0MmM5LWFiNWQtNDgzOS04ZmQ0LTYxOWZmZDNkOTMzZiIsImVtYWlsIjoiYW50b25pby50YXJyaWNvbmVAcGFnb3BhLml0In0.agPEYHTxZtWsDpS1vcOxMnkZJbBWATw2AXhk2_ZFyzTbg1pNVep9IwXGNVwBrfi5Fx5HCfXebvcC0BLM40naEWdW1-Xc5MhsXbnq4TzU_XdXb6KFgrSJUUMGHUS4eUOio9z00w1fhdbBx4Tp0mrbu1tNxluIel6Y12cfSxfJq0JFX_sD80Zg-2O5sGiGe-zUjGTRw6jgk8MTkZRz9XbInDwg2Y826sZYtl-cy5uuPJZq1OxZqILMMacGmhGOvQGliD7a32uxQXApuTWzItaIV2VC_qRE69DskQbK_RFor9XubZNUYFPOSDfeStatl_K56HnEm2DLW6UpNjWcZTkcEw");
        BpmnCreationDto bpmnCreationDto = new BpmnCreationDto();
        VerifyResponse verifyResponse = new VerifyResponse();
        verifyResponse.setIsVerified(Boolean.TRUE);
        bpmnCreationDto.setFile(new File("src/test/resources/Test.bpmn"));
        when(bpmnWebClient.createBpmn(any(BpmnCreationDto.class))).thenReturn(Uni.createFrom().nullItem());
        when(camundaWebClient.verifyBpmn(any())).thenReturn(verifyResponse);
        Uni<BpmnDTO> result = bpmnService.createBpmn(containerRequestContext,bpmnCreationDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void downloadBpmnFrontEndTest() {
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.downloadBpmnFrontEnd(eq(uuid), anyLong())).thenReturn(Uni.createFrom().nullItem());
        Uni<FileS3Dto> result = bpmnService.downloadBpmnFrontEnd(uuid, 1L);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void getAssociationsByBpmnTest() {
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.getAssociationsByBpmn(anyInt(), anyInt(), eq(uuid), anyLong())).thenReturn(Uni.createFrom().nullItem());
        Uni<PageInfo<BpmnBankConfigDTO>> result = bpmnService.getAssociationsByBpmn(0, 1, uuid, 1L);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void addSingleAssociationTest() {
        UUID uuid = UUID.randomUUID();
        BankConfigTripletDto bankConfigTripletDto = mock();
        when(bpmnWebClient.addSingleAssociation(eq(uuid), anyLong(), any(BankConfigTripletDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnBankConfigDTO> result = bpmnService.addSingleAssociation(uuid, 1L, bankConfigTripletDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void deleteSingleAssociationTest() {
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.deleteSingleAssociation(eq(uuid), anyLong(), anyString(), anyString(), anyString())).thenReturn(Uni.createFrom().nullItem());
        Uni<Void> result = bpmnService.deleteSingleAssociation(uuid, 1L, "acquirerId", "branchId", "terminalId");

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void replaceSingleAssociationTest() {
        UUID uuid = UUID.randomUUID();
        BankConfigTripletDto bankConfigTripletDto = mock();
        when(bpmnWebClient.replaceSingleAssociation(eq(uuid), anyLong(), any(BankConfigTripletDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnBankConfigDTO> result = bpmnService.replaceSingleAssociation(uuid, 1L, bankConfigTripletDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void deployBPMNTest() {
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.deployBPMN(eq(uuid), anyLong())).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnDTO> result = bpmnService.deployBPMN(uuid, 1L);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void disableBPMNTest() {
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.disableBPMN(eq(uuid), anyLong())).thenReturn(Uni.createFrom().nullItem());
        Uni<Void> result = bpmnService.disableBPMN(uuid, 1L);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void upgradeBPMNTest() {
        BpmnUpgradeDto bpmnUpgradeDto = new BpmnUpgradeDto();
        bpmnUpgradeDto.setFile(new File("src/test/resources/Test.bpmn"));
        VerifyResponse verifyResponse = new VerifyResponse();
        verifyResponse.setIsVerified(Boolean.TRUE);
        when(camundaWebClient.verifyBpmn(any())).thenReturn(verifyResponse);
        when(bpmnWebClient.upgradeBPMN(any(BpmnUpgradeDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnDTO> result = bpmnService.upgradeBPMN(bpmnUpgradeDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }
}
