package it.gov.pagopa.atmlayer.service.consolebackend.service.impl;

import io.smallrye.mutiny.Uni;
import it.gov.pagopa.atmlayer.service.consolebackend.client.ProfileWebClient;
import it.gov.pagopa.atmlayer.service.consolebackend.clientdto.ProfileDTO;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ProfileService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class ProfileServiceImpl implements ProfileService {

    @Inject
    @RestClient
    ProfileWebClient profileWebClient;

    @Override
    public Uni<List<ProfileDTO>> getAll() {
        return profileWebClient.getAll();
    }
}
