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

    public void addTicket(Ticket ticket){
        int id = getTicketHash(ticket.getName(), ticket.getPrice());
        this.addEntry(id, ticket);
    }

    public Ticket getTicket(int id) {
        return this.getEntry(id);
    }

    public void removeTicket(Ticket ticket) {
        int id = getTicketHash(ticket.getName(), ticket.getPrice());
        removeEntry(id);
    }

    private int getTicketHash(String name, double price) {
        return (name+price).hashCode();
    }
}
