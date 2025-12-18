package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoatServiceTest {

    @Mock
    private BoatRepository boatRepository;

    @InjectMocks
    private BoatService boatService;

    @Spy
    BoatMapper mapper = Mappers.getMapper(BoatMapper.class);

    private final UUID boatId1 = UUID.randomUUID();
    private final UUID boatId2 = UUID.randomUUID();
    private final UUID boatId3 = UUID.randomUUID();

    @Test
    @DisplayName("Find all boats OK")
    void getAllBoats_returnsAllBoats() {
        when(boatRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<BoatDto> result = boatService.getAllBoats();
        assertThat(result).isEmpty();
        verify(boatRepository).findAll();
    }

    @Test
    @DisplayName("Find boatId by UUID is OK")
    void getBoatByBoatId_returnsBoat_whenFound() {
        BoatEntity boat = new BoatEntity(boatId1, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.findByBoatId(boatId1)).thenReturn(Optional.of(boat));
        BoatDto result = boatService.getBoatByBoatId(boatId1);
        assertThat(result.name()).isEqualTo(boat.getName());
    }

    @Test
    @DisplayName("Throws if boatId not found by UUID")
    void getBoatByBoatId_throws_whenNotFound() {
        when(boatRepository.findByBoatId(boatId1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> boatService.getBoatByBoatId(boatId1))
                .isInstanceOf(BoatNotFoundException.class);
    }

    @Test
    @DisplayName("Find by owner is OK")
    void getBoatsByOwner_returnsBoats() {
        when(boatRepository.findByOwner("owner")).thenReturn(Collections.emptyList());
        Iterable<BoatDto> result = boatService.getBoatsByOwner("owner");
        assertThat(result).isEmpty();
        verify(boatRepository).findByOwner("owner");
    }

    @Test
    @DisplayName("Creating boatId is OK when inspectionId is null")
    void addBoatToRegister_savesBoat_whenIdIsNull() {
        BoatEntity boat = new BoatEntity(null, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        NewBoatDto dto = new NewBoatDto("owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.save(boat)).thenReturn(boat);
        BoatDto result = boatService.create(dto);
        assertThat(result.sign()).isEqualTo(boat.getSign());
        verify(boatRepository).save(boat);
    }

    @Test
    @DisplayName("Creating boatId throws when inspectionId is not null")
    void create_throws_whenIdExists() {
        BoatEntity boat = new BoatEntity(boatId2, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        BoatDto dto = new BoatDto(boatId2, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.existsByBoatId(boatId2)).thenReturn(true);
        assertThatThrownBy(() -> boatService.create(dto))
                .isInstanceOf(BoatAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Creating saves boatId when not exists in DB")
    void create_saves_whenIdNotExists() {
        BoatEntity boat = new BoatEntity(boatId2, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        NewBoatDto dto = new NewBoatDto("owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.save(any(BoatEntity.class))).thenReturn(boat);
        BoatDto result = boatService.create(dto);
        assertThat(result.sign()).isEqualTo(boat.getSign());
        verify(boatRepository).save(any(BoatEntity.class));
    }

    @Test
    @DisplayName("Deleting boatId is OK")
    void delete_deletes_whenExists() {
        when(boatRepository.existsByBoatId(boatId1)).thenReturn(true);
        boatService.delete(boatId1);
        verify(boatRepository).deleteByBoatId(boatId1);
    }

    @Test
    @DisplayName("Deleting non existing boatId is OK")
    void delete_ok_whenNotExists() {
        when(boatRepository.existsByBoatId(boatId2)).thenReturn(false);
        boatService.delete(boatId2);
        assertThatNoException();
    }

    @Test
    @DisplayName("Updating boatId is OK when boatId exists")
    void upsert_updates_whenExists() {
        Instant createdAt = Instant.now();
        BoatEntity before = new BoatEntity(boatId3, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", createdAt, "createdBy", null, null, 0);
        BoatDto updated = new BoatDto(boatId3, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        BoatEntity after = new BoatEntity(boatId3, "newOwner", "newName", "newSign", "newMake", "newModel", 2.0, 2.0, 2.0, 2.0, "newEngines", "newYear", null, null, null, null, 0);
        when(boatRepository.findByBoatId(boatId3)).thenReturn(Optional.of(before));
        when(boatRepository.save(any(BoatEntity.class))).thenReturn(after);
        BoatDto result = boatService.upsert(boatId3, updated);
        assertThat(result.sign().equals(after.getSign()));
        verify(boatRepository).save(any(BoatEntity.class));
    }

    @Test
    @DisplayName("Updating boatId becomes Save when boatId not exists")
    void upsert_adds_whenNotExists() {
        BoatEntity updated = new BoatEntity(boatId3, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", Instant.now(), null, null, null, 0);
        BoatDto dto = new BoatDto(boatId3, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.findByBoatId(boatId3)).thenReturn(Optional.empty());
        when(boatRepository.save(any(BoatEntity.class))).thenReturn(updated);
        BoatDto result = boatService.upsert(boatId3, dto);
        assertThat(result).isEqualTo(dto);
        verify(boatRepository).save(any(BoatEntity.class));
    }

    @Test
    @DisplayName("Updating boatId throws if UUIDs do not match")
    void upsert_throws_whenMalformed() {
        BoatDto dto = new BoatDto(boatId2, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        assertThatThrownBy(() -> boatService.upsert(boatId3, dto))
                .isInstanceOf(BoatRequestMalformedException.class);
    }

}
