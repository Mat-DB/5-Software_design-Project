import logic.MoneyTrackerApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        MoneyTrackerApplication moneyTrackerApplication = new MoneyTrackerApplication();
        moneyTrackerApplication.run();
        System.out.println("Application started");
        Logger logger = Logger.getLogger("main");
        logger.setLevel(Level.INFO);
    }
}
