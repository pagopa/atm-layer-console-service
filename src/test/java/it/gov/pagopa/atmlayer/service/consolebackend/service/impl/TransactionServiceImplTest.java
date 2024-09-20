package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.TransactionWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionWebClient transactionWebClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsert() {
        TransactionDTO transactionDTO = new TransactionDTO();
        TransactionInsertionDTO transactionInsertionDTO = new TransactionInsertionDTO();

        when(transactionWebClient.insert(any(TransactionInsertionDTO.class))).thenReturn(Uni.createFrom().item(transactionDTO));

        Uni<TransactionDTO> result = transactionService.insert(transactionInsertionDTO);

        assertEquals(transactionDTO, result.await().indefinitely());
    }

    @Test
    void testSearchTransaction() {
        int pageIndex = 0, pageSize = 10;
        String transactionId = "txn123", functionType = "funcType", acquirerId = "acq123", branchId = "branch123", terminalId = "term123", transactionStatus = "completed";
        Timestamp startTime = new Timestamp(System.currentTimeMillis()), endTime = new Timestamp(System.currentTimeMillis() + 1000);

        TransactionDTO transactionDTO = new TransactionDTO();
        PageInfo<TransactionDTO> pageInfo = new PageInfo<>(pageIndex, pageSize, 1, 1, List.of(transactionDTO));

        when(transactionWebClient.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime))
                .thenReturn(Uni.createFrom().item(pageInfo));

        Uni<PageInfo<TransactionDTO>> result = transactionService.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime);

        assertEquals(pageInfo, result.await().indefinitely());
    }


    @Test
    void testUpdate() {
        TransactionDTO transactionDTO = new TransactionDTO();
        TransactionUpdateDTO transactionUpdateDTO = new TransactionUpdateDTO();

        when(transactionWebClient.update(any(TransactionUpdateDTO.class))).thenReturn(Uni.createFrom().item(transactionDTO));

        Uni<TransactionDTO> result = transactionService.update(transactionUpdateDTO);

        assertEquals(transactionDTO, result.await().indefinitely());
    }

    @Test
    void testDelete() {

        when(transactionWebClient.delete(anyString())).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = transactionService.delete(anyString());

        assertEquals(result, Uni.createFrom().voidItem());
    }

}
