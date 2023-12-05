package logic;

import graphics.ViewFrame;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.users.User;

public class MoneyTrackerApplication {
    ViewFrame appView;

    AppStates state;

    ControllerUsers controllerUsers;

    ControllerTickets controllerTickets;

    Group group;
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
            // click 'create group'
            state = AppStates.CREATE_TICKET;
            // click 'view groups'

        }
        // create a new group for tickets
        if (state == AppStates.CREATE_GROUP) {
            System.out.println("Create group");
            String groupName = "Group1";
            group = new Group(groupName);

            //
            User newParticipant = controllerUsers.createUser("Gones", "Anseel");
            group.addParticipant(newParticipant);
            newParticipant = controllerUsers.createUser("Matthias", "De Beukelaer");
            group.addParticipant(newParticipant);


        }
        // Create tickets in group
        if (state == AppStates.CREATE_TICKET) {
            System.out.println("create ticket");
            // 1. App-user input name-ticket, price, and who paid
            String ticketName = "Airplane Prague";
            double price = 120.0;
            User payer = group.getParticipant("Gones", "Anseel");
            if (payer == null) {
                // create user?
            }



        }




    }

    private enum AppStates {
        HOMESCREEN,
        CREATE_GROUP,
        CREATE_TICKET,

    }
}
