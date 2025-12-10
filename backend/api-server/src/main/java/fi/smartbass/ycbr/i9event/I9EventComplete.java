package fi.smartbass.ycbr.i9event;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

public record I9EventComplete(
        Long id,
        String place,
        String day,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String starts,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String ends,
        Set<BoatBookingDTO> boats,
        Set<InspectorRegistrationDTO> inspectors) {}
