package fi.smartbass.ycbr.register;

@Service
public class BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    public Boat registerBoat(Boat boat) {
        return boatRepository.save(boat);
    }

    public Optional<Boat> getBoatById(Long id) {
        return boatRepository.findById(id);
    }

    public List<Boat> getAllBoats() {
        return boatRepository.findAll();
    }

    public void deleteBoat(Long id) {
        boatRepository.deleteById(id);
    }
}
