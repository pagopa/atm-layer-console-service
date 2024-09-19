package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ProfileDTO;

import java.util.List;

public interface ProfileService {

    Uni<List<ProfileDTO>> getAll();

}
