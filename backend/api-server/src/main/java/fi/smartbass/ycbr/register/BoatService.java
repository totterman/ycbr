package fi.smartbass.ycbr.register;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {

    private final BoatRepository boatRepository;

    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public Iterable<Boat> getAllBoats() {
        return boatRepository.findAll();
    }

    public Boat getBoatById(Long id) {
        return boatRepository.findById(id).orElseThrow(() -> new BoatNotFoundException(id));
    }

    public Iterable<Boat> getBoatsByOwner(String owner) {
        return boatRepository.findByOwner(owner);
    }

    public Boat addBoatToRegister(Boat boat) {
        if (boat.id() != null && boatRepository.existsById(boat.id())) {
            throw new BoatAlreadyExistsException(boat.id());
        }
        return boatRepository.save(boat);
    }

    public void deleteBoatFromRegister(Long id) {
        if (!boatRepository.existsById(id)) {
            throw new BoatNotFoundException(id);
        }
        boatRepository.deleteById(id);
    }

    public Boat updateBoatInRegister(Long id, Boat updatedBoat) {
        return boatRepository.findById(id)
                .map(existingBoat -> {
                    Boat boatToUpdate = new Boat(
                            existingBoat.id(),
                            updatedBoat.owner(),
                            updatedBoat.name(),
                            updatedBoat.sign(),
                            updatedBoat.make(),
                            updatedBoat.model(),
                            updatedBoat.loa(),
                            updatedBoat.draft(),
                            updatedBoat.beam(),
                            updatedBoat.deplacement(),
                            updatedBoat.engines(),
                            updatedBoat.year(),
                            existingBoat.createdAt(), // Preserve the original creation timestamp
                            existingBoat.createdBy(), // Preserve the original creator
                            null, // modifiedAt will be set automatically
                            null, // modifiedBy will be set automatically
                            existingBoat.version() // Preserve the version for optimistic locking
                    );
                    return boatRepository.save(boatToUpdate);
                })
                .orElseGet(() -> addBoatToRegister(updatedBoat));
    }
}
