import logic.MoneyTrackerApplication;
import logic.MoneyTrackerLogger;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        MoneyTrackerLogger.setLogLevel(Level.INFO);
        MoneyTrackerApplication moneyTrackerApplication = new MoneyTrackerApplication();
        moneyTrackerApplication.run();
        System.out.println("Application started");
    }
}
