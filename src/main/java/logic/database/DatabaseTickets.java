package logic.database;

import logic.tickets.Ticket;

public class DatabaseTickets extends Database<Ticket> {
    private static DatabaseTickets instance;
    private DatabaseTickets() {}

    public static DatabaseTickets getTicketDatabase() {
        if (instance == null) {
            instance = new DatabaseTickets();
        }
        return instance;
    }

    public void addTicket(Ticket ticket){
        int id = ticket.getName().hashCode();
        this.addEntry(id, ticket);
    }

    public Ticket getTicket(int id) {
        return this.getEntry(id);
    }
}
