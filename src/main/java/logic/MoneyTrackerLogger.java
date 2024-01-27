package logic;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoneyTrackerLogger {
    /**
     * https://www.geeksforgeeks.org/logging-in-java/
     */
    private Level logLevel;
    private ConsoleHandler handler;
    private static MoneyTrackerLogger instance;
    private MoneyTrackerLogger(Level logLevel) {
        this.logLevel = logLevel;
        this.handler = new ConsoleHandler();
    }

    public static void setLogLevel(Level logLevel) {
        if (instance == null) {
            instance = new MoneyTrackerLogger(logLevel);
        }
        instance.setLevel(logLevel);
    }

    private void setLevel(Level logLevel) {
        this.logLevel = logLevel;
        handler.setLevel(logLevel);
    }

    public static MoneyTrackerLogger getInstance() {
        if (instance == null) {
            instance = new MoneyTrackerLogger(Level.INFO);
        }
        return instance;
    }

    public Logger getLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.setLevel(logLevel);
        logger.addHandler(handler);
        return logger;
    }
}
