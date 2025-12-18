package fi.smartbass.ycbr.i9event;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

public record I9EventDto(
        UUID i9eventId,
        String place,
        String day,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String starts,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String ends,
        int boats,
        int inspectors) {}
