package it.gov.pagopa.atmlayer.service.consolebackend.service;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;

import java.util.List;

public interface UserService {

    Uni<UserDTO> createUser(String userId);

    Uni<Void> deleteUser(String userId);

    Uni<UserDTO> getUserById(String userId);

    Uni<List<UserDTO>> getAllUsers();

}
