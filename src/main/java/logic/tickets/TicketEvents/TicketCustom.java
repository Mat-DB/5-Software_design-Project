package logic.tickets.TicketEvents;

import logic.tickets.EventTicket;

public class TicketCustom implements EventTicket {
    String name;
    String description;
    double total;

    int paid;

    public TicketCustom(String name, double total, int paid) {
        this.name = name;
        this.total = total;
        this.paid = paid;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    @Override
    public double getTotal() {
        return total;
    }

    /**
     * @return
     */
    @Override
    public int getWhoPaid() {
        return paid;
    }

    /**
     * @return
     */
    @Override
    public TypeEvents getEvent() {
        return TypeEvents.CUSTOM;
    }

    @Override
    public String toString() {
        return "TicketCustom{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + total +
                ", paid=" + paid +
                '}';
    }
}
