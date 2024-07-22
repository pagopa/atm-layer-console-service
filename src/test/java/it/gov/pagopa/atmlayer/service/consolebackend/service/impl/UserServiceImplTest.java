package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.UserWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.BpmnVersionFrontEndDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.UserInsertionWithProfilesDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.model.PageInfo;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserWebClient userWebClient;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    ContainerRequestContext containerRequestContext;

    @Test
    void testCreateUser() {
        UserInsertionDTO userInsertionDTO = new UserInsertionDTO();
        UserDTO userDTO = new UserDTO();
        when(userWebClient.createUser(any(UserInsertionDTO.class))).thenReturn(Uni.createFrom().item(userDTO));

        Uni<UserDTO> result = userServiceImpl.createUser(userInsertionDTO);

        assertEquals(userDTO, result.await().indefinitely());
        verify(userWebClient).createUser(userInsertionDTO);
    }

    @Test
    void testDeleteUser() {
        String userId = "test-user-id";
        when(userWebClient.delete(anyString())).thenReturn(Uni.createFrom().voidItem());

        Uni<Void> result = userServiceImpl.deleteUser(userId);

        assertNull(result.await().indefinitely());
        verify(userWebClient).delete(userId);
    }

    @Test
    void testGetUserById() {
        String userId = "test-user-id";
        UserDTO userDTO = new UserDTO();
        when(userWebClient.getById(anyString())).thenReturn(Uni.createFrom().item(userDTO));

        Uni<UserDTO> result = userServiceImpl.getUserById(userId);

        assertEquals(userDTO, result.await().indefinitely());
        verify(userWebClient).getById(userId);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> userList = List.of(new UserDTO());
        when(userWebClient.getAll()).thenReturn(Uni.createFrom().item(userList));

        Uni<List<UserDTO>> result = userServiceImpl.getAllUsers();

        assertEquals(userList, result.await().indefinitely());
        verify(userWebClient).getAll();
    }

    @Test
    void testGetUserFiltered() {
        List<UserDTO> userDTOList = List.of(new UserDTO());
        PageInfo<UserDTO> pageInfo = new PageInfo<>(0, 10, 1, 1, userDTOList);
        when(userWebClient.getUserFiltered(anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(Uni.createFrom().item(pageInfo));

        Uni<PageInfo<UserDTO>> result = userServiceImpl.getUserFiltered(0, 10, "John", "Doe", "test-user-id");

        assertEquals(pageInfo, result.await().indefinitely());
        verify(userWebClient).getUserFiltered(0, 10, "John", "Doe", "test-user-id");
    }

    @Test
    void testUpdateWithProfiles() {
        UserInsertionWithProfilesDTO userInsertionWithProfilesDTO = UserInsertionWithProfilesDTO.builder()
                .userId("a@b.it")
                .name("a")
                .surname("b")
                .profileIds(List.of(1, 2, 3))
                .build();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("a@b.it");
        userDTO.setName("a");
        userDTO.setSurname("b");
        when(userWebClient.updateWithProfiles(any(UserInsertionWithProfilesDTO.class))).thenReturn(Uni.createFrom().item(userDTO));

        Uni<UserDTO> result = userServiceImpl.updateWithProfiles(userInsertionWithProfilesDTO);

        assertEquals(userDTO, result.await().indefinitely());
        verify(userWebClient).updateWithProfiles(userInsertionWithProfilesDTO);
    }

    @Test
    void testCheckFirstAccess() {
        String userId = "test-user-id";
        UserDTO userDTO = new UserDTO();
        when(userWebClient.firstAccess(anyString())).thenReturn(Uni.createFrom().item(userDTO));

        Uni<UserDTO> result = userServiceImpl.checkFirstAccess(userId);

        assertEquals(userDTO, result.await().indefinitely());
        verify(userWebClient).firstAccess(userId);
    }
}
