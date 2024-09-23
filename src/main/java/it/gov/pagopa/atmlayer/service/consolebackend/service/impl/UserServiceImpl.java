package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.UserWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.AppErrorCodeEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.exception.AtmLayerException;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import it.gov.pagopa.atmlayer.service.consolebackend.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.getEmailJWT;
import static it.gov.pagopa.atmlayer.service.consolebackend.utils.HeadersUtils.havePermission;

@ApplicationScoped
@Slf4j
public class UserServiceImpl implements UserService {

    @Inject
    @RestClient
    UserWebClient userWebClient;

    @Override
    public Uni<UserDTO> createUser(UserInsertionDTO userInsertionDTO, ContainerRequestContext containerRequestContext) {
        return userWebClient.createUser(userInsertionDTO)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento utente"));
    }

    @Override
    public Uni<Void> deleteUser(String userId, ContainerRequestContext containerRequestContext) {
        return userWebClient.delete(userId)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Cancellazione utente"));
    }

    @Override
    public Uni<UserDTO> getUserById(String userId) {
        return userWebClient.getById(userId);
    }

    @Override
    public Uni<List<UserDTO>> getAllUsers() {
        return userWebClient.getAll();
    }

    @Override
    public Uni<PageInfo<UserDTO>> getUserFiltered(int pageIndex, int pageSize, String name, String surname, String userId, int profileNumber) {
        return userWebClient.getUserFiltered(pageIndex, pageSize, name, surname, userId, profileNumber);
    }

    @Override
    public Uni<Void> checkAuthorizationUser(ContainerRequestContext containerRequestContext, UserProfileEnum userProfileEnum) {
        return getUserById(getEmailJWT(containerRequestContext))
                .onItem()
                .transform(userProfile -> {
                    if (!havePermission(userProfile, userProfileEnum)) {
                        throw new AtmLayerException("Accesso negato!", Response.Status.UNAUTHORIZED, AppErrorCodeEnum.ATMLCB_401);
                    }
                    return null;
                });
    }

    @Override
    public Uni<UserDTO> checkFirstAccess(String userId) {
        return userWebClient.firstAccess(userId);
    }

    @Override
    public Uni<UserDTO> updateWithProfiles(UserInsertionWithProfilesDTO userInsertionWithProfilesDTO, ContainerRequestContext containerRequestContext) {
        return userWebClient.updateWithProfiles(userInsertionWithProfilesDTO)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Aggiornamento utente con profili"));
    }

    @Override
    public Uni<UserDTO> insertWithProfiles(UserInsertionWithProfilesDTO userInsertionWithProfilesDTO, ContainerRequestContext containerRequestContext) {
        return userWebClient.insertWithProfiles(userInsertionWithProfilesDTO)
                .onItem().invoke(createdBPMN -> LogUtils.logOperation(containerRequestContext, "Inserimento utente con profili"));
    }

}
