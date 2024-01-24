package logic;

import logic.controllers.ControllerGroups;
import logic.controllers.ControllerHelperFunctions;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.groups.GroupBalancer;
import logic.tickets.TicketInfo;
import logic.users.User;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TicketRestaurant;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;
import logic.factories.FactoryTicket;

import java.util.*;

/**
 * Functions to test during development.
 */
public class testFunctions {
    public enum AppStates {
        HOMESCREEN,
        CREATE_GROUP,
        CREATE_EVENSPLIT,
        CREATE_UNEVENSPLIT,
    }
    private AppStates state;

    private ControllerUsers controllerUsers;

    private ControllerTickets controllerTickets;

    private Group group;

    public testFunctions() {
        controllerUsers = ControllerUsers.getUserController();
        controllerTickets = ControllerTickets.getTicketController();

        state = AppStates.HOMESCREEN;
    }

    public void run() {
                /*\
         TO DO : split run into state machine coupled with buttons/use-cases
                    - create group
                    - add people to group
                    - add ticket : price, person who paid, people who are in debt, how much each debts is, ...
                        - > 'update debts'
                    - update debts of group members
                    - someone pays
                        - > 'update debts'

         */
        System.out.println("Test TicketInfo Events");
        testFunctions.testTicketEvents();

        System.out.println("Test TicketInfo Split Decoration and Factory");
        testFunctions.testTicketFactory();

        System.out.println("Test payment");
        testFunctions.testPayment();
        testFunctions.testTicketList();
        textApplication();
        testFunctions.testGroupBalancer();
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

    public void textApplication() {
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
            System.out.println(ControllerHelperFunctions.convertHashesToUsers(group.getParticipants()));
            int whoPaid = userInput.nextInt();

            Set<Integer> debtors = new HashSet<>();
            for (int part : group.getParticipants()) {
                if (part != whoPaid)
                    debtors.add(part);
            }
            System.out.println(debtors);
            userInput.useDelimiter("\r");

            controllerTickets.createEvenSplitTicket(group, ticketName, price, whoPaid, TypeEvents.AIRPLANE, debtors);

            System.out.println("Create 2nd ticket");
            userInput = new Scanner(System.in);  // Create a Scanner object

            System.out.println("Enter ticket name: ");
            // 1. App-user input name-ticket, price, and selects who paid
            ticketName = userInput.nextLine();

            System.out.println("Enter total price: ");
            price = userInput.nextDouble();

            System.out.println("enter id who paid: ");
            System.out.println(ControllerHelperFunctions.convertHashesToUsers(group.getParticipants()));
            whoPaid = userInput.nextInt();

            debtors = new HashSet<>();
            for (int part : group.getParticipants()) {
                if (part != whoPaid)
                    debtors.add(part);
            }
            System.out.println(debtors);

            controllerTickets.createEvenSplitTicket(group, ticketName, price, whoPaid, TypeEvents.AIRPLANE, debtors);

            System.out.println(ControllerHelperFunctions.convertHashesToTickets(group.getTickets()));

            GroupBalancer.createBalance(group);

            System.out.println(group.getGroupBalances());
        }
    }

    public static void testTicketEvents() {
        User gones = new User("Gones", "Anseel", 1);
        int gonesHash = (gones.getName() + gones.getID()).hashCode();

        TicketInfo ticket = new TicketAirplane("Airplane Prague", 125, gonesHash);

        System.out.println(ticket);


        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getID() + matthias.toString()).hashCode();
        ticket = new TicketRestaurant("Resto b-day", 120, matthiasHash);

