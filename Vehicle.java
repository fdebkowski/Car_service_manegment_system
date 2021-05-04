package proj_1;

import java.time.LocalDate;

abstract public class Vehicle {

    enum EngineType {
        DIESEL,
        PETROL,
        ELECTRIC
    }

    final String id;
    final double engine_cap;
    final EngineType engineType;
    final String brand;
    final String model;
    boolean toRepair, isParked;
    LocalDate repairStartDate, repairEndDate;

    public Vehicle(double engine_cap, EngineType engineType, String brand, String model) {
        this.id = "V_" + Place.generateId();
        this.engine_cap = engine_cap;
        this.engineType = engineType;
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return "{" +
                "engine_cap=" + engine_cap +
                ", engineType=" + engineType +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                "}";
    }

    public void isRepairEnded() {
        if (this.repairEndDate != null && this.repairEndDate.equals(Main.baseDate)) {
            System.out.println("Repair of:" + this + " has ended.");
            this.toRepair = false;
            for (Service s :
                    Main.services) {
                for (CarServiceSpot css :
                        s.carServiceSpots) {
                    if (css.vehicle != null && css.vehicle.id.equals(this.id)) {
                        css.vehicle = null;
                        return;
                    }
                }
                for (IndependentCarServiceSpot icss :
                        s.independentCarServiceSpots) {
                    if (icss.vehicle.id.equals(this.id)) {
                        icss.vehicle = null;
                        return;
                    }
                }
            }
            System.out.println("Do you want to rent a parking space?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int wantRent = Main.menuSc.nextInt();
            switch (wantRent){
                case 1:
                    ParkingSpace ps = Main.selectedService.selectFreeParkingSpaces();
                    this.isParked = true;
                    Main.selectedPerson.rents.add(ps);
                    ps.vehicle = this;
            }
        }
    }


}