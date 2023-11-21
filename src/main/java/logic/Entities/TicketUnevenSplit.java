package logic.Entities;

import java.util.HashMap;

public class TicketUnevenSplit extends Ticket{
    public TicketUnevenSplit(double price, User paid, HashMap<User, Double> debts) {
        super(price, paid);
        this.debts = debts;
    }

}
