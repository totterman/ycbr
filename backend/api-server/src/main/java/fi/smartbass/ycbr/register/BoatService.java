package fi.smartbass.ycbr.register;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class BoatService {

    private final Log LOGGER = LogFactory.getLog(BoatService.class);
    private final BoatRepository boatRepository;
    private final TransactionalOperator transactionalOperator;

    public BoatService(BoatRepository boatRepository, TransactionalOperator transactionalOperator) {
        this.boatRepository = boatRepository;
        this.transactionalOperator = transactionalOperator;
    }

    public Flux<BoatDTO> getAllBoats() {
        return boatRepository.findAll()
                .flatMap(this::convertToDTO)
                .as(transactionalOperator::transactional);
    }

    public Mono<BoatDTO> getBoatById(Long id) {
        return boatRepository.findById(id)
                .switchIfEmpty(Mono.error(new BoatNotFoundException(id)))
                .flatMap(this::convertToDTO)
                .as(transactionalOperator::transactional);
    }

    public Flux<BoatDTO> getBoatsByOwner(String owner) {
        return boatRepository.findByOwner(owner)
                .flatMap(this::convertToDTO)
                .as(transactionalOperator::transactional);
    }

    public Mono<BoatDTO> addBoatToRegister(Boat boat) {
        if (boat.getId() != null && Boolean.TRUE.equals(boatRepository.existsById(boat.getId()).block())) {
            LOGGER.warn("Boat with id " + boat.getId() + " already exists.");
            return Mono.error(new BoatAlreadyExistsException(boat.getId()));
        }
        return boatRepository.save(boat)
                .flatMap(this::convertToDTO)
                .as(transactionalOperator::transactional);
    }

    public Mono<Void> deleteBoatFromRegister(Long id) {
        return boatRepository.existsById(id)
                .flatMap(exists -> {
                    if (Boolean.FALSE.equals(exists)) {
                        LOGGER.info("Boat with id " + id + " not found for deletion.");
                        return Mono.empty();
                    } else {
                        return boatRepository.deleteById(id)
                                .as(transactionalOperator::transactional);
                    }
                });
    }

    public Mono<BoatDTO> updateBoatInRegister(Long id, Boat updatedBoat) {
        return boatRepository.existsById(id)
                .flatMap(exists -> {
                    if (Boolean.FALSE.equals(exists)) {
                        return boatRepository.save(updatedBoat)
                                .map(newBoat -> new BoatDTO(
                                        newBoat.getId(),
                                        newBoat.getOwner(),
                                        newBoat.getName(),
                                        newBoat.getSign(),
                                        newBoat.getMake(),
                                        newBoat.getModel(),
                                        newBoat.getLoa(),
                                        newBoat.getDraft(),
                                        newBoat.getBeam(),
                                        newBoat.getDeplacement(),
                                        newBoat.getEngines(),
                                        newBoat.getYear()
                                ))
                                .as(transactionalOperator::transactional);
                    } else {
                        return boatRepository.findById(id)
                                .flatMap(existingBoat -> {
                                    Boat boatToUpdate = new Boat(
                                            existingBoat.getId(),
                                            updatedBoat.getOwner(),
                                            updatedBoat.getName(),
                                            updatedBoat.getSign(),
                                            updatedBoat.getMake(),
                                            updatedBoat.getModel(),
                                            updatedBoat.getLoa(),
                                            updatedBoat.getDraft(),
                                            updatedBoat.getBeam(),
                                            updatedBoat.getDeplacement(),
                                            updatedBoat.getEngines(),
                                            updatedBoat.getYear(),
                                            existingBoat.getCreatedAt(), // Preserve the original creation timestamp
                                            existingBoat.getCreatedBy(), // Preserve the original creator
                                            null, // modifiedAt will be set automatically
                                            null, // modifiedBy will be set automatically
                                            existingBoat.getVersion() // Preserve the version for optimistic locking
                                    );
                                    return boatRepository.save(boatToUpdate)
                                            .flatMap(this::convertToDTO)
                                            .as(transactionalOperator::transactional);
                                });
                    }
                });
    }



    private Mono<BoatDTO> convertToDTO(Boat boat) {
        return Mono.just(boat)
                .map(result -> new BoatDTO(
                        result.getId(),
                        result.getOwner(),
                        result.getName(),
                        result.getSign(),
                        result.getMake(),
                        result.getModel(),
                        result.getLoa(),
                        result.getDraft(),
                        result.getBeam(),
                        result.getDeplacement(),
                        result.getEngines(),
                        result.getYear()
                ));
    }

    private Mono<Boolean> boatExists(Long id) {
        return boatRepository.existsById(id).handle((exists, sink) -> {
            if (Boolean.FALSE.equals(exists)) {
                sink.error(new IllegalArgumentException());
            } else {
                sink.next(exists);
            }
        });
    }
}
