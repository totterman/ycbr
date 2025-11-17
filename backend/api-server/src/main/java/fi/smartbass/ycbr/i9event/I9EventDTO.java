package fi.smartbass.ycbr.i9event;

import org.springframework.format.annotation.DateTimeFormat;

public record I9EventDTO(
        Long id,
        String place,
        String day,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String starts,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String ends,
        int boats,
        int inspectors) {}
