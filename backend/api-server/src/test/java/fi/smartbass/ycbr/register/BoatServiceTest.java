package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

//    @BeforeEach
//    void setUp() {
//        boatRepository = mock(BoatRepository.class);
//        boatService = new BoatService(boatRepository);
//    }

    @Test
    void getAllBoats_returnsAllBoats() {
        when(boatRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<Boat> result = boatService.getAllBoats();
        assertThat(result).isEmpty();
        verify(boatRepository).findAll();
    }

    @Test
    void getBoatById_returnsBoat_whenFound() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.findById(1L)).thenReturn(Optional.of(boat));
        Boat result = boatService.getBoatById(1L);
        assertThat(result).isEqualTo(boat);
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
        Iterable<Boat> result = boatService.getBoatsByOwner("owner");
        assertThat(result).isEmpty();
        verify(boatRepository).findByOwner("owner");
    }

    @Test
    void addBoatToRegister_savesBoat_whenIdIsNull() {
        Boat boat = new Boat(null, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.save(boat)).thenReturn(boat);
        Boat result = boatService.addBoatToRegister(boat);
        assertThat(result).isEqualTo(boat);
        verify(boatRepository).save(boat);
    }

    @Test
    void addBoatToRegister_throws_whenIdExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.existsById(1L)).thenReturn(true);
        assertThatThrownBy(() -> boatService.addBoatToRegister(boat))
                .isInstanceOf(BoatAlreadyExistsException.class);
    }

    @Test
    void addBoatToRegister_saves_whenIdNotExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.existsById(1L)).thenReturn(false);
        when(boatRepository.save(boat)).thenReturn(boat);
        Boat result = boatService.addBoatToRegister(boat);
        assertThat(result).isEqualTo(boat);
        verify(boatRepository).save(boat);
    }

    @Test
    void deleteBoatFromRegister_deletes_whenExists() {
        when(boatRepository.existsById(1L)).thenReturn(true);
        boatService.deleteBoatFromRegister(1L);
        verify(boatRepository).deleteById(1L);
    }

    @Test
    void deleteBoatFromRegister_throws_whenNotExists() {
        when(boatRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> boatService.deleteBoatFromRegister(1L))
                .isInstanceOf(BoatNotFoundException.class);
    }

    @Test
    void updateBoatInRegister_updates_whenExists() {
        Instant createdAt = Instant.now();
        Boat existing = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", createdAt, "createdBy", null, null, 0);
        Boat updated = new Boat(1L, "newOwner", "newName", "newSign", "newMake", "newModel", 2.0, 2.0, 2.0, 2.0, "newEngines", "newYear", null, null, null, null, 0);
        when(boatRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(boatRepository.save(any(Boat.class))).thenReturn(updated);

        Boat result = boatService.updateBoatInRegister(1L, updated);

        assertThat(result).isEqualTo(updated);
        ArgumentCaptor<Boat> captor = ArgumentCaptor.forClass(Boat.class);
        verify(boatRepository).save(captor.capture());
        Boat saved = captor.getValue();
        assertThat(saved.owner()).isEqualTo("newOwner");
        assertThat(saved.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void updateBoatInRegister_adds_whenNotExists() {
        Boat updated = new Boat(2L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", Instant.now(), null, null, null, 0);
        when(boatRepository.findById(2L)).thenReturn(Optional.empty());
        when(boatRepository.save(updated)).thenReturn(updated);

        Boat result = boatService.updateBoatInRegister(2L, updated);

        assertThat(result).isEqualTo(updated);
        verify(boatRepository).save(updated);
    }
}
