package logic.database;

import logic.entities.Ticket;

public class DatabaseTickets extends Database<Ticket> {
    public DatabaseTickets() {}

    public void addTicket(Ticket ticket){
        int id = ticket.getName().hashCode();
        this.addEntry(id, ticket);
    }

    public Ticket getTicket(int id) {
        return this.getEntry(id);
    }
}
