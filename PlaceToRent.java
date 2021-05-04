package proj_1;

import java.time.LocalDate;
import java.util.ArrayList;

abstract public class PlaceToRent extends Place {
    ArrayList<Person> people = new ArrayList<>();
    String startDate;
    String endDate;
    final double price;
    boolean hasAlert = false;

    public PlaceToRent(double volume, String type, double price) {
        super(volume, type);
        this.price = price;
    }

    public boolean isExpired() {
        if (endDate == null) {
            return false;
        }
        return Main.baseDate.isAfter(LocalDate.parse(endDate));
    }

    public void renew() {
        this.startDate = Main.baseDate.toString();
        this.endDate = Main.baseDate.plusDays(14).toString();
        this.hasAlert = false;
        System.out.println(this + " has been renewed.");
        System.out.println("Next end date:" + this.endDate);
    }

    private boolean checkToClean() {
        return endDate != null && Main.baseDate.isAfter(LocalDate.parse(endDate).plusDays(15));
    }

    public void cleanPlace(Service service) {
        if (checkToClean()) {
            Person rentee = this.people.get(0);
            TenantAlert tenantAlert = new TenantAlert(this.id + " not payed for 30 days has been cleaned");
            tenantAlert.price = this.price;
            tenantAlert.placeId = this.id;
            rentee.alerts.add(tenantAlert);
            service.tenantAlerts.add(tenantAlert);
            this.people.clear();
            this.startDate = null;
            this.endDate = null;
            this.hasAlert = false;
            this.items.clear();
        }
    }

    public void sendAlert(Service service){
        if (this.isExpired() && !this.hasAlert){
            Person rentee = this.people.get(0);
            TenantAlert tenantAlert = new TenantAlert(this.id + " has not been payed for 14 days");
            tenantAlert.price = this.price;
            tenantAlert.placeId = this.id;
            rentee.alerts.add(tenantAlert);
            service.tenantAlerts.add(tenantAlert);
            this.hasAlert = true;
        }
    }

    @Override
    public String toString() {
        if (startDate == null && endDate == null)
            return "{" +
                    "id='" + id + '\'' +
                    ", price=" + price +
                    ", volume=" + volume +
                    "}\n";
        else
            return "{" +
                    "id='" + id + '\'' +
                    ", price=" + price +
                    ", volume=" + volume +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    "}\n";
    }
}
