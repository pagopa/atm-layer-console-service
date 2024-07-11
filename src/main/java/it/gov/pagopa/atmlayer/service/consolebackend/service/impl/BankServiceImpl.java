package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.BankWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.BankService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Slf4j
public class BankServiceImpl implements BankService {

    @Inject
    @RestClient
    BankWebClient bankWebClient;

    @Override
    public Uni<BankDTO> insert(BankInsertionDTO bankInsertionDTO) {
        return bankWebClient.insert(bankInsertionDTO);
    }

    @Override
    public Uni<BankDTO> update(BankInsertionDTO bankInsertionDTO) {
        return bankWebClient.update(bankInsertionDTO);
    }

    @Override
    public Uni<Void> disable(String acquirerId) {
        return bankWebClient.disable(acquirerId);
    }

    @Override
    public Uni<PageInfo<BankDTO>> search(int pageIndex, int pageSize, String acquirerId, String denomination, int rateMin, int rateMax, String clientId) {
        return bankWebClient.search(pageIndex, pageSize, acquirerId, denomination, rateMin, rateMax, clientId);
    }

}
