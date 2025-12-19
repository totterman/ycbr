package fi.smartbass.ycbr.i9event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class I9EventMapperTest {

    private I9EventMapper mapper = Mappers.getMapper(I9EventMapper.class);



    @Test
    @DisplayName("Maps entity to DTO")
    void toDTO() {
        final I9EventEntity entity = new I9EventEntity(UUID.randomUUID(), "Björkholmen", OffsetDateTime.parse("2026-05-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        assertNotNull(mapper);
        I9EventDto dto = mapper.toDTO(entity);
        assertEquals(entity.getI9eventId(), dto.i9eventId());
        assertEquals(entity.getPlace(), dto.place());
        assertEquals(entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), dto.starts());
        assertEquals(entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), dto.ends());
        assertEquals(entity.getBoats().size(), dto.boats());
        assertEquals(entity.getInspectors().size(), dto.inspectors());
    }

    @Test
    @DisplayName("Maps DTO to entity")
    void toEntity() {
        final I9EventDto dto = new I9EventDto(UUID.randomUUID(), "Björkholmen", "2026-05-15T00:00:00.000+02:00", "2026-05-15T10:00:00+02:00", "2026-05-15T16:00:00+02:00", 0, 0);
        assertNotNull(mapper);
        I9EventEntity entity = mapper.toEntity(dto);
        assertEquals(dto.i9eventId(), entity.getI9eventId());
        assertEquals(dto.place(), entity.getPlace());
        assertEquals(dto.starts(), entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(dto.ends(), entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(dto.boats(), entity.getBoats().size());
        assertEquals(dto.inspectors(), entity.getInspectors().size());
    }

    @Test
    @DisplayName("Maps New DTO to entity")
    void toNewEntity() {
        final NewI9EventDto dto = new NewI9EventDto("Björkholmen", "2026-05-15T00:00:00.000+02:00", "2026-05-15T10:00:00+02:00", "2026-05-15T16:00:00+02:00");
        assertNotNull(mapper);
        I9EventEntity entity = mapper.toEntity(dto);
        assertNull(entity.getI9eventId());
        assertEquals(dto.place(), entity.getPlace());
        assertEquals(dto.starts(), entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(dto.ends(), entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(0, entity.getBoats().size());
        assertEquals(0, entity.getInspectors().size());
    }

}