package proj_1;

public class Item {
    double volume;

    public Item(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "{" +
                "volume=" + volume +
                '}';
    }

    public double getVolume() {
        return volume;
    }
}
