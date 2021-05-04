package proj_1;

import java.util.concurrent.TimeUnit;

public class RentChecker implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Main.selectedService != null) {
                for (Service service : Main.services) {
                    service.checkRents();
                }
            }
        }
    }
}