        System.out.println(ticket);
    }

    public static void testTicketFactory() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getID() + matthias.toString()).hashCode();
        User gones = new User("Gones", "Anseel", 1);
        int gonesHash = (gones.getName() + gones.getID()).hashCode();


        FactoryTicket ticketFactory = FactoryTicket.getTicketFactory();

        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);

        // 1. user creates airplane ticket
        TicketEvenSplit airplane = ticketFactory.getEvenSplitTicket("Airplane Prague", 5, gonesHash, TypeEvents.AIRPLANE);

        // 2. user adds debtors
        airplane.setInitialBalances(debtors);

        System.out.println(airplane);
        ArrayList<TicketInfo> tickets = new ArrayList<>();
        tickets.add(airplane);
        System.out.println(tickets.get(0) instanceof TicketEvenSplit);

        TicketUnevenSplit restoUnEven = ticketFactory.getUnevenSplitTicket("Resto Stijn", 120, matthiasHash, TypeEvents.RESTAURANT);
        HashMap<Integer, Double> debts = new HashMap<>();
        debts.put(matthiasHash, -20.0);
        debts.put(gonesHash, -100.0);
        restoUnEven.setInitialBalances(debts);

        System.out.println(restoUnEven);
    }

    public static void testPayment() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getID() + matthias.toString()).hashCode();
        User gones = new User("Gones", "Anseel", 1);
        int gonesHash = (gones.getName() + gones.getID()).hashCode();

        FactoryTicket ticketFactory = FactoryTicket.getTicketFactory();

        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);

        // 1. user creates airplane ticket
        TicketEvenSplit airplane = ticketFactory.getEvenSplitTicket("Airplane Prague", 5, gonesHash, TypeEvents.AIRPLANE);

        // 2. user adds debtors
        airplane.setInitialBalances(debtors);

        System.out.println(airplane);
        System.out.println(airplane.isEven());

        airplane.addPayment(matthiasHash, 2.5);

        System.out.println(airplane);
        System.out.println(airplane.isEven());

        TicketUnevenSplit restoUnEven = ticketFactory.getUnevenSplitTicket("Resto Stijn", 120, matthiasHash, TypeEvents.RESTAURANT);
        HashMap<Integer, Double> debts = new HashMap<>();
        debts.put(matthiasHash, -20.0);
        debts.put(gonesHash, -100.0);
        restoUnEven.setInitialBalances(debts);

        System.out.println(restoUnEven);
        System.out.println(restoUnEven.isEven());

        restoUnEven.addPayment(gonesHash, 100);

        System.out.println(restoUnEven);
        System.out.println(restoUnEven.isEven());

    }

    public static void testTicketList() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getID() + matthias.toString()).hashCode();
        User gones = new User("Gones", "Anseel", 1);
        int gonesHash = (gones.getName() + gones.getID()).hashCode();

        ArrayList<TicketInfo> tickets = new ArrayList<>();

        FactoryTicket ticketFactory = FactoryTicket.getTicketFactory();

        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);

        // 1. user creates airplane ticket
        TicketEvenSplit airplane = ticketFactory.getEvenSplitTicket("Airplane Prague", 5, gonesHash, TypeEvents.AIRPLANE);

        // 2. user adds debtors
        airplane.setInitialBalances(debtors);
        System.out.println(airplane);

        tickets.add(airplane);


        TicketUnevenSplit restoUnEven = ticketFactory.getUnevenSplitTicket("Resto Stijn", 120, matthiasHash, TypeEvents.RESTAURANT);
        HashMap<Integer, Double> debts = new HashMap<>();

        debts.put(matthiasHash, -20.0);
        debts.put(gonesHash, -100.0);
        restoUnEven.setInitialBalances(debts);
        System.out.println(restoUnEven);

        tickets.add(restoUnEven);

        System.out.println(tickets);
    }

    public static void testGroupBalancer(){
        ControllerUsers controllerUsers = ControllerUsers.getUserController();
        ControllerTickets controllerTickets = ControllerTickets.getTicketController();
        Group group = new Group("test");

        // controller finds first available ID for User, add user to database and returns key.
        int gKey = controllerUsers.createUser("G", "A");
        group.addParticipant(gKey);
        int mKey = controllerUsers.createUser("M", "DB");
        group.addParticipant(mKey);
        int jdpKey = controllerUsers.createUser("J", "DP");
        group.addParticipant(jdpKey);
        int sKey = controllerUsers.createUser("S", "VRD");
        group.addParticipant(sKey);
        int jgKey = controllerUsers.createUser("J", "G");
        group.addParticipant(jgKey);

        System.out.println(group);

        Set<Integer> debtors = Set.of(mKey, jdpKey, sKey, jgKey);
        controllerTickets.createEvenSplitTicket(group, "To pra", 11, gKey, TypeEvents.AIRPLANE, debtors);
        debtors = Set.of(gKey, jdpKey, sKey, jgKey);
        controllerTickets.createEvenSplitTicket(group, "Fro pra", 12, mKey, TypeEvents.AIRPLANE, debtors);

        System.out.println(ControllerHelperFunctions.convertHashesToTickets(group.getTickets()));

        GroupBalancer.createBalance(group);

        System.out.println(group.getGroupBalances());

        GroupBalancer.calculateWhoPaysWho(group);

        System.out.println(group.getWhoPaysWhoHowMuch());
    }
}
