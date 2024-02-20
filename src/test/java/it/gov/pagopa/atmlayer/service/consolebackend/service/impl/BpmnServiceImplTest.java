package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BpmnWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BpmnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static it.gov.pagopa.atmlayer.service.consolebackend.enums.StatusEnum.CREATED;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@QuarkusTest
public class BpmnServiceImplTest {

    @Mock
    private BpmnWebClient bpmnWebClient;

    @Mock
    private BpmnService bpmnService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void  getBpmnFilteredTest(){
        UUID uuid = UUID.randomUUID();
        when(bpmnWebClient.getBpmnFiltered(anyInt(), anyInt(), anyString(),  anyString(), anyString(), eq(uuid), eq(uuid),anyString(), anyString(),anyString(),anyString(),anyString(),eq(CREATED),anyString(),anyString(),anyString(), anyString()))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<PageInfo<BpmnVersionFrontEndDTO>> result = bpmnService.getBpmnFiltered(0, 1, "functionType", "modelVersion", "1", uuid, uuid, "camundaDefinitionId", "definitionKey", "deployedFileName", "resource", "sha256", CREATED, "acquirerId", "branchId", "terminalId", "fileName");
        
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }
}
