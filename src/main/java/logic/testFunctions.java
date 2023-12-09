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

        Ticket ticket = new TicketAirplane("Airplane Prague", 125, gones);

        System.out.println(ticket);


        User matthias = new User("Matthias", "De Beukelaer", 2);
        ticket = new TicketRestaurant("Resto b-day", 120, matthias);

        System.out.println(ticket);
    }

    public static void testTicketFactory() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        User gones = new User("Gones", "Anseel", 1);


        FactoryTicket ticketFactory = FactoryTicket.getTicketFactory();

        Set<User> debtors = new HashSet<>();
        debtors.add(matthias);

        // 1. user creates airplane ticket
        TicketEvenSplit airplane = ticketFactory.getEvenSplitTicket("Airplane Prague", 5, gones, TypeEvents.AIRPLANE);

        // 2. user adds debtors
        airplane.setInitialBalances(debtors);

        System.out.println(airplane);
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(airplane);
        System.out.println(tickets.get(0) instanceof TicketEvenSplit);

        TicketUnevenSplit restoUnEven = ticketFactory.getUnevenSplitTicket("Resto Stijn", 120, matthias, TypeEvents.RESTAURANT);
        HashMap<User, Double> debts = new HashMap<>();
        debts.put(matthias, -20.0);
        debts.put(gones, -100.0);
        restoUnEven.setInitialBalances(debts);

        System.out.println(restoUnEven);
    }

    public static void testPayment() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        User gones = new User("Gones", "Anseel", 1);


        FactoryTicket ticketFactory = FactoryTicket.getTicketFactory();

        Set<User> debtors = new HashSet<>();
        debtors.add(matthias);

        // 1. user creates airplane ticket
        TicketEvenSplit airplane = ticketFactory.getEvenSplitTicket("Airplane Prague", 5, gones, TypeEvents.AIRPLANE);

        // 2. user adds debtors
        airplane.setInitialBalances(debtors);

        System.out.println(airplane);
        System.out.println(airplane.isEven());

        airplane.addPayment(matthias, 2.5);

        System.out.println(airplane);
        System.out.println(airplane.isEven());

        TicketUnevenSplit restoUnEven = ticketFactory.getUnevenSplitTicket("Resto Stijn", 120, matthias, TypeEvents.RESTAURANT);
        HashMap<User, Double> debts = new HashMap<>();
        debts.put(matthias, -20.0);
        debts.put(gones, -100.0);
        restoUnEven.setInitialBalances(debts);

        System.out.println(restoUnEven);
        System.out.println(restoUnEven.isEven());

        restoUnEven.addPayment(gones, 100);

        System.out.println(restoUnEven);
        System.out.println(restoUnEven.isEven());

    }

}
