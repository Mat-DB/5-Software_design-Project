package logic.entities.tickets;

import logic.entities.User;

import java.util.HashMap;

public class TicketUnevenSplit extends Ticket{
    public TicketUnevenSplit(String name, double price, User paid, HashMap<User, Double> debts) {
        super(name, price, paid);
        this.debts = debts;
    }

}
