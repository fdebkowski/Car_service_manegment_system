package proj_1;

import java.util.ArrayList;

public class ParkingSpace extends PlaceToRent {
    Vehicle vehicle;

    public ParkingSpace(double volume, double price) {
        super(volume, "PS_", price);
    }

    @Override
    public void cleanPlace(Service service) {
        super.cleanPlace(service);
        this.vehicle = null;
    }
}
