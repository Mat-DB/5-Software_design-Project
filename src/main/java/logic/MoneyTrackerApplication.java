package logic;

import graphics.ViewFrame;
import logic.database.DatabaseGroups;
import logic.database.DatabaseTickets;
import logic.database.DatabaseUsers;

public class MoneyTrackerApplication {
    private ViewFrame appView;
    public MoneyTrackerApplication() {
        init();
    }

    public void init() {
        System.out.println("Initializing the money tracker application.");
        appView = new ViewFrame();
        DatabaseUsers.getUserDatabase().addObserver(appView);
        DatabaseGroups.getGroupDatabase().addObserver(appView);
        DatabaseTickets.getTicketDatabase().addObserver(appView);
        System.out.println("Money tracker initialized.");
    }

    public void run() {
        appView.init();
    }
}
