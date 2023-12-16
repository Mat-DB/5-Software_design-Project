package logic.controllers;

import logic.database.DatabaseTickets;
import logic.tickets.Ticket;
import logic.factories.FactoryTicket;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TypeSplit;

/**
 * This is the controller of the tickets.
 * Properties:
 * - Singleton
 * - Manages the tickets database
 */
public class ControllerTickets {
    private static ControllerTickets controller;
    private DatabaseTickets ticketDB;
    private FactoryTicket ticketFactory;

    private ControllerTickets() {
        ticketDB = DatabaseTickets.getTicketDatabase();
        ticketFactory = FactoryTicket.getTicketFactory();
    }

    public static ControllerTickets getTicketController() {
        if (controller == null) {
            controller = new ControllerTickets();
        }
        return controller;
    }

    public void addTicketToDB(Ticket ticket) {
        ticketDB.addTicket(ticket);
    }

    public Ticket getTicket(int id) {
        return ticketDB.getTicket(id);
    }

    /**
     * Create a new ticket.
     * @param name name of the ticket
     * @param price total amount of the ticket
     * @param paid the hash of the user who paid
     * @param event type of event
     * @param split type of split, even or uneven
     * @return the hash of the ticket
     */
    public int createTicket(String name, double price, int paid, TypeEvents event, TypeSplit split) {
        Ticket ticket = switch (split) {
            case EVEN_SPLIT -> ticketFactory.getEvenSplitTicket(name, price, paid, event);
            case UNEVEN_SPLIT -> ticketFactory.getUnevenSplitTicket(name, price, paid, event);
        };
        return ticketDB.addTicket(ticket);
    }

    public void removeTicket(int ticketHash) {
        ticketDB.removeTicket(ticketHash);
    }
}
