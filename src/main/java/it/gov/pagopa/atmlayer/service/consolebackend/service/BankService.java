package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankPresentationDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BankUpdateDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;

public interface BankService {

    Uni<BankPresentationDTO> insert(BankInsertionDTO bankInsertionDTO, ContainerRequestContext containerRequestContext);

    Uni<BankPresentationDTO> update(BankUpdateDTO bankUpdateDTO, ContainerRequestContext containerRequestContext);

    Uni<Void> disable(String acquirerId, ContainerRequestContext containerRequestContext);

    Uni<PageInfo<BankDTO>> search(int pageIndex, int pageSize, String acquirerId, String denomination, String clientId);

    Uni<BankPresentationDTO> findByAcquirerId(String acquirerId);
}
