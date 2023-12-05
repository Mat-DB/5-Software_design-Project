package logic.factories;

import logic.users.User;
import logic.tickets.EventTicket;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TicketCustom;
import logic.tickets.TicketEvents.TicketRestaurant;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;

public class FactoryTicket {
    private static FactoryTicket factory;

    private FactoryTicket(){}

    public static FactoryTicket getTicketFactory() {
        if (factory == null) {
            factory = new FactoryTicket();
        }
        return factory;
    }

//    public Ticket getTicket(String name, double price, User paid, TypeEvents event) {
//        // basic ticket
//        Ticket ticket = switch (split) {
//            case EVEN_SPLIT ->
//                // ticket = even
//                    new TicketEvenSplit(name, price, paid);
//            case UNEVEN_SPLIT ->
//                // ticket = uneven
//                    new TicketUnevenSplit(name, price, paid);
//        };
//
//
//        Ticket ticket = new TicketBase(name, price, paid);
//
//        switch (event) {
//            case AIRPLANE:
//                ticket = new DecoratorTicketAirplane(ticket);
//                break;
//            case RESTAURANT:
//                ticket = new DecoratorTicketRestaurant(ticket);
//                break;
//            case CUSTOM:
//                break;
//
//        }
//        return ticket;
//    }

    public TicketEvenSplit getEvenSplitTicket(String name, double price, User paid, TypeEvents event) {
        return new TicketEvenSplit(getEventTicket(name, price, paid, event));
    }

    public TicketUnevenSplit getUnevenSplitTicket(String name, double price, User paid, TypeEvents event) {
        return new TicketUnevenSplit(getEventTicket(name, price, paid, event));
    }

    private EventTicket getEventTicket(String name, double price, User paid, TypeEvents event) {
        return switch (event) {
            case AIRPLANE -> new TicketAirplane(name, price, paid);
            case RESTAURANT -> new TicketRestaurant(name, price, paid);
            case CUSTOM -> new TicketCustom(name, price, paid);
        };
    }
}
