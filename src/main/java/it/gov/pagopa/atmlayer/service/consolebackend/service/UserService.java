package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfileDto;

public interface UserService {
    Uni<UserProfileDto> findByUserId(String userId);

}
