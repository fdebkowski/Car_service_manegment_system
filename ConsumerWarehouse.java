package proj_1;

import java.util.ArrayList;

public class ConsumerWarehouse extends PlaceToRent {

    public ConsumerWarehouse(double volume, double price) {
        super(volume, "CW_", price);
    }

    @Override
    public String toString() {
        return super.toString() + "Items:" + items.toString() + "\n";
    }
}
