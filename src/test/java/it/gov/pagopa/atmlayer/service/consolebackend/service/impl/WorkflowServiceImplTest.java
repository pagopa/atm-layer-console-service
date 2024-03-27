package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import it.gov.pagopa.atmlayer.service.consolebackend.client.WorkflowWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.FileS3Dto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceCreationDto;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.WorkflowResourceFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.DeployableResourceType;
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
public class WorkflowServiceImplTest {
    @Mock
    private WorkflowWebClient workflowWebClient;

    @InjectMocks
    private WorkflowServiceImpl workflowService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getWorkflowResourceFilteredTest(){
        UUID uuid = UUID.randomUUID();
        WorkflowResourceFrontEndDTO bpmnVersionFrontEndDTO = new WorkflowResourceFrontEndDTO();
        DeployableResourceType deployableResourceType = mock(DeployableResourceType.class);
        List<WorkflowResourceFrontEndDTO> dtoList = new ArrayList<>();
        dtoList.add(bpmnVersionFrontEndDTO);
        PageInfo<WorkflowResourceFrontEndDTO> response = new PageInfo<>(0, 1, 1, 1, dtoList);
        when(workflowWebClient.getWorkflowResourceFiltered(anyInt(), anyInt(), eq(CREATED), eq(uuid),  anyString(), anyString(),  any(DeployableResourceType.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(uuid), anyString()))
                .thenReturn(Uni.createFrom().item(response));
        workflowService.getWorkflowResourceFiltered(0, 1, CREATED, uuid, "deployedFileName", "definitionKey", deployableResourceType, "sha256", "definitionVersionCamunda", "camundaDefinition", "description", "resource", uuid, "filename")
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }

    @Test
    void downloadFrontEndTest(){
        UUID uuid = UUID.randomUUID();
        when(workflowWebClient.downloadFrontEnd(uuid))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<FileS3Dto> result = workflowService.downloadFrontEnd(uuid);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void createTest(){
        WorkflowResourceCreationDto workflowResourceCreationDto = mock(WorkflowResourceCreationDto.class);
        when(workflowWebClient.create(workflowResourceCreationDto))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<WorkflowResourceDTO> result = workflowService.create(workflowResourceCreationDto);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void deployTest(){
        UUID uuid = UUID.randomUUID();
        when(workflowWebClient.deploy(uuid))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<WorkflowResourceDTO> result = workflowService.deploy(uuid);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void updateTest(){
        UUID uuid = UUID.randomUUID();
        File file = mock(File.class);
        when(workflowWebClient.update(file, uuid))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<WorkflowResourceDTO> result = workflowService.update(file, uuid);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void rollbackTest(){
        UUID uuid = UUID.randomUUID();
        when(workflowWebClient.rollback(uuid))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<WorkflowResourceDTO> result = workflowService.rollback(uuid);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }

    @Test
    void disableTest(){
        UUID uuid = UUID.randomUUID();
        when(workflowWebClient.disable(uuid))
                .thenReturn(Uni.createFrom().nullItem());
        Uni<Void> result = workflowService.disable(uuid);
        assertNotNull(result);
        assertNull(result.await().indefinitely());
    }
}
