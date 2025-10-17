package fi.smartbass.ycbr.register;

import fi.smartbass.ycbr.register.Boat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Valid boat should have no violations")
    void validBoatShouldHaveNoViolations() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Missing owner should fail validation")
    void missingOwnerShouldFailValidation() {
        Boat boat = new Boat(1L, "", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("owner"));
    }

    @Test
    @DisplayName("Missing name should fail validation")
    void missingNameShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    @DisplayName("Null LOA should fail validation")
    void nullLoaShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", null, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    @DisplayName("Negative LOA should fail validation")
    void negativeLoaShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", -1.0, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("loa"));
    }

    @Test
    @DisplayName("Zero draft should fail validation")
    void zeroDraftShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 0.0, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("draft"));
    }

    @Test
    @DisplayName("Negative beam should fail validation")
    void negativeBeamShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, -2.0, 4000.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("beam"));
    }

    @Test
    @DisplayName("Zero deplacement should fail validation")
    void zeroDeplacementShouldFailValidation() {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 0.0, "VP", "1988", null, null, null, null, 0);
        Set<ConstraintViolation<Boat>> violations = validator.validate(boat);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("deplacement"));
    }
}
