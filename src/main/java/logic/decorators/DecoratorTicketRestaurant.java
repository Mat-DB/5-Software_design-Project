package logic.decorators;

import logic.entities.tickets.Ticket;
import logic.entities.tickets.TypeEvents;

public class DecoratorTicketRestaurant extends DecoratorTicket{
    TypeEvents typeEvent = TypeEvents.RESTAURANT;

    public DecoratorTicketRestaurant(Ticket decoratedTicket) {
        super(decoratedTicket);

    }

    /**
     * @param event
     * @return
     */
    @Override
    public TypeEvents getEvent(TypeEvents event) {
        return typeEvent;
    }

    @Override
    public String toString() {
        return "Airplane" + decoratedTicket.toString();
    }

}
