package fi.smartbass.ycbr;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ycbr")
@AllArgsConstructor
public class YcbrController {

    @GetMapping
    public YcbrDto getYcbr() {
        return new YcbrDto("Hello from YCBR!");
    }

    @GetMapping("/staff")
    @PreAuthorize("hasAuthority('staff')")
    public YcbrDto getYcbrStaff() {
        return new YcbrDto("Hello, staff!");
    }

    @GetMapping("/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public YcbrDto getYcbrBoats() {
        return new YcbrDto("Hello, boatowner!");
    }

    @GetMapping("/inspect")
    @PreAuthorize("hasAuthority('inspector')")
    public YcbrDto getYcbrInspections() {
        return new YcbrDto("Hello, inspector!");
    }

    public static record YcbrDto(String greeting) {}
}
