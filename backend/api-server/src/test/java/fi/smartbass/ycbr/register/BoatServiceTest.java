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

    @Test
    void getAllBoats_returnsAllBoats() {
        when(boatRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<BoatDTO> result = boatService.getAllBoats();
        assertThat(result).isEmpty();
        verify(boatRepository).findAll();
    }

    @Test
    void getBoatById_returnsBoat_whenFound() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));
        BoatDTO result = boatService.getBoatById(1L);
        assertThat(result.name()).isEqualTo(boat.getName());
    }

    @Test
    void getBoatById_throws_whenNotFound() {
        when(boatRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> boatService.getBoatById(1L))
                .isInstanceOf(BoatNotFoundException.class);
    }

    @Test
    void getBoatsByOwner_returnsBoats() {
        when(boatRepository.findByOwner("owner")).thenReturn(Collections.emptyList());
        Iterable<BoatDTO> result = boatService.getBoatsByOwner("owner");
        assertThat(result).isEmpty();
        verify(boatRepository).findByOwner("owner");
    }

    @Test
    void addBoatToRegister_savesBoat_whenIdIsNull() {
        Boat boat = new Boat(null, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        BoatDTO dto = new BoatDTO(null, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.save(boat)).thenReturn(boat);
        BoatDTO result = boatService.create(dto);
        assertThat(result.sign()).isEqualTo(boat.getSign());
        verify(boatRepository).save(boat);
    }

    @Test
    void create_throws_whenIdExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        BoatDTO dto = new BoatDTO(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.existsById(1L)).thenReturn(true);
        assertThatThrownBy(() -> boatService.create(dto))
                .isInstanceOf(BoatAlreadyExistsException.class);
    }

    @Test
    void create_saves_whenIdNotExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        BoatDTO dto = new BoatDTO(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.existsById(1L)).thenReturn(false);
        when(boatRepository.save(boat)).thenReturn(boat);
        BoatDTO result = boatService.create(dto);
        assertThat(result.sign()).isEqualTo(boat.getSign());
        verify(boatRepository).save(boat);
    }

    @Test
    @DisplayName("Deleting boat is OK")
    void delete_deletes_whenExists() {
        when(boatRepository.existsById(1L)).thenReturn(true);
        boatService.delete(1L);
        verify(boatRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deleting non existing boat is OK")
    void delete_ok_whenNotExists() {
        when(boatRepository.existsById(1L)).thenReturn(false);
        boatService.delete(1L);
        assertThatNoException();
    }

    @Test
    void upsert_updates_whenExists() {
        Instant createdAt = Instant.now();
        Boat before = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", createdAt, "createdBy", null, null, 0);
        BoatDTO updated = new BoatDTO(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        Boat after = new Boat(1L, "newOwner", "newName", "newSign", "newMake", "newModel", 2.0, 2.0, 2.0, 2.0, "newEngines", "newYear", null, null, null, null, 0);
        when(boatRepository.findById(1L)).thenReturn(Optional.of(before));
        when(boatRepository.save(any(Boat.class))).thenReturn(after);
        BoatDTO result = boatService.upsert(1L, updated);
        assertThat(result.sign().equals(after.getSign()));
    }

    @Test
    void upsert_adds_whenNotExists() {
        Boat updated = new Boat(2L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", Instant.now(), null, null, null, 0);
        BoatDTO dto = new BoatDTO(2L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        when(boatRepository.findById(2L)).thenReturn(Optional.empty());
        when(boatRepository.save(updated)).thenReturn(updated);
        BoatDTO result = boatService.upsert(2L, dto);
        assertThat(result).isEqualTo(dto);
        verify(boatRepository).save(updated);
    }

    @Test
    void upsert_throws_whenMalformed() {
        BoatDTO dto = new BoatDTO(3L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year");
        assertThatThrownBy(() -> boatService.upsert(4L, dto))
                .isInstanceOf(BoatRequestMalformedException.class);
    }

}
