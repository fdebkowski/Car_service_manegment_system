package proj_1;

public class CityCar extends Vehicle{
    final int maxPeople;
    final boolean isTaxi;

    public CityCar(double engine_cap, EngineType engineType, String brand, String model, int maxPeople, boolean isTaxi) {
        super(engine_cap, engineType, brand, model);
        this.maxPeople = maxPeople;
        this.isTaxi = isTaxi;
    }
}
