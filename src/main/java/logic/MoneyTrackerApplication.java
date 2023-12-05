package logic;

import graphics.ViewFrame;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.users.User;

public class MoneyTrackerApplication {
    ViewFrame appView;

    AppStates state;

    ControllerUsers controllerUsers;

    ControllerTickets controllerTickets;
    public MoneyTrackerApplication() {
        init();
    }

    public void init() {
        appView = new ViewFrame();
        controllerUsers = ControllerUsers.getUserController();
        controllerTickets = ControllerTickets.getTicketController();
        appView.initialize();

        state = AppStates.HOMESCREEN;
    }

    public void run() {

        /*\
         TODO : split run into state machine coupled with buttons/use-cases
                    - create group
                    - add people to group
                    - add ticket : price, person who paid, people who are in debt, how much each debts is, ...
                        - > 'update debts'
                    - update debts of group members
                    - someone pays
                        - > 'update debts'

         */
        //System.out.println("Test Ticket Events");
        //testFunctions.testTicketEvents();

        //System.out.println("Test Ticket Split Decoration and Factory");
        //testFunctions.testTicketFactory();

        //System.out.println("Test payment");
        //testFunctions.testPayment();
        if (state == AppStates.HOMESCREEN) {
            System.out.println("Home screen");
            appView.homescreen();
            // click 'create ticket'
            state = AppStates.CREATE_TICKET;
            // click 'view tickets'

            // click 'get total'
        }
        if (state ==AppStates.CREATE_TICKET) {
            System.out.println("create ticket");
            // App-user input name-ticket, price, and who paid
            User payer = controllerUsers.createUser("Gones", "Anseel");
        }



    }

    private enum AppStates {
        HOMESCREEN,
        CREATE_TICKET,

    }
}
