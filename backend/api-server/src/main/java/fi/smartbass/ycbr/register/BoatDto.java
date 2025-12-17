package fi.smartbass.ycbr.register;

public record BoatDTO(Long id,
                      String owner,
                      String name,
                      String sign,
                      String make,
                      String model,
                      Double loa,
                      Double draft,
                      Double beam,
                      Double deplacement,
                      String engines,
                      String year) {
}
