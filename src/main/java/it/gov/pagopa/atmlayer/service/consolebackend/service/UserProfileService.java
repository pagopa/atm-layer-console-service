package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesInsertionDTO;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;

public interface UserProfileService {

    Uni<List<UserProfilesDTO>> insertUserProfiles(UserProfilesInsertionDTO userProfilesInsertionDTO, ContainerRequestContext containerRequestContext);

    Uni<UserProfilesDTO> findById(String userId, int profileId);

    Uni<Void> deleteUserProfiles(String userId, int profileId);

}
