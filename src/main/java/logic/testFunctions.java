package logic;

import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.users.User;
import logic.tickets.Ticket;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TicketRestaurant;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;
import logic.factories.FactoryTicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class testFunctions {
    public static void globalTest() {
        ControllerUsers userController = ControllerUsers.getUserController();
        ControllerTickets ticketController = ControllerTickets.getTicketController();


    }

    public static void testTicketEvents() {
        User gones = new User("Gones", "Anseel", 1);
        int gonesHash = (gones.getName() + gones.getID()).hashCode();

        Ticket ticket = new TicketAirplane("Airplane Prague", 125, gonesHash);

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
        ArrayList<Ticket> tickets = new ArrayList<>();
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

}
