package proj_1;

public class TenantAlert {
    String alert, placeId;
    double price;

    public TenantAlert(String alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + alert + '\'' +
                ", price=" + price +
                "}\n";
    }
}
