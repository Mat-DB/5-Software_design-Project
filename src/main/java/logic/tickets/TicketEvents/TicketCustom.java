package logic.tickets.TicketEvents;

import logic.tickets.TicketInfo;

public class TicketCustom implements TicketInfo {
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

    public TicketCustom(String name, double total, int paid) {
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
        return TypeEvents.CUSTOM;
    }

    @Override
    public String toString() {
        return "TicketCustom{" +
                "name='" + name + '\'' +
                ", price=" + total +
                ", paid=" + paid +
                '}';
    }
}
