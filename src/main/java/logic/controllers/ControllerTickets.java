package logic.controllers;

import logic.database.DatabaseTickets;
import logic.groups.Group;
import logic.tickets.Ticket;
import logic.factories.FactoryTicket;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;

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

    public Ticket getTicket(int id) {
        return ticketDB.getTicket(id);
    }

    public Ticket getTicket(String ticketName) {
        return ticketDB.getTicket(getTicketHash(ticketName));
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
     * @param debtors a set of the hash values of the users who needs to pay
     * @return the hash of the ticket
     */
    public int createEvenSplitTicket(Group group, String name, double price, int paid, TypeEvents event, Set<Integer> debtors) {
        TicketEvenSplit ticket = ticketFactory.getEvenSplitTicket(name, price, paid, event);
        ticket.setInitialBalances(debtors);
        int hash = ticketDB.addTicket(ticket);
        group.addTicket(hash);
        return hash;
    }

    /**
     * Create a new ticket.
     * @param name name of the ticket
     * @param price total amount of the ticket
     * @param paid the hash of the user who paid
     * @param event type of event
     * @param balances a hashmap of the hash values of the users who needs to pay and the amount
     * @return the hash of the ticket
     */
    public int createUnevenSplitTicket(Group group, String name, double price, int paid, TypeEvents event, HashMap<Integer, Double> balances) {
        TicketUnevenSplit ticket = ticketFactory.getUnevenSplitTicket(name, price, paid, event);
        ticket.setInitialBalances(balances);
        int hash = ticketDB.addTicket(ticket);
        group.addTicket(hash);
        return hash;
    }

    public void removeTicket(int ticketHash) {
        ControllerGroups controllerGroups = ControllerGroups.getGroupController();
        Set<Integer> groups = controllerGroups.findTicketInGroup(ticketHash);
        for (int groupHash : groups) {
            Group group = controllerGroups.getGroup(groupHash);
            group.removeTicket(ticketHash);
        }
        ticketDB.removeTicket(ticketHash);
    }

    public boolean doesTicketNameExist(String ticketName) {
        return (ticketDB.getTicket(ticketDB.getTicketHash(ticketName)) != null);
    }
}
