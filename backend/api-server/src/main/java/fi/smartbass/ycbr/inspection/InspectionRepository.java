package fi.smartbass.ycbr.inspection;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InspectionRepository extends CrudRepository<InspectionEntity, UUID> {
    Iterable<InspectionEntity> findByInspector(String inspector);

    @Query("""
            SELECT i.inspection_id , i.inspector as inspector_name, b."name"  as boat_name,
                   ie.starts as "day", ie.place , i.completed
            FROM inspections i
            JOIN boats b ON b.boat_id = i.boat_id
            JOIN i9events ie ON ie.i9event_id = i.event_id
            WHERE i.inspector = :inspectorName
            """)
    Iterable<MyInspectionsDto> fetchMyInspections(String inspectorName);

    @Query("""
            SELECT i.inspection_id , i.inspector as inspector_name, b."name"  as boat_name,
                   ie.starts as "day", ie.place , i.completed
            FROM inspections i
            JOIN boats b ON b.boat_id = i.boat_id
            JOIN i9events ie ON ie.i9event_id = i.event_id
            """)
    Iterable<MyInspectionsDto> fetchAllInspections();

}
