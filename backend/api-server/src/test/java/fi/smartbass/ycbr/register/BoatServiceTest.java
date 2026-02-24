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
import java.util.*;

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
        BoatEntity boat = new BoatEntity(boatId1, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
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
        BoatEntity newBoat = new BoatEntity(null, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        BoatEntity boat = new BoatEntity(boatId2, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        NewBoatDto dto = new NewBoatDto("club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "1977", 9.5, 3.2, 1.5, 14.5, 4.7, "D", null, "Owner1", "Owner2");
        when(boatRepository.save(newBoat)).thenReturn(boat);
        BoatDto result = boatService.create(dto);
        assertThat(result.sign()).isEqualTo(boat.getSign());
        verify(boatRepository).save(newBoat);
    }

    @Test
    @DisplayName("Creating boatId throws when inspectionId is not null")
    void create_throws_whenIdExists() {
        BoatEntity boat = new BoatEntity(boatId2, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        BoatDto dto = new BoatDto(boatId2, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
        when(boatRepository.existsByBoatId(boatId2)).thenReturn(true);
        assertThatThrownBy(() -> boatService.create(dto))
                .isInstanceOf(BoatAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Creating saves boatId when not exists in DB")
    void create_saves_whenIdNotExists() {
        BoatEntity boat = new BoatEntity(boatId2, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        NewBoatDto dto = new NewBoatDto("club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", 9.5, 3.2, 1.5, 14.5, 4.7, "D", null,  "Owner1", null);
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
        BoatEntity before = new BoatEntity(boatId3, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg0000", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        BoatDto updated = new BoatDto(boatId3, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
        BoatEntity after = new BoatEntity(boatId3, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "NewOwner1", "NewOwner2", null, null, null, null, 0);
        when(boatRepository.findByBoatId(boatId3)).thenReturn(Optional.of(before));
        when(boatRepository.save(any(BoatEntity.class))).thenReturn(after);
        BoatDto result = boatService.upsert(boatId3, updated);
        assertThat(result.sign().equals(after.getSign()));
        verify(boatRepository).save(any(BoatEntity.class));
    }

    @Test
    @DisplayName("Updating boatId becomes Save when boatId not exists")
    void upsert_adds_whenNotExists() {
        Set<EngineEntity> engineEntities = Set.of(new EngineEntity(0,"1995", "Yamaha", "ModelZ", "Serial123", 75.0));
        BoatEntity updated = new BoatEntity(boatId3, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", engineEntities, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
        Set<EngineDto> engineDtos = Set.of(new EngineDto(0, "1995", "Yamaha", "ModelZ", "Serial123", 75.0));
        BoatDto dto = new BoatDto(boatId3, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", engineDtos, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
        when(boatRepository.findByBoatId(boatId3)).thenReturn(Optional.empty());
        when(boatRepository.save(any(BoatEntity.class))).thenReturn(updated);
        BoatDto result = boatService.upsert(boatId3, dto);
        assertThat(result).isEqualTo(dto);
        verify(boatRepository).save(any(BoatEntity.class));
    }

    @Test
    @DisplayName("Updating boatId throws if UUIDs do not match")
    void upsert_throws_whenMalformed() {
        BoatDto dto = new BoatDto(boatId2, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", null, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
        assertThatThrownBy(() -> boatService.upsert(boatId3, dto))
                .isInstanceOf(BoatRequestMalformedException.class);
    }

}
