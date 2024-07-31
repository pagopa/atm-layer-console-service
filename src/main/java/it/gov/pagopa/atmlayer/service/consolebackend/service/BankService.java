package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;

public interface BankService {

    Uni<BankPresentationDTO> insert(BankInsertionDTO bankInsertionDTO);

    Uni<BankPresentationDTO> update(BankInsertionDTO bankInsertionDTO);

    Uni<Void> disable(String acquirerId);

    Uni<PageInfo<BankDTO>> search(int pageIndex, int pageSize, String acquirerId, String denomination, String clientId);

    Uni<BankPresentationDTO> findByAcquirerId(String acquirerId);
}
