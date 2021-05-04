package proj_1;

import java.util.ArrayList;

abstract public class Place {
    final String id;
    final double volume;
    String type;
    boolean isOccupied;
    ArrayList<Item> items = new ArrayList<>();

    public Place(double volume, String type) {
        this.type = type;
        this.id = type + generateId();
        this.volume = volume;
        this.isOccupied = false;
    }

    public static String generateId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append((int) (Math.random() * 10));
        }
        if (Main.ids.contains(sb.toString()))
            generateId();
        return sb.toString();
    }

    public double filled_volume() {
        double fVol = 0;
        for (Item i : this.items) {
            fVol += i.volume;
        }
        return fVol;
    }

    public void insertIntoPlace(Item i) throws TooManyThingsException {
        if (this.filled_volume() + i.volume > this.volume) {
            throw new TooManyThingsException();
        } else {
            this.items.add(i);
        }
    }

    public double getVolume() {
        return volume;
    }
}
