package fi.smartbass.ycbr.inspection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class InspectionService {

    private final Log LOGGER = LogFactory.getLog(InspectionService.class);
    private final InspectionRepository repository;
    private final InspectionMapper mapper;

    public InspectionService(InspectionRepository repository, InspectionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public InspectionDTO read(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new InspectionNotFoundException(id)));
    }

    public Iterable<InspectionDTO> fetchByInspector(final String inspector) {
        if (inspector == null) throw new NameNotFoundException("NULL");
        return mapper.toDTOs(repository.findByInspector(inspector));
    }

    public InspectionDTO create(NewInspectionDTO newDto) {
        InspectionDTO dto = new InspectionDTO(
                null,
                Instant.now().toString(),
                newDto.inspectorName(),
                newDto.eventId(),
                newDto.boatId(),
                new InspectionDataDto(
                        new HullDataDto(false, false, false, false, false, false, false, false, false, false, 0),
                        new RigDataDto(false, false, false, false),
                        new EngineDataDto(false, false, false, false, false, false, false, false)
                ),
                null);
        LOGGER.info("DTO: " + dto);
        InspectionEntity newEntity = repository.save(mapper.toEntity(dto));
        LOGGER.info("Entity: " + newEntity);
        return mapper.toDTO(newEntity);
    }

    public InspectionDTO update(Long id, InspectionDTO dto) {
        if (!id.equals(dto.id())) {
            LOGGER.error("IDs do not match: " + id + " <> " + dto.id());
            throw new InspectionRequestMalformedException(id, dto.id());
        }
        return repository.findById(id)
                .map(old -> {
                    InspectionEntity updated = mapper.toEntity(dto);
                    updated.setVersion(old.getVersion()); // Preserve the version for optimistic locking
                    LOGGER.info("upsert from " + old + " to " + updated);
                    return mapper.toDTO(repository.save(updated));
                })
                .orElseGet(() -> createFrom(dto));
    }

    private InspectionDTO createFrom(InspectionDTO from) {
        InspectionDTO dto = new InspectionDTO(
                null,
                Instant.now().toString(),
                from.inspector(),
                from.event(),
                from.boat(),
                from.inspection(),
                from.completed());
        LOGGER.info("DTO: " + dto);
        InspectionEntity newEntity = repository.save(mapper.toEntity(dto));
        LOGGER.info("Entity: " + newEntity);
        return mapper.toDTO(newEntity);
    }

}
