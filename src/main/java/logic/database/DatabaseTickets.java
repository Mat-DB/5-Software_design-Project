package logic.database;

import logic.entities.tickets.Ticket;

public class DatabaseTickets extends Database<Ticket> {
    private static DatabaseTickets ticketDB;
    private DatabaseTickets() {}

    public static DatabaseTickets getTicketDatabase() {
        if (ticketDB == null) {
            ticketDB = new DatabaseTickets();
        }
        return ticketDB;
    }

    public void addTicket(Ticket ticket){
        int id = ticket.getName().hashCode();
        this.addEntry(id, ticket);
    }

    public Ticket getTicket(int id) {
        return this.getEntry(id);
    }
}
