package proj_1;

public class AmphibiousCar extends Vehicle {
    double buoyancy;
    boolean isRoadLegal;

    public AmphibiousCar(double engine_cap, EngineType engineType, String brand, String model, double buoyancy, boolean isRoadLegal) {
        super(engine_cap, engineType, brand, model);
        this.buoyancy = buoyancy;
        this.isRoadLegal = isRoadLegal;
    }
}
