package logic;

import graphics.ViewFrame;
import logic.controllers.ControllerGroups;
import logic.controllers.ControllerHelperFunctions;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.database.DatabaseGroups;
import logic.database.DatabaseTickets;
import logic.database.DatabaseUsers;
import logic.groups.Group;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        DatabaseUsers.getUserDatabase().addObserver(appView);
        DatabaseGroups.getGroupDatabase().addObserver(appView);
        DatabaseTickets.getTicketDatabase().addObserver(appView);
        state = AppStates.HOMESCREEN;
    }

    public void addUsers() {
        String groupName = "gones_1";

        System.out.println("First name: ");
        String firstName = "g";
        System.out.println("Last name: ");
        String lastName = "a";

        // controller finds first available ID for User, add user to database and returns key.
        int participantKey = controllerUsers.createUser(firstName, lastName);
        Set<Integer> memberKeys = new HashSet<>();
        memberKeys.add(participantKey);

        System.out.println("First name: ");
        firstName = "g";
        System.out.println("Last name: ");
        lastName = "a";

        participantKey = controllerUsers.createUser(firstName, lastName);
        memberKeys.add(participantKey);

        ControllerGroups.getGroupController().createGroup(groupName, memberKeys);
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
        //testFunctions.testTicketSplit();
        //testFunctions.globalTest();
        //System.out.println("Test Ticket Events");
        //testFunctions.testTicketEvents();

        //System.out.println("Test Ticket Split Decoration and Factory");
        //testFunctions.testTicketFactory();

        //System.out.println("Test payment");
        //testFunctions.testPayment();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        textApplication();
    }
    private void textApplication() {
        if (state == AppStates.HOMESCREEN) {
            System.out.println("Home screen");
            // click 'Start' - > 'Create Group'
            state = AppStates.CREATE_GROUP;

        }
        // create a new group for tickets
        if (state == AppStates.CREATE_GROUP) {
            System.out.println("Add participants");

            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter groupname");

            //String groupName = userInput.nextLine();
            String groupName = "test";
            group = new Group(groupName);

            System.out.println("First name: ");
            String firstName = "g";
            System.out.println("Last name: ");
            String lastName = "a";

            // controller finds first available ID for User, add user to database and returns key.
            int participantKey = controllerUsers.createUser(firstName, lastName);
            group.addParticipant(participantKey);

            System.out.println("First name: ");
            firstName = "g";
            System.out.println("Last name: ");
            lastName = "a";

            participantKey = controllerUsers.createUser(firstName, lastName);
            group.addParticipant(participantKey);

            System.out.println(group);

            state = AppStates.CREATE_TICKET;

        }
        // Create tickets in group
        if (state == AppStates.CREATE_TICKET) {
            System.out.println("create ticket");
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter ticket name: ");
            // 1. App-user input name-ticket, price, and selects who paid
            String ticketName = userInput.nextLine();

            System.out.println("Enter total price: ");
            double price = userInput.nextDouble();

            System.out.println("enter id who paid: ");
            System.out.println(ControllerHelperFunctions.convertHashToUsers(group.getParticipants(), controllerUsers));
            int whoPaid = userInput.nextInt();

            //User payer = group.getParticipant();
        }
    }

    public enum AppStates {
        HOMESCREEN,
        CREATE_GROUP,
        CREATE_TICKET,

    }
}
