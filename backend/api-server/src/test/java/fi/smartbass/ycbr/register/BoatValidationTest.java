package fi.smartbass.ycbr.register;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BoatValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void validBoatShouldHaveNoViolations() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).isEmpty();
    }

    @Test
    void missingOwnerShouldFailValidation() {
        Boat boat = new Boat(1L, "", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("owner"));
    }

    @Test
    void missingNameShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void nullLoaShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", null, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    void negativeLoaShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", -1.0, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    void zeroDraftShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 0.0, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("draft"));
    }

    @Test
    void negativeBeamShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, -2.0, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("beam"));
    }

    @Test
    void zeroDeplacementShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 0.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("deplacement"));
    }
}
