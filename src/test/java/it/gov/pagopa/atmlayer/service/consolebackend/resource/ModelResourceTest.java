package it.gov.pagopa.atmlayer.service.consolebackend.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import it.gov.pagopa.atmlayer.service.consolebackend.service.ModelService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ModelResourceTest {

    @InjectMock
    ModelService modelService;

    @Test
    void testWorkflowFiltered() {



    }
}