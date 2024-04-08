package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.*;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
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
        BpmnCreationDto bpmnCreationDto = new BpmnCreationDto();
        bpmnCreationDto.setFile(new File("src/test/resources/Test.bpmn"));
        when(bpmnWebClient.createBpmn(any(BpmnCreationDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnDTO> result = bpmnService.createBpmn(bpmnCreationDto);

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
    void deleteSingleAssociationTest(){
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.deleteSingleAssociation(eq(uuid), anyLong(), anyString(), anyString(), anyString())).thenReturn(Uni.createFrom().nullItem());
        Uni<Void>  result = bpmnService.deleteSingleAssociation(uuid, 1L, "acquirerId", "branchId", "terminalId");

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void replaceSingleAssociationTest(){
        UUID uuid = UUID.randomUUID();
        BankConfigTripletDto bankConfigTripletDto = mock();
        when(bpmnWebClient.replaceSingleAssociation(eq(uuid), anyLong(), any(BankConfigTripletDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnBankConfigDTO>  result = bpmnService.replaceSingleAssociation(uuid, 1L, bankConfigTripletDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void  deployBPMNTest() {
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
    void  upgradeBPMNTest() {
        BpmnUpgradeDto bpmnUpgradeDto = new BpmnUpgradeDto();
        bpmnUpgradeDto.setFile(new File("src/test/resources/Test.bpmn"));
        when(bpmnWebClient.upgradeBPMN(any(BpmnUpgradeDto.class))).thenReturn(Uni.createFrom().nullItem());
        Uni<BpmnDTO> result = bpmnService.upgradeBPMN(bpmnUpgradeDto);

        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }
}
