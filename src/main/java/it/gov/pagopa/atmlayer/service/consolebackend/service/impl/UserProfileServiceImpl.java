package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.UserProfileWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserProfilesInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserProfileService;
import it.gov.pagopa.atmlayer.service.consolebackend.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    @Inject
    @RestClient
    UserProfileWebClient userProfileWebClient;

    @Override
    public Uni<List<UserProfilesDTO>> insertUserProfiles(UserProfilesInsertionDTO userProfilesInsertionDTO, ContainerRequestContext containerRequestContext) {
        return userProfileWebClient.insert(userProfilesInsertionDTO)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento associazione tra utente e profili"));
    }

    @Override
    public Uni<UserProfilesDTO> findById(String userId, int profileId) {
        return userProfileWebClient.getById(userId, profileId);
    }

    @Override
    public Uni<Void> deleteUserProfiles(String userId, int profileId) {
        return userProfileWebClient.deleteUserProfiles(userId, profileId);
    }
}
