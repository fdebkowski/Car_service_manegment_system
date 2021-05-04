package proj_1;

import java.util.ArrayList;
import java.util.List;

public class Service {
    List<ConsumerWarehouse> consumerWarehouses = new ArrayList<>();
    ServiceWarehouse serviceWarehouse = new ServiceWarehouse(45);
    List<ParkingSpace> parkingSpaces = new ArrayList<>();
    List<CarServiceSpot> carServiceSpots = new ArrayList<>();
    List<IndependentCarServiceSpot> independentCarServiceSpots = new ArrayList<>();
    List<Person> clients = new ArrayList<>();
    List<Vehicle> vehiclesToRepair = new ArrayList<>();
    List<TenantAlert> tenantAlerts = new ArrayList<>();
    double earnings = 0;

    public Service(int cwCount, int psCount, int cssCount, int icssCount){
        int i = 0;
        for (i = 0; i < cwCount; i++) {
            this.consumerWarehouses.add(new ConsumerWarehouse(Math.random() * 100, 600));
        }
        for (i = 0; i < psCount; i++) {
            this.parkingSpaces.add(new ParkingSpace(Math.random() * 50, 300));
        }
        for (i = 0; i < cssCount; i++) {
            this.carServiceSpots.add(new CarServiceSpot(Math.random() * 70));
        }
        for (i = 0; i < icssCount; i++) {
            this.independentCarServiceSpots.add(new IndependentCarServiceSpot(Math.random() * 80, 400));
        }
    }

    public CarServiceSpot selectFreeCarServiceSpots() {
        for (CarServiceSpot css : this.carServiceSpots) {
            if (css.vehicle == null)
                return css;
        }
        return null;
    }

    public IndependentCarServiceSpot selectFreeIndependentCarServiceSpots() {
        for (IndependentCarServiceSpot icss : this.independentCarServiceSpots) {
            if (icss.vehicle == null)
                return icss;
        }
        return null;
    }

    public ParkingSpace selectFreeParkingSpaces(){
        for (ParkingSpace ps : this.parkingSpaces) {
            if (ps.vehicle == null)
                return ps;
        }
        return null;
    }

    public void checkRents() {
        for (ConsumerWarehouse cw : this.consumerWarehouses) {
            cw.sendAlert(this);
            cw.cleanPlace(this);
        }
        for (ParkingSpace ps : this.parkingSpaces) {
            ps.sendAlert(this);
            ps.cleanPlace(this);
        }
        for (IndependentCarServiceSpot icss : this.independentCarServiceSpots) {
            icss.sendAlert(this);
            icss.cleanPlace(this);
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "consumerWarehouses=" + consumerWarehouses +
                ", serviceWarehouse=" + serviceWarehouse +
                ", parkingSpaces=" + parkingSpaces +
                ", carServiceSpots=" + carServiceSpots +
                ", independentCarServiceSpots=" + independentCarServiceSpots +
                ", clients=" + clients +
                ", vehiclesToRepair=" + vehiclesToRepair +
                ", tenantAlerts=" + tenantAlerts +
                ", earnings=" + earnings +
                '}';
    }
}
