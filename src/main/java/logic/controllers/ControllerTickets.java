package logic.controllers;

import logic.database.DatabaseTickets;
import logic.tickets.Ticket;
import logic.factories.FactoryTicket;

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

    public Ticket createTicket() {
        // ToDo: implement this methode, depends on the factory
        //Ticket newTicket = ticketFactory;
        //ticketDB.addTicket(newTicket);
        return null;
    }

    public void removeTicket(Ticket ticket) {
        ticketDB.removeTicket(ticket);
    }
}
