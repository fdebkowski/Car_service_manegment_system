package proj_1;

import java.awt.*;
import java.util.ArrayList;

class Person {
    final String firstName;
    final String lastName;
    final String pesel;
    final String address;
    final String birthDate;
    String firstRentDate;
    ArrayList<PlaceToRent> rents = new ArrayList<>();
    ArrayList<TenantAlert> alerts = new ArrayList<>();
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    public Person(String firstName, String lastName, String pesel, String address, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.address = address;
        this.birthDate = birthDate;

        try {
            this.firstRentDate = this.hasRentedBefore();
        } catch (NeverRentException e) {
            System.out.println(e.getMessage());
        }
    }

    private String hasRentedBefore() throws NeverRentException {
        if (this.firstRentDate != null) {
            return this.firstRentDate;
        } else {
            throw new NeverRentException("Person has never rented before");
        }
    }

    public double checkRentPayments() {
        double sum = 0;
        for (PlaceToRent rent : this.rents) {
            sum += rent.price;
        }
        return sum;
    }

    private double indebtedness(){
        double sum = 0;
        for (TenantAlert tenantAlert : this.alerts){
            sum += tenantAlert.price;
        }
        return sum;
    }

    public String toProblematicTennantException(){
        StringBuilder sb = new StringBuilder();
        sb.append("Person " + this.firstName + " " + this.lastName + " have rented places:\n");
        for (TenantAlert tenantAlert : this.alerts){
            sb.append(tenantAlert.placeId + "\n");
        }
        sb.append("Owes: " + this.indebtedness() + " PLN\n");
        sb.append("Rent denied.");
        return sb.toString();
    }

    public boolean arePaymentsNotExceeded() {
        if (this.checkRentPayments() <= 1250){
            return true;
        } else {
            System.out.println("Payments exceeded - Rent denied");
            return false;
        }
    }

    public boolean hasUnder3Alerts() throws ProblematicTenantException{
        if (this.alerts.size() < 3){
            return true;
        } else {
            throw new ProblematicTenantException(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String basicInfo = "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' + "}\n";
        sb.append(basicInfo);
        sb.append("Rents: \n");
        rents.forEach((r) -> sb.append(r.toString()));
        sb.append("Alerts: \n");
        alerts.forEach((r) -> sb.append(r.toString()));
        sb.append("Vehicles: \n");
        vehicles.forEach((r) -> sb.append(r.toString()));
        return sb.toString();
    }
}
