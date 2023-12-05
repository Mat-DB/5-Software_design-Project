package logic.tickets;

import logic.users.User;

public interface Ticket {
    String getName();

    double getTotal();

    User whoPaid();
}

