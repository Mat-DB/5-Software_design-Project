package logic.tickets;

import logic.tickets.TicketEvents.TypeEvents;

public interface EventTicket extends Ticket{
    TypeEvents getEvent();
}
