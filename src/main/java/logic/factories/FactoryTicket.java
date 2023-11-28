package logic.factories;

import logic.entities.tickets.Ticket;
import logic.entities.tickets.TypeEvents;
import logic.entities.tickets.TypeSplit;

public class FactoryTicket {

    public Ticket getTicket(TypeEvents event, TypeSplit split) {
        // basic ticket

        switch (split) {
            case EVEN_SPLIT:
                // ticket = even
            case UNEVEN_SPLIT:
                // ticket = uneven
        }

        switch (event) {
            case AIRPLANE:

            case RESTAURANT:

            case CUSTOM:

        }
        return null;
    }
}