package logic;

import graphics.ViewFrame;
import logic.controllers.ControllerHelperFunctions;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.groups.GroupBalancer;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashSet;
import java.util.Objects;
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
        //testFunctions.testTicketSplit();
        //testFunctions.globalTest();
        //System.out.println("Test Ticket Events");
        //testFunctions.testTicketEvents();

        //System.out.println("Test Ticket Split Decoration and Factory");
        //testFunctions.testTicketFactory();

        //System.out.println("Test payment");
        //testFunctions.testPayment();
        //testFunctions.testTicketList();
        //textApplication();
        testFunctions.testGroupBalancer();
    }
    private void textApplication() {
        if (state == AppStates.HOMESCREEN) {
            System.out.println("Home screen");
            appView.homescreen();
            // click 'Start' - > 'Create Group'
            state = AppStates.CREATE_GROUP;

        }
        // create a new group for tickets
        if (state == AppStates.CREATE_GROUP) {
            System.out.println("Add participants");

            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter groupname");

            String groupName = userInput.nextLine();
            group = new Group(groupName);

            System.out.println("First name: ");
            String firstName = userInput.nextLine();
            System.out.println("Last name: ");
            String lastName = userInput.nextLine();

            // controller finds first available ID for User, add user to database and returns key.
            int participantKey = controllerUsers.createUser(firstName, lastName);
            group.addParticipant(participantKey);

            System.out.println("First name: ");
            firstName = userInput.nextLine();
            System.out.println("Last name: ");
            lastName = userInput.nextLine();

            participantKey = controllerUsers.createUser(firstName, lastName);
            group.addParticipant(participantKey);

            System.out.println(group);

            state = AppStates.CREATE_EVENSPLIT;

        }
        // Create tickets in group
        if (state == AppStates.CREATE_EVENSPLIT) {
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

            Set<Integer> debtors = new HashSet<>();
            for (int part : group.getParticipants()) {
                if (part != whoPaid)
                    debtors.add(part);
            }
            System.out.println(debtors);
            userInput.useDelimiter("\r");

            group.addTicket(controllerTickets.createEvenSplitTicket(ticketName, price, whoPaid, TypeEvents.AIRPLANE, debtors));

            System.out.println("Create 2nd ticket");
            userInput = new Scanner(System.in);  // Create a Scanner object

            System.out.println("Enter ticket name: ");
            // 1. App-user input name-ticket, price, and selects who paid
            ticketName = userInput.nextLine();

            System.out.println("Enter total price: ");
            price = userInput.nextDouble();

            System.out.println("enter id who paid: ");
            System.out.println(ControllerHelperFunctions.convertHashToUsers(group.getParticipants(), controllerUsers));
            whoPaid = userInput.nextInt();

            debtors = new HashSet<>();
            for (int part : group.getParticipants()) {
                if (part != whoPaid)
                    debtors.add(part);
            }
            System.out.println(debtors);

            group.addTicket(controllerTickets.createEvenSplitTicket(ticketName, price, whoPaid, TypeEvents.AIRPLANE, debtors));

            System.out.println(ControllerHelperFunctions.convertHashToTickets(group.getTickets(), controllerTickets));

            GroupBalancer.createBalance(group, controllerTickets);

            System.out.println(group.getGroupBalances());
        }




    }

    private enum AppStates {
        HOMESCREEN,
        CREATE_GROUP,

        CREATE_EVENSPLIT,
        CREATE_UNEVENSPLIT,


    }
}
