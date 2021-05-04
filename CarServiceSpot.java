package proj_1;

public class CarServiceSpot extends Place {
    Vehicle vehicle;

    public CarServiceSpot(double volume) {
        super(volume, "CSP_");
    }

    @Override
    public String toString() {
        return "CarServiceSpot{" +
                "vehicle=" + vehicle +
                ", id='" + id + '\'' +
                ", volume=" + volume +
                ", type='" + type + '\'' +
                ", isOccupied=" + isOccupied +
                ", items=" + items +
                "}\n";
    }
}
