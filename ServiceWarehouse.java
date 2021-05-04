package proj_1;

public class ServiceWarehouse extends Place {

    public ServiceWarehouse(double area) {
        super(area, "SW_");
    }

    @Override
    public String toString() {
        return "ServiceWarehouse{" +
                "id='" + id + '\'' +
                ", volume=" + volume +
                ", type='" + type + '\'' +
                ", isOccupied=" + isOccupied +
                ", items=" + items +
                "}\n";
    }

}
