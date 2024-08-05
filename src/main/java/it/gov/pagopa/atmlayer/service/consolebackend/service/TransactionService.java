package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionService {
    Uni<TransactionDTO> insert(TransactionInsertionDTO transactionInsertionDTO);

    Uni<PageInfo<TransactionDTO>> searchTransaction(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transactionStatus, Timestamp startTime, Timestamp endTime);

    Uni<List<TransactionDTO>> getAllTransaction();

    Uni<TransactionDTO> update(TransactionUpdateDTO transactionUpdateDTO);

    Uni<Void> delete(String transactionId);
}
