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
    private final DatabaseTickets ticketDB;
    private final FactoryTicket ticketFactory;

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

    public int getTicketHash(String ticketName) {
        return ticketDB.getTicketHash(ticketName);
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

    public boolean doesTicketNameExist(String ticketName) {
        return (ticketDB.getTicket(ticketDB.getTicketHash(ticketName)) != null);
    }
}
