package fi.smartbass.ycbr.register;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class BoatService {

    private final Log LOGGER = LogFactory.getLog(BoatService.class);
    private final BoatMapper mapper;
    private final BoatRepository boatRepository;

    public BoatService(BoatMapper mapper, BoatRepository boatRepository) {
        this.mapper = mapper;
        this.boatRepository = boatRepository;
    }

    public Iterable<BoatDTO> getAllBoats() {
        return mapper.toDTOs(boatRepository.findAll());
    }

    public BoatDTO getBoatById(Long id) {
        return mapper.toDTO(boatRepository.findById(id).orElseThrow(() -> new BoatNotFoundException(id)));
    }

    public Iterable<BoatDTO> getBoatsByOwner(String owner) {
        return mapper.toDTOs(boatRepository.findByOwner(owner));
    }

    public BoatDTO create(BoatDTO dto) {
        if (dto.id() != null && boatRepository.existsById(dto.id())) {
            LOGGER.warn("Boat with id " + dto.id() + " already exists.");
            throw new BoatAlreadyExistsException(dto.id());
        }
        return mapper.toDTO(boatRepository.save(mapper.toEntity(dto)));
    }

    public void delete(Long id) {
        if (!boatRepository.existsById(id)) {
            LOGGER.warn("Boat with id " + id + " not found for deletion.");
        }
        boatRepository.deleteById(id);
    }

    public BoatDTO upsert(Long id, BoatDTO dto) {
        if (!id.equals(dto.id())) {
            LOGGER.warn("parameter " + id + " and boatId " + dto.id() + " are not equal");
            throw new BoatRequestMalformedException(id, dto.id());
        }
        return boatRepository.findById(id)
                .map(existingBoat -> {
                    Boat boatToUpdate = mapper.toEntity(dto);
                    boatToUpdate.setId(existingBoat.getId());
                    boatToUpdate.setCreatedAt(existingBoat.getCreatedAt()); // Preserve the original creation timestamp
                    boatToUpdate.setCreatedBy(existingBoat.getCreatedBy()); // Preserve the original creator
                            // modifiedAt will be set automatically
                            // modifiedBy will be set automatically
                    boatToUpdate.setVersion(existingBoat.getVersion()); // Preserve the version for optimistic locking
                    return mapper.toDTO(boatRepository.save(boatToUpdate));
                })
                .orElseGet(() -> create(dto));
    }
}
