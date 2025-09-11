package fi.smartbass.ycbr.register;

@Entity
@AllArgsConstructor
@Getter
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sign;
    private String make;
    private String model;
    private double loa; // Length overall in meters
    private double depth; // Depth in meters
    private double beam; // Beam in meters
    private double displacement; // Displacement in kilograms

    public static BoatDto of(Boat boat) {
        return new BoatDto(
            boat.getName(),
            boat.getSign(),
            boat.getMake(),
            boat.getModel(),
            boat.getLoa(),
            boat.getDepth(),
            boat.getBeam(),
            boat.getDisplacement()
        );
    }
}