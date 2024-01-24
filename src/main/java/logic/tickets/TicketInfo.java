package logic.tickets;

import logic.tickets.TicketEvents.TypeEvents;

public interface TicketInfo {
    String getName();

    double getTotal();

    int getWhoPaid();

    TypeEvents getEventType();
}
