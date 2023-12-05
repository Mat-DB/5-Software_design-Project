package logic.tickets.TicketEvents;

import logic.users.User;
import logic.tickets.EventTicket;

public class TicketAirplane implements EventTicket {
    String name;
    String description;

    /**
     * total price of the ticket
     */
    double total;

    User paid;


    public TicketAirplane(String name, double total, User paid) {
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
    public User whoPaid() {
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
