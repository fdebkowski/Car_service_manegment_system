package proj_1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    //date and date counter setup
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    public static LocalDate baseDate = LocalDate.now();
    public static ArrayList<String> ids = new ArrayList<>();
    public static ArrayList<Service> services = new ArrayList<>();
    public static Scanner menuSc = new Scanner(System.in);
    static DateCounter dateCounter = new DateCounter();
    static Thread dcThread = new Thread(dateCounter);
    //rent checker setup
    static RentChecker rentChecker = new RentChecker();
    static Thread rcThread = new Thread(rentChecker);
    static Person selectedPerson = null;
    static Service selectedService = null;

    public static void main(String[] args) {

        int i;
        dcThread.start(); // start Date Counter
        rcThread.start(); // start Rent Checker

        //create service
        Service serviceA = new Service(16, 8, 4, 2);
        services.add(serviceA);

        // create people
        Person mark = new Person("Mark",
                "Smith",
                "1111",
                "4th Avenue, NY",
                "15-02-2000");
        Person michael = new Person("Michael",
                "Rock",
                "2222",
                "5th Street, CA",
                "01-20-1998");
        Person juliet = new Person("Juliet",
                "Clayton",
                "3333",
                "Brooklyn Avenue, NY",
                "01-04-1970");
        Person arek = new Person(
                "Arkadiusz",
                "Markowski",
                "4444",
                "Plac Defilad 1",
                "01-01-2000"
        );
        Person maria = new Person(
                "Maria",
                "Kowalska",
                "5555",
                "Kolorowa 23",
                "14-06-1989"
        );
        mark.vehicles.add(new CityCar(1, Vehicle.EngineType.PETROL, "Toyota", "Yaris", 5, false));

        // add people to service
        serviceA.clients.add(mark);
        serviceA.clients.add(michael);
        serviceA.clients.add(juliet);
        serviceA.clients.add(arek);
        serviceA.clients.add(maria);

        // select service
        System.out.println("Select service:");
        for (i = 0; i < Main.services.size(); i++) {
            System.out.println(i);
        }
        int serviceIndex = menuSc.nextInt();
        selectedService = services.get(serviceIndex);

        //main loop with switch
        while (true) {

            try {
                System.out.println("""
                        Welcome to the service
                        Choose option:
                        1. Select Person
                        2. Print out your data
                        3. Show free rooms
                        4. Rent room
                        5. Print out other's room components
                        6. Insert item/vehicle
                        7. Remove item/vehicle
                        8. Setup repair
                        9. Start repair
                        10. Add person to rent
                        11. Renew rent
                        12. Save service state to file
                        0. Exit""");
                System.out.println(baseDate);
                int noOption = menuSc.nextInt();
                switch (noOption) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1: // select person
                        System.out.println("Enter your pesel");
                        String inputPesel = menuSc.next();

                        for (Person client : selectedService.clients) {
                            if (client.pesel.equals(inputPesel)) {
                                selectedPerson = client;
                                break;
                            }
                        }

                        if (selectedPerson == null) {
                            System.out.println("Person does not exist\n");
                        } else {
                            System.out.println("Selected person: ");
                            System.out.println(selectedPerson);
                        }
                        pressAnyKeyToContinue();
                        break;
                    case 2: // show selected person data
                        if (isPersonSelected()) {
                            System.out.println(selectedPerson);
                            pressAnyKeyToContinue();
                        }
                        break;
                    case 3: // show free consumer warehouses
                        i = 0;
                        for (ConsumerWarehouse consumerWarehouse : selectedService.consumerWarehouses) {
                            if (!consumerWarehouse.isOccupied) {
                                System.out.println(i + " " + consumerWarehouse);
                                i++;
                            }
                        }
                        pressAnyKeyToContinue();
                        break;
                    case 4: // rent place
                        try {
                            if (isPersonSelected() && selectedPerson.hasUnder3Alerts() && selectedPerson.arePaymentsNotExceeded()) {
                                System.out.println("Free warehouses");
                                i = 0;
                                for (ConsumerWarehouse consumerWarehouse : selectedService.consumerWarehouses) {
                                    if (!consumerWarehouse.isOccupied) {
                                        System.out.println(i + " " + consumerWarehouse);
                                        i++;
                                    }
                                }
                                System.out.println("Select warehouse to rent");
                                int cw_index = menuSc.nextInt();
                                ConsumerWarehouse cw = selectedService.consumerWarehouses.get(cw_index);
                                rentPlace(cw);
                                pressAnyKeyToContinue();
                            }
                        } catch (ProblematicTenantException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5: // show rent
                        if (isPersonSelected()) {
                            if (selectedPerson.rents != null) {
                                System.out.println("Select rent");
                                i = 0;
                                for (PlaceToRent rent : selectedPerson.rents) {
                                    System.out.println(i + " " + rent.id);
                                    i++;
                                }
                                int rent_index = menuSc.nextInt();
                                System.out.println(selectedPerson.rents.get(rent_index));
                            } else {
                                System.out.println("No rents\n");
                            }
                            pressAnyKeyToContinue();
                        }
                        break;
                    case 6: // add item to rented place
                        if (isPersonSelected()) {
                            if (selectedPerson.rents != null) {
                                System.out.println("Select rent");
                                i = 0;
                                for (PlaceToRent rent : selectedPerson.rents) {
                                    System.out.println(i + " " + rent.toString());
                                    i++;
                                }
                                int rentIndex = menuSc.nextInt();
                                PlaceToRent selectedPlace = selectedPerson.rents.get(rentIndex);

                                if (selectedPlace.getClass() == ConsumerWarehouse.class) {
                                    System.out.println("Capacity: " + selectedPlace.filled_volume() + "/" + selectedPlace.volume);
                                    System.out.println("Object volume:");
                                    double volume = menuSc.nextDouble();
                                    try {
                                        selectedPlace.insertIntoPlace(new Item(volume));
                                    } catch (TooManyThingsException e) {
                                        e.printStackTrace();
                                    }

                                } else if (selectedPlace.getClass() == ParkingSpace.class) {
                                    if (selectedPlace.isOccupied) {
                                        System.out.println("Place is occupied");
                                    } else {
                                        System.out.println("Select vehicle");
                                        for (Vehicle vehicle : selectedPerson.vehicles) {
                                            System.out.println(vehicle);
                                        }
                                        int veh_index = menuSc.nextInt();
                                        ((ParkingSpace) selectedPlace).vehicle = selectedPerson.vehicles.get(veh_index);
                                    }
                                }
                            } else {
                                System.out.println("No rents");
                                pressAnyKeyToContinue();
                            }
                        }

                        break;
                    case 7: // remove item/car from rent
                        if (isPersonSelected()) {
                            if (selectedPerson.rents != null) {
                                System.out.println("Select rent");
                                for (PlaceToRent rent : selectedPerson.rents) {
                                    System.out.println(rent.toString());
                                }
                                int rentIndex = menuSc.nextInt();
                                PlaceToRent selectedPlace;
                                try {
                                    selectedPlace = selectedPerson.rents.get(rentIndex);
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Rent index out of bounds");
                                    pressAnyKeyToContinue();
                                    break;
                                }
                                if (selectedPlace.getClass() == ConsumerWarehouse.class) {
                                    System.out.println("Select item index to remove:");
                                    System.out.println("Capacity: " + selectedPlace.filled_volume() + "/" + selectedPlace.volume);
                                    i = 0;
                                    for (Item item :
                                            selectedPlace.items) {
                                        System.out.println(i + " " + item);
                                        i++;
                                    }
                                    int item_index = menuSc.nextInt();
                                    System.out.println("Item: " + selectedPlace.items.get(item_index) + " is removed");
                                    selectedPlace.items.remove(item_index);
                                } else if (selectedPlace.getClass() == ParkingSpace.class) {
                                    if (selectedPlace.isOccupied) {
                                        selectedPerson.vehicles.add(((ParkingSpace) selectedPlace).vehicle);
                                    } else {
                                        System.out.println("Place is free");
                                    }
                                }
                            } else {
                                System.out.println("No rents");
                            }
                            pressAnyKeyToContinue();
                        }

                        break;
                    case 8: // setup repair
                        if (isPersonSelected()) {
                            System.out.println("Select vehicle to be repaired");
                            i = 0;
                            for (Vehicle vehicle : selectedPerson.vehicles) {
                                System.out.println(i + " " + vehicle);
                            }
                            int veh_index = menuSc.nextInt();
                            System.out.println("""
                                    Select type of repair
                                    1. Self repair
                                    2. Repair by mechanic""");
                            int repair_index = menuSc.nextInt();
                            final Vehicle vehicleToRepair = selectedPerson.vehicles.get(veh_index);
                            vehicleToRepair.toRepair = true;
                            switch (repair_index) {
                                case 1 -> {
                                    IndependentCarServiceSpot freeIcss = selectedService.selectFreeIndependentCarServiceSpots();
                                    if (freeIcss != null) {
                                        freeIcss.vehicle = vehicleToRepair;
                                        freeIcss.people.add(selectedPerson);
                                    } else {
                                        selectedService.vehiclesToRepair.add(vehicleToRepair);
                                    }
                                }
                                case 2 -> {
                                    CarServiceSpot freeCss = selectedService.selectFreeCarServiceSpots();
                                    if (freeCss != null) {
                                        freeCss.vehicle = vehicleToRepair;
                                    } else {
                                        selectedService.vehiclesToRepair.add(vehicleToRepair);
                                    }
                                }
                            }
                        }
                        break;
                    case 9: // start repair
                        if (isPersonSelected()) {
                            //check if there are vehicles to be repaired
                            boolean areVehiclesToBeRepaired = false;
                            for (Vehicle vehicle : selectedPerson.vehicles) {
                                if (vehicle.toRepair) {
                                    areVehiclesToBeRepaired = true;
                                    break;
                                }
                            }
                            if (areVehiclesToBeRepaired) {
                                System.out.println("Select vehicle to have its repair started");
                                i = 0;
                                for (Vehicle vehicle : selectedPerson.vehicles) {
                                    if (vehicle.toRepair) {
                                        System.out.println(i + " " + vehicle);
                                    }
                                }
                                int veh_index = menuSc.nextInt();
                                Vehicle vehicle = selectedPerson.vehicles.get(veh_index);
                                vehicle.repairStartDate = baseDate;
                                final long daysToAdd = (long) (Math.random() * 5);
                                vehicle.repairEndDate = baseDate.plusDays(daysToAdd);
                                selectedService.earnings += daysToAdd * 400;
                                System.out.println(vehicle + "repair has been started.\n End of repair on: " + vehicle.repairEndDate);
                            } else {
                                System.out.println("Selected person has no vehicle to repair");
                            }
                            pressAnyKeyToContinue();
                        }
                        break;
                    case 10: // add person to rent
                        if (isPersonSelected()) {
                            System.out.println("Select rent:");
                            for (PlaceToRent cw :
                                    selectedPerson.rents) {
                                if (cw.getClass() == ConsumerWarehouse.class) {
                                    System.out.println(cw);
                                }
                            }
                            int rentIndex = menuSc.nextInt();
                            System.out.println("Select person");
                            for (Person p :
                                    selectedService.clients) {
                                System.out.println(p);
                            }
                            int personIndex = menuSc.nextInt();
                            ConsumerWarehouse selectedRent = selectedService.consumerWarehouses.get(rentIndex);
                            Person personToAdd = selectedService.clients.get(personIndex);
                            try {
                                if (personToAdd.arePaymentsNotExceeded() && personToAdd.hasUnder3Alerts()) {
                                    selectedRent.people.add(personToAdd);
                                    System.out.println(personToAdd + "added to rent: " + selectedRent + "\n");
                                } else {
                                    System.out.println("Selected person cannot be added to rent");
                                }
                            } catch (ProblematicTenantException e) {
                                e.printStackTrace();
                            }
                            pressAnyKeyToContinue();
                            break;
                        }
                    case 11: //renew rent
                        if (isPersonSelected()) {
                            System.out.println("Select rent:");
                            System.out.println(selectedPerson.rents);
                            int rentIndex = menuSc.nextInt();
                            selectedPerson.rents.get(rentIndex).renew();
                        }
                        break;
                    case 12:
                        saveServiceToFile();
                        break;
                    default:
                        System.out.println("Wrong option");
                        pressAnyKeyToContinue();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input");
                break;
            }
        }
    }

    private static void rentPlace(PlaceToRent pl) {
        pl.startDate = baseDate.toString();
        pl.endDate = baseDate.plusDays(14).toString();
        pl.isOccupied = true;
        pl.people.add(selectedPerson);
        selectedPerson.rents.add(pl);
        System.out.println("Rent of:" + pl + "has started.\n");
    }

    public static boolean isPersonSelected() {
        if (selectedPerson == null) {
            System.out.println("Person is not selected");
            pressAnyKeyToContinue();
            return false;
        } else
            return true;
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public static void saveServiceToFile() {
        //save service state
        try {
            FileWriter servicesF = new FileWriter("services.txt");
//            StringBuilder servicesSb = new StringBuilder();
//            servicesSb.append("Vehicles to repair:");
//            for (Vehicle v : selectedService.vehiclesToRepair) {
//                if (v != null) {
//                    servicesSb.append("    " + v);
//                }
//            }
//            servicesSb.append("Vehicles in repair:");
//            for (CarServiceSpot css : selectedService.carServiceSpots) {
//                if (css.vehicle != null) {
//                    servicesSb.append("    " + css.vehicle);
//                }
//            }
//            for (IndependentCarServiceSpot icss : selectedService.independentCarServiceSpots) {
//                if (icss.vehicle != null) {
//                    servicesSb.append("    " + icss.vehicle);
//                }
//            }
            servicesF.write(selectedService.toString());
            servicesF.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // save warehouses
        try {
            FileWriter warehousesF = new FileWriter("warehouses.txt");
            StringBuilder warehousesSb = new StringBuilder();
            selectedService.consumerWarehouses.sort(Comparator.comparing(Place::getVolume));
            for (ConsumerWarehouse cw : selectedService.consumerWarehouses) {
                cw.items.sort(Comparator.comparing(Item::getVolume).reversed());
                warehousesSb.append(cw);
            }
            warehousesF.write(warehousesSb.toString());
            warehousesF.close();
            System.out.println("Warehouses saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
