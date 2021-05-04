package proj_1;

public class TerrainCar extends Vehicle{
    int tireSize;
    boolean hasBlockedDiff;

    public TerrainCar(double engine_cap, EngineType engineType, String brand, String model, int tireSize, boolean hasBlockedDiff) {
        super(engine_cap, engineType, brand, model);
        this.tireSize = tireSize;
        this.hasBlockedDiff = hasBlockedDiff;
    }
}
