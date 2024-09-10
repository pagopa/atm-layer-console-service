package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.enums.UserProfileEnum;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.util.List;

public interface UserService {

    Uni<UserDTO> createUser(UserInsertionDTO userInsertionDTO);

    Uni<Void> deleteUser(String userId);

    Uni<UserDTO> getUserById(String userId);

    Uni<List<UserDTO>> getAllUsers();

    Uni<PageInfo<UserDTO>> getUserFiltered(int pageIndex, int pageSize, String name, String surname, String userId, int profileNumber);

    Uni<Void> checkAuthorizationUser(ContainerRequestContext containerRequestContext, UserProfileEnum userProfileEnum);
    Uni<UserDTO> checkFirstAccess(String userId);

    Uni<UserDTO> updateWithProfiles(UserInsertionWithProfilesDTO userInsertionWithProfilesDTO);

    Uni<UserDTO> insertWithProfiles(UserInsertionWithProfilesDTO userInsertionWithProfilesDTO);

}
