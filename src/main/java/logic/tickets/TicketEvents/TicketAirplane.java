package logic.tickets.TicketEvents;

import logic.tickets.TicketInfo;

public class TicketAirplane implements TicketInfo {
    String name;
    String description;

    /**
     * total price of the ticket
     */
    double total;

    int paid;


    public TicketAirplane(String name, double total, int paid) {
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
       return TypeEvents.AIRPLANE;
    }


    @Override
    public String toString() {
        return "TicketAirplane{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", paid=" + paid +
                '}';
    }
}
