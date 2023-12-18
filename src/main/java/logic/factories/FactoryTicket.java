package logic.factories;

import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TicketCustom;
import logic.tickets.TicketEvents.TicketRestaurant;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketInfo;
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

    public TicketEvenSplit getEvenSplitTicket(String name, double price, int paid, TypeEvents event) {
        return new TicketEvenSplit(getEventTicket(name, price, paid, event));
    }

    public TicketUnevenSplit getUnevenSplitTicket(String name, double price, int paid, TypeEvents event) {
        return new TicketUnevenSplit(getEventTicket(name, price, paid, event));
    }

    private TicketInfo getEventTicket(String name, double price, int paid, TypeEvents event) {
        return switch (event) {
            case AIRPLANE -> new TicketAirplane(name, price, paid);
            case RESTAURANT -> new TicketRestaurant(name, price, paid);
            case CUSTOM -> new TicketCustom(name, price, paid);
        };
    }
}
