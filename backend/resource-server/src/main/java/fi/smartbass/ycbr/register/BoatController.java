package fi.smartbass.ycbr.register;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boats")
public class YcbrController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public List<BoatDto> getBoats() {
        return new BoatDto("Boaty McBoatface", "ABC-123", "Yamaha", "Model X", 10.5, 1.5, 3.2, 5000).intoList();
    }

    public static record BoatDto(String name, String sign, String make, String model, double loa, double depth, double beam, double deplacement) {}
}
