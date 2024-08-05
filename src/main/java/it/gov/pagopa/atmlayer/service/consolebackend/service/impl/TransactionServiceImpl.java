package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.TransactionWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.transactiondto.TransactionUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.TransactionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
public class TransactionServiceImpl implements TransactionService {

    @Inject
    @RestClient
    TransactionWebClient transactionWebClient;

    @Override
    public Uni<TransactionDTO> insert(TransactionInsertionDTO transactionInsertionDTO) {
        return transactionWebClient.insert(transactionInsertionDTO);
    }

    public Uni<PageInfo<TransactionDTO>> searchTransaction(int pageIndex, int pageSize, String transactionId, String functionType, String acquirerId, String branchId, String terminalId, String transactionStatus, Timestamp startTime, Timestamp endTime) {
        return transactionWebClient.searchTransaction(pageIndex, pageSize, transactionId, functionType, acquirerId, branchId, terminalId, transactionStatus, startTime, endTime);
    }

    public Uni<List<TransactionDTO>> getAllTransaction() {
        return transactionWebClient.getAllTransaction();
    }

    public Uni<TransactionDTO> update(TransactionUpdateDTO transactionUpdateDTO) {
        return transactionWebClient.update(transactionUpdateDTO);
    }

    public Uni<Void> delete (String transactionId) {
        return transactionWebClient.delete(transactionId);
    }
}
