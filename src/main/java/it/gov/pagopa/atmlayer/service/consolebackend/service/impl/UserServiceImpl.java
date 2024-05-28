package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.UserWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
@Slf4j
public class UserServiceImpl implements UserService {

    @Inject
    @RestClient
    UserWebClient userWebClient;

    @Override
    public Uni<UserDTO> createUser(String userId) {
        return userWebClient.createUser(userId);
    }

    @Override
    public Uni<Void> deleteUser(String userId) {
        return userWebClient.delete(userId);
    }

    @Override
    public Uni<UserDTO> getUserById(String userId) {
        return userWebClient.getById(userId);
    }

    @Override
    public Uni<List<UserDTO>> getAllUsers() {
        return userWebClient.getAll();
    }

}
