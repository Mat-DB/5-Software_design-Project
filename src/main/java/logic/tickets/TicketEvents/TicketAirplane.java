package logic.tickets.TicketEvents;

import logic.tickets.TicketInfo;

public class TicketAirplane implements TicketInfo {
    /**
     * Name of the ticket
     */
    private final String name;

    /**
     * Total price of the ticket
     */
    double total;
    /**
     * Hash ID of the user who paid the ticket
     */
    int paid;

    public TicketAirplane(String name, double total, int paid) {
        this.name = name;
        this.total = total;
        this.paid = paid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public int getWhoPaid() {
        return paid;
    }

    @Override
    public TypeEvents getEventType() {
       return TypeEvents.AIRPLANE;
    }

    @Override
    public String toString() {
        return "TicketAirplane{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", paid=" + paid +
                '}';
    }
}
