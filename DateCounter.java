package proj_1;

import java.util.concurrent.TimeUnit;

public class DateCounter implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.baseDate = Main.baseDate.plusDays(1);

            // inform about finished reapair
            if (Main.selectedPerson != null) {
                for (Vehicle v : Main.selectedPerson.vehicles) {
                    v.isRepairEnded();
                }
            }

            // check if css are free
            if (Main.selectedService != null && Main.selectedService.selectFreeCarServiceSpots() != null){
                for (Vehicle v : Main.selectedService.vehiclesToRepair) {
                    Main.selectedService.selectFreeCarServiceSpots().vehicle = v;
                    System.out.println(v + " has been put to repair spot");
                }
            }
        }
    }
}
