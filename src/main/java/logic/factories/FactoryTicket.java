package logic.factories;

import logic.decorators.DecoratorTicketAirplane;
import logic.decorators.DecoratorTicketRestaurant;
import logic.entities.tickets.*;

public class FactoryTicket {
    private static FactoryTicket factory;

    private FactoryTicket(){}

    public static FactoryTicket getTicketFactory() {
        if (factory == null) {
            factory = new FactoryTicket();
        }
        return factory;
    }

    public Ticket getTicket(TypeEvents event, TypeSplit split) {
        // basic ticket
        Ticket ticket = switch (split) {
            case EVEN_SPLIT ->
                // ticket = even
                    new TicketEvenSplit();
            case UNEVEN_SPLIT ->
                // ticket = uneven
                    new TicketUnevenSplit();
        };

        switch (event) {
            case AIRPLANE:
                ticket = new DecoratorTicketAirplane(ticket);
                break;
            case RESTAURANT:
                ticket = new DecoratorTicketRestaurant(ticket);
                break;
            case CUSTOM:
                break;

        }
        return ticket;
    }
}
