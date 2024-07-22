package it.gov.pagopa.atmlayer.service.consolebackend.clientdto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInsertionWithProfilesDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUserInsertionWithProfilesDTO() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("email@domain.com")
                .name("John")
                .surname("Doe")
                .profileIds(List.of(1, 2, 3))
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "There should be no violations");
    }

    @Test
    void testInvalidEmail() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("invalid-email")
                .profileIds(List.of(1))
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("must be an email address in the correct format", violations.iterator().next().getMessage());
    }

    @Test
    void testBlankUserId() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("")
                .profileIds(List.of(1))
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullProfileIds() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("email@domain.com")
                .profileIds(null)
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testEmptyProfileIds() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("email@domain.com")
                .profileIds(List.of())
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 2147483647", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidProfileIdRange() {
        UserInsertionWithProfilesDTO dto = UserInsertionWithProfilesDTO.builder()
                .userId("email@domain.com")
                .profileIds(List.of(0, -1))
                .build();

        Set<ConstraintViolation<UserInsertionWithProfilesDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size());
    }
}
