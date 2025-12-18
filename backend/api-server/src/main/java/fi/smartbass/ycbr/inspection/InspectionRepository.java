package fi.smartbass.ycbr.inspection;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface InspectionRepository extends CrudRepository<InspectionEntity, UUID> {
    Iterable<InspectionEntity> findByInspector(String inspector);
}
