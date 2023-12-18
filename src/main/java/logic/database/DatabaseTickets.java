package logic.database;

import logic.tickets.TicketInfo;

public class DatabaseTickets extends Database<TicketInfo> {
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

    public int addTicket(TicketInfo ticket){
        int hash = getTicketHash(ticket.getName());
        this.addEntry(hash, ticket);
        observable.firePropertyChange("new ticket", null, ticket);
        return hash;
    }

    public TicketInfo getTicket(int id) {
        return this.getEntry(id);
    }

    public void removeTicket(int ticketHash) {
        TicketInfo ticket = getEntry(ticketHash);
        observable.firePropertyChange("remove ticket", ticket, null);
        removeEntry(ticketHash);
    }

    public int getTicketHash(String name) {
        return name.hashCode();
    }
}
