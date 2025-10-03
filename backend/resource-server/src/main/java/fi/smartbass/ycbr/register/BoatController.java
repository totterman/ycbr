package fi.smartbass.ycbr.register;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoatController {

    private final Log LOGGER = LogFactory.getLog(BoatController.class);

    public BoatController() { }

    private static final List<BoatDto> BOATS = List.of(
        new BoatDto(1000001L, "BoatName", "Sign123", "MakeX", "ModelY", 10.5, 2.5, 3.5, 5000.0, "OwnerName"),
        new BoatDto(1000002L, "AnotherBoat", "Sign456", "MakeA", "ModelB", 12.0, 3.0, 4.0, 6000.0, "AnotherOwner"),
        new BoatDto(1000003L, "ThirdBoat", "Sign789", "MakeC", "ModelD", 15.0, 4.0, 5.0, 7000.0, "ThirdOwner"),
        new BoatDto(1000004L, "FourthBoat", "Sign012", "MakeE", "ModelF", 20.0, 5.0, 6.0, 8000.0, "FourthOwner"),
        new BoatDto(1000005L, "FifthBoat", "Sign345", "MakeG", "ModelH", 25.0, 6.0, 7.0, 9000.0, "FifthOwner"),
        new BoatDto(1000006L, "SixthBoat", "Sign678", "MakeI", "ModelJ", 30.0, 7.0, 8.0, 10000.0, "SixthOwner"),
        new BoatDto(1000007L, "SeventhBoat", "Sign901;", "MakeK", "ModelL", 35.0, 8.0, 9.0, 11000.0, "SeventhOwner"),
        new BoatDto(1000008L, "EighthBoat", "Sign234", "MakeM", "ModelN", 40.0, 9.0, 10.0, 12000.0, "EighthOwner"),
        new BoatDto(1000009L, "NinthBoat", "Sign567", "MakeO", "ModelP", 45.0, 10.0, 11.0, 13000.0, "NinthOwner"),
        new BoatDto(10000010L, "TenthBoat", "Sign890", "MakeQ", "ModelR", 50.0, 11.0, 12.0, 14000.0, "TenthOwner"),
        new BoatDto(10000011L, "EleventhBoat", "Sign111", "MakeS", "ModelT", 55.0, 12.0, 13.0, 15000.0, "EleventhOwner")
    );

    private static final Map<Long, BoatDto> boatMap = BOATS.stream().collect(Collectors.toMap(BoatDto::id, b -> b));

    @GetMapping("/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public List<BoatDto> getBoats(Authentication auth) {
        LOGGER.info("getBoats() called: " + auth);
        return BOATS;
    }

    @GetMapping("/boats/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public BoatDto getBoat(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getBoat(" + id + ") called: " + auth);
        return boatMap.get(id);
    }

    public static record BoatDto(Long id, String name, String sign, String make, String model, double loa, double draft, double beam, double deplacement, String owner) {}
}
