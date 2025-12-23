package fi.smartbass.ycbr.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BoatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoatService.class);
    private final BoatMapper mapper;
    private final BoatRepository boatRepository;

    public BoatService(BoatMapper mapper, BoatRepository boatRepository) {
        this.mapper = mapper;
        this.boatRepository = boatRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<BoatDto> getAllBoats() {
        return mapper.toDTOs(boatRepository.findAll());
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public BoatDto getBoatByBoatId(UUID boatId) {
        return mapper.toDTO(boatRepository.findByBoatId(boatId).orElseThrow(() -> new BoatNotFoundException(boatId)));
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<BoatDto> getBoatsByOwner(String owner) {
        return mapper.toDTOs(boatRepository.findByOwner(owner));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public BoatDto create(BoatDto dto) {
        if (dto.boatId() != null && boatRepository.existsByBoatId(dto.boatId())) {
            LOGGER.warn("BoatEntity with inspectionId {} already exists.", dto.boatId());
            throw new BoatAlreadyExistsException(dto.boatId());
        }
        return mapper.toDTO(boatRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public BoatDto create(NewBoatDto dto) {
        return mapper.toDTO(boatRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public void delete(UUID boatId) {
        if (!boatRepository.existsByBoatId(boatId)) {
            LOGGER.warn("BoatEntity with inspectionId {} not found for deletion.", boatId);
        }
        boatRepository.deleteByBoatId(boatId);
    }


    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public BoatDto upsert(UUID boatId, BoatDto dto) {
        if (boatId != null && !boatId.equals(dto.boatId())) {
            LOGGER.warn("parameter {} and boatId {} are not equal", boatId, dto.boatId());
            throw new BoatRequestMalformedException(boatId, dto.boatId());
        }
        return boatRepository.findByBoatId(boatId)
                .map(existingBoat -> {
                    LOGGER.info("Existing boatId with Version nr: {} found for upsert with inspectionId: {}", existingBoat.getVersion(), existingBoat.getBoatId());
                    BoatEntity boatToUpdate = mapper.toEntity(dto);
                    boatToUpdate.setVersion(existingBoat.getVersion()); // Preserve the version for optimistic locking
                    return mapper.toDTO(boatRepository.save(boatToUpdate));
                })
                .orElseGet(() -> create(dto));
    }
}
