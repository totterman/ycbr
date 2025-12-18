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
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).isEmpty();
    }

    @Test
    void missingOwnerShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("owner"));
    }

    @Test
    void missingNameShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void nullLoaShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", null, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    void negativeLoaShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", -1.0, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    void zeroDraftShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 0.0, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("draft"));
    }

    @Test
    void negativeBeamShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, -2.0, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("beam"));
    }

    @Test
    void zeroDeplacementShouldFailValidation() {
        BoatEntity boat = new BoatEntity(boatId, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 0.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<BoatEntity>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("deplacement"));
    }
}
