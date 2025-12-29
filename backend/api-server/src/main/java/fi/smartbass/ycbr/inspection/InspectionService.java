package fi.smartbass.ycbr.inspection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class InspectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionService.class);
    private final InspectionRepository repository;
    private final InspectionMapper mapper;

    public InspectionService(InspectionRepository repository, InspectionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public InspectionDto read(UUID id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new InspectionNotFoundException(id)));
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<InspectionDto> fetchByInspector(final String inspector) {
        if (inspector == null) throw new NameNotFoundException("NULL");
        return mapper.toDTOs(repository.findByInspector(inspector));
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<MyInspectionsDto> fetchMyInspections(final String inspectorName) {
        if (inspectorName == null) throw new NameNotFoundException("NULL");
        List<MyInspectionsDto> inspections = (List<MyInspectionsDto>) repository.fetchMyInspections(inspectorName);
        LOGGER.info("Found {} inspections for inspector {}", inspections.size(), inspectorName);
        LOGGER.info("Inspections: {}", inspections);
        return repository.fetchMyInspections(inspectorName);
    }

    @Transactional(readOnly = true)
    public Iterable<MyInspectionsDto> fetchAllInspections() {
        return repository.fetchAllInspections();
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
         LOGGER.warn("Inspection with id {} not found for deletion.", id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public InspectionDto create(NewInspectionDto newDto) {
        InspectionDto dto = new InspectionDto(
                null,
                Instant.now().toString(),
                newDto.inspectorName(),
                newDto.eventId(),
                newDto.boatId(),
                new InspectionDataDto(
                        new HullDataDto(false, false, false, false, false, false, false, false, false, false, 0),
                        new RigDataDto(false, false, false, false),
                        new EngineDataDto(false, false, false, false, false, false, false, false),
                        new EquipmentDataDto(false, false, false, 0, false, false, false, false, false, false, false, false, false, false, false),
                        new MaritimeDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                        new SafetyDataDto(false, 0, 0, false, false, 0, false, 0, false, false, 0, false, false, false, false, false, false)
                ),
                null);
        LOGGER.info("DTO: {}", dto);
        InspectionEntity newEntity = repository.save(mapper.toEntity(dto));
        LOGGER.info("Entity: {}", newEntity);
        return mapper.toDTO(newEntity);
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public InspectionDto update(UUID id, InspectionDto dto) {
        if (!id.equals(dto.inspectionId())) {
            LOGGER.error("IDs do not match: {} <> {}", id, dto.inspectionId());
            throw new InspectionRequestMalformedException(id, dto.inspectionId());
        }
        return repository.findById(id)
                .map(old -> {
                    InspectionEntity updated = mapper.toEntity(dto);
                    updated.setVersion(old.getVersion()); // Preserve the version for optimistic locking
                    LOGGER.info("upsert from {} to {}", old, updated);
                    return mapper.toDTO(repository.save(updated));
                })
                .orElseGet(() -> createFrom(dto));
    }

    private InspectionDto createFrom(InspectionDto from) {
        InspectionDto dto = new InspectionDto(
                null,
                Instant.now().toString(),
                from.inspector(),
                from.eventId(),
                from.boatId(),
                from.inspection(),
                from.completed());
        LOGGER.info("DTO: {}", dto);
        InspectionEntity newEntity = repository.save(mapper.toEntity(dto));
        LOGGER.info("Entity: {}", newEntity);
        return mapper.toDTO(newEntity);
    }

}
