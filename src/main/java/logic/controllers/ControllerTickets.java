package logic.controllers;

import logic.database.DatabaseTickets;
import logic.entities.tickets.Ticket;

public class ControllerTickets {
    private static ControllerTickets controller;
    private DatabaseTickets ticketDB;

    private ControllerTickets() {
        ticketDB = DatabaseTickets.getTicketDatabase();
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

}
