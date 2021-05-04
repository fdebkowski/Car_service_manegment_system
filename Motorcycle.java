package proj_1;

public class Motorcycle extends Vehicle {
    final int noEngineStrokes;
    final String driverLicenceCategory;

    public Motorcycle(double engine_cap, EngineType engineType, String brand, String model, int noEngineStrokes, String driverLicenceCategory) {
        super(engine_cap, engineType, brand, model);
        this.noEngineStrokes = noEngineStrokes;
        this.driverLicenceCategory = driverLicenceCategory;
    }
}
