// File: backend/resource-server/src/test/java/fi/smartbass/ycbr/register/BoatControllerTest.java
package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoatControllerTest {

    @Test
    void getBoats_returnsExpectedList() {
        BoatController controller = new BoatController();
        Authentication mockAuth = mock(Authentication.class);

        List<BoatController.BoatDto> boats = controller.getBoats(mockAuth);

        assertNotNull(boats);
        assertEquals(11, boats.size());

        BoatController.BoatDto first = boats.get(0);
        assertEquals(1000001L, first.id());
        assertEquals("BoatName", first.name());
        assertEquals("Sign123", first.sign());
        assertEquals("MakeX", first.make());
        assertEquals("ModelY", first.model());
        assertEquals(10.5, first.loa());
        assertEquals(2.5, first.draft());
        assertEquals(3.5, first.beam());
        assertEquals(5000.0, first.deplacement());
        assertEquals("OwnerName", first.owner());
    }

    @Test
    void boatDto_recordFields() {
        BoatController.BoatDto dto = new BoatController.BoatDto(42L, "TestBoat", "Sign999", "MakeZ", "ModelQ", 11.1, 2.2, 3.3, 4444.0, "OwnerX");

        assertEquals(42L, dto.id());
        assertEquals("TestBoat", dto.name());
        assertEquals("Sign999", dto.sign());
        assertEquals("MakeZ", dto.make());
        assertEquals("ModelQ", dto.model());
        assertEquals(11.1, dto.loa());
        assertEquals(2.2, dto.draft());
        assertEquals(3.3, dto.beam());
        assertEquals(4444.0, dto.deplacement());
        assertEquals("OwnerX", dto.owner());
    }

    @Test
    void getBoats_returnsAllBoats() {
        BoatController controller = new BoatController();
        Authentication mockAuth = mock(Authentication.class);
        List<BoatController.BoatDto> boats = controller.getBoats(mockAuth);

        assertNotNull(boats);
        assertEquals(11, boats.size());
        assertEquals("BoatName", boats.get(0).name());
    }

    @Test
    void getBoat_returnsCorrectBoat() {
        BoatController controller = new BoatController();
        Authentication mockAuth = mock(Authentication.class);
        BoatController.BoatDto boat = controller.getBoat(mockAuth, 1000001L);

        assertNotNull(boat);
        assertEquals(1000001L, boat.id());
        assertEquals("BoatName", boat.name());
    }

    @Test
    void getBoat_returnsNullForUnknownId() {
        BoatController controller = new BoatController();
        Authentication mockAuth = mock(Authentication.class);
        BoatController.BoatDto boat = controller.getBoat(mockAuth, 9999999L);

        assertNull(boat);
    }
}
