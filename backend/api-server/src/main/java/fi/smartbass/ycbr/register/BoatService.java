package fi.smartbass.ycbr.register;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BoatService {

    private final Log LOGGER = LogFactory.getLog(BoatService.class);
    private final BoatMapper mapper;
    private final BoatRepository boatRepository;

    public BoatService(BoatMapper mapper, BoatRepository boatRepository) {
        this.mapper = mapper;
        this.boatRepository = boatRepository;
    }

    public Iterable<BoatDto> getAllBoats() {
        return mapper.toDTOs(boatRepository.findAll());
    }

    public BoatDto getBoatByBoatId(UUID boatId) {
        return mapper.toDTO(boatRepository.findByBoatId(boatId).orElseThrow(() -> new BoatNotFoundException(boatId)));
    }

    public Iterable<BoatDto> getBoatsByOwner(String owner) {
        return mapper.toDTOs(boatRepository.findByOwner(owner));
    }

    public BoatDto create(BoatDto dto) {
        if (dto.boatId() != null && boatRepository.existsByBoatId(dto.boatId())) {
            LOGGER.warn("BoatEntity with inspectionId " + dto.boatId() + " already exists.");
            throw new BoatAlreadyExistsException(dto.boatId());
        }
        return mapper.toDTO(boatRepository.save(mapper.toEntity(dto)));
    }

    public BoatDto create(NewBoatDto dto) {
        return mapper.toDTO(boatRepository.save(mapper.toEntity(dto)));
    }

    public void delete(UUID boatId) {
        if (!boatRepository.existsByBoatId(boatId)) {
            LOGGER.warn("BoatEntity with inspectionId " + boatId + " not found for deletion.");
        }
        boatRepository.deleteByBoatId(boatId);
    }


    public BoatDto upsert(UUID boatId, BoatDto dto) {
        if (boatId != null && !boatId.equals(dto.boatId())) {
            LOGGER.warn("parameter " + boatId + " and boatId " + dto.boatId() + " are not equal");
            throw new BoatRequestMalformedException(boatId, dto.boatId());
        }
        return boatRepository.findByBoatId(boatId)
                .map(existingBoat -> {
                    LOGGER.info("Existing boatId with Version nr: " + existingBoat.getVersion() + " found for upsert with inspectionId: " + existingBoat.getBoatId());
                    BoatEntity boatToUpdate = mapper.toEntity(dto);
                    boatToUpdate.setVersion(existingBoat.getVersion()); // Preserve the version for optimistic locking
                    return mapper.toDTO(boatRepository.save(boatToUpdate));
                })
                .orElseGet(() -> create(dto));
    }
}
