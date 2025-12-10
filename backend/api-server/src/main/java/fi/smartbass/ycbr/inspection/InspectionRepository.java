package fi.smartbass.ycbr.inspection;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface InspectionRepository extends CrudRepository<InspectionEntity, Long> {
    Iterable<InspectionEntity> findByInspector(String inspector);
}
