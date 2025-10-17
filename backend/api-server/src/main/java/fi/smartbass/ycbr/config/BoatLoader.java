package fi.smartbass.ycbr.config;

import fi.smartbass.ycbr.register.BoatRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class BoatLoader {

    private final BoatRepository boatRepository;

    public BoatLoader(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        // Load test data into the database or perform other initialization tasks
        System.out.println("Test data profile active: loading boat data...");
        /*
        boatRepository.deleteAll();

        Boat boat1 = new Boat(1000001L, "OwnerName", "BoatName", "Sign123", "MakeX", "ModelY", 10.5, 2.5, 3.5, 5000.0, "", "1982", null, null, null, null, 0);
        Boat boat2 = new Boat(1000002L, "AnotherOwner","AnotherBoat", "Sign456", "MakeA", "ModelB", 12.0, 3.0, 4.0, 6000.0, "", "1990");
        Boat boat3 = new Boat(1000003L, "ThirdOwner","ThirdBoat", "Sign789", "MakeC", "ModelD", 15.0, 4.0, 5.0, 7000.0, "", "1995");
        Boat boat4 = new Boat(1000004L, "FourthOwner","FourthBoat", "Sign012", "MakeE", "ModelF", 20.0, 5.0, 6.0, 8000.0, "", "2000");
        Boat boat5 = new Boat(1000005L, "FifthOwner","FifthBoat", "Sign345", "MakeG", "ModelH", 25.0, 6.0, 7.0, 9000.0, "", "1985");
        Boat boat6 = new Boat(1000006L, "SixthOwner","SixthBoat", "Sign678", "MakeI", "ModelJ", 30.0, 7.0, 8.0, 10000.0, "", "2010");
        Boat boat7 = new Boat(1000007L, "SeventhOwner","SeventhBoat", "Sign901;", "MakeK", "ModelL", 35.0, 8.0, 9.0, 11000.0, "", "2015");
        Boat boat8 = new Boat(1000008L, "EighthOwner","EighthBoat", "Sign234", "MakeM", "ModelN", 40.0, 9.0, 10.0, 12000.0, "", "1980");
        Boat boat9 = new Boat(1000009L, "NinthOwner","NinthBoat", "Sign567", "MakeO", "ModelP", 45.0, 10.0, 11.0, 13000.0, "", "1990");
        Boat boat10 = new Boat(10000010L, "TenthOwner","TenthBoat", "Sign890", "MakeQ", "ModelR", 50.0, 11.0, 12.0, 14000.0, "", "1975");
        Boat boat11 = new Boat(10000011L, "EleventhOwner", "EleventhBoat", "Sign111", "MakeS", "ModelT", 55.0, 12.0, 13.0, 15000.0,"", "1980");

        boatRepository.saveAll(List.of(boat1, boat2, boat3, boat4, boat5, boat6, boat7, boat8, boat9, boat10, boat11));

         */
    }
}
