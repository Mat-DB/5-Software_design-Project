package logic.controllers;

import logic.database.DatabaseTickets;
import logic.tickets.Ticket;
import logic.factories.FactoryTicket;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;
import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

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

    public int createEvenSplitTicket(String name, double price, int paid, TypeEvents event, Set<Integer> debtors) {
        TicketEvenSplit ticket = ticketFactory.getEvenSplitTicket(name, price, paid, event);
        ticket.setInitialBalances(debtors);
        return ticketDB.addTicket(ticket);
    }

    public int createUnevenSplitTicket(String name, double price, int paid, TypeEvents event, HashMap<Integer, Double> balances) {
        TicketUnevenSplit ticket = ticketFactory.getUnevenSplitTicket(name, price, paid, event);
        ticket.setInitialBalances(balances);
        return ticketDB.addTicket(ticket);
    }

    public void removeTicket(int ticketHash) {
        ticketDB.removeTicket(ticketHash);
    }
}
