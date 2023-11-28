package logic.decorators;

import logic.entities.tickets.Ticket;
import logic.entities.tickets.TypeEvents;

public class DecoratorTicketAirplane extends DecoratorTicket{
    TypeEvents typeEvent = TypeEvents.AIRPLANE;
    public DecoratorTicketAirplane(Ticket decoratedTicket) {
        super(decoratedTicket);
    }

    /**
     *
     */
    @Override
    public TypeEvents getEvent(TypeEvents event) {
        return typeEvent;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Airplane" + decoratedTicket.toString();
    }


}
