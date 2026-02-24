package fi.smartbass.ycbr.register;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BoatValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;
    private static UUID boatId;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        boatId = UUID.randomUUID();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void validBoatShouldHaveNoViolations() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).isEmpty();
    }

    @Test
    void missingOwnerShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, null, null, null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("owner"));
    }

    @Test
    void missingNameShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", null, "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void missingClubShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, null, "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("club"));
    }

    @Test
    void missingCertShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", null, "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cert"));
    }

    @Test
    void negativeLoaShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", -9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    void zeroDraftShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 0.0, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("draft"));
    }

    @Test
    void negativeBeamShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, -3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("beam"));
    }

    @Test
    void zeroDeplacementShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 0.0, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("deplacement"));
    }
}
