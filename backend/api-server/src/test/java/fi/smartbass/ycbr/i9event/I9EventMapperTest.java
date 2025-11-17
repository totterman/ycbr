package fi.smartbass.ycbr.i9event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class I9EventMapperTest {

    private I9EventMapper mapper = Mappers.getMapper(I9EventMapper.class);



    @Test
    @DisplayName("Maps entity to DTO")
    void toDTO() {
        final I9Event entity = new I9Event(1021L, "Björkholmen", OffsetDateTime.parse("2026-05-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        assertNotNull(mapper);
        I9EventDTO dto = mapper.toDTO(entity);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getPlace(), dto.place());
        assertEquals(entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), dto.starts());
        assertEquals(entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), dto.ends());
        assertEquals(entity.getBoats().size(), dto.boats());
        assertEquals(entity.getInspectors().size(), dto.inspectors());
    }

    @Test
    @DisplayName("Maps DTO to entity")
    void toEntity() {
        final I9EventDTO dto = new I9EventDTO(1021L, "Björkholmen", "2026-05-15T00:00:00.000+02:00", "2026-05-15T10:00:00+02:00", "2026-05-15T16:00:00+02:00", 0, 0);
        assertNotNull(mapper);
        I9Event entity = mapper.toEntity(dto);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.place(), entity.getPlace());
        assertEquals(dto.starts(), entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(dto.ends(), entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(dto.boats(), entity.getBoats().size());
        assertEquals(dto.inspectors(), entity.getInspectors().size());
    }
}