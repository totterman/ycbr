package fi.smartbass.ycbr.i9event;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

public record NewI9EventDto(
        String place,
        String day,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String starts,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        String ends) {}
