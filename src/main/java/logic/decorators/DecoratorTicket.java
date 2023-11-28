package logic.decorators;

import logic.entities.tickets.Ticket;
import logic.entities.tickets.TypeEvents;

public abstract class DecoratorTicket extends Ticket {
    protected Ticket decoratedTicket;
    public DecoratorTicket (Ticket decoratedTicket) {
        this.decoratedTicket = decoratedTicket;
    }

    public abstract TypeEvents getEvent(TypeEvents event);

    @Override
    public abstract String toString();
}
