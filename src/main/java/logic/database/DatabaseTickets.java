package logic.database;

import logic.tickets.Ticket;

public class DatabaseTickets extends Database<Ticket> {
    private static DatabaseTickets instance;
    private DatabaseTickets() {
        super();
    }

    public static DatabaseTickets getTicketDatabase() {
        if (instance == null) {
            instance = new DatabaseTickets();
        }
        return instance;
    }

    public int addTicket(Ticket ticket){
        int hash = getTicketHash(ticket.getName(), ticket.getTotal(), ticket.getWhoPaid());
        this.addEntry(hash, ticket);
        observable.firePropertyChange("new ticket", null, ticket);
        return hash;
    }

    public Ticket getTicket(int id) {
        return this.getEntry(id);
    }

    public void removeTicket(int ticketHash) {
        Ticket ticket = getEntry(ticketHash);
        observable.firePropertyChange("remove ticket", ticket, null);
        removeEntry(ticketHash);
    }

    private int getTicketHash(String name, double price, int whoPaid) {
        return (name+price+whoPaid).hashCode();
    }
}
