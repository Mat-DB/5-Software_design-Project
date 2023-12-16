package logic.tickets.TicketEvents;

import logic.tickets.EventTicket;

public class TicketRestaurant implements EventTicket {
    String name;
    String description;
    double total;

    int paid;

    public TicketRestaurant(String name, double total, int paid) {
        this.name = name;
        this.total = total;
        this.paid = paid;
    }

    // ToDo: add java doc or remove

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
        return TypeEvents.RESTAURANT;
    }

    @Override
    public String toString() {
        return "TicketRestaurant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", paid=" + paid +
                '}';
    }
}
