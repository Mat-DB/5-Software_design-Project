package logic.entities.tickets;

import logic.entities.User;

import java.util.ArrayList;

public class TicketEvenSplit extends Ticket {

    //ArrayList<User> debtors;

    public TicketEvenSplit(String name, double price, User paid, ArrayList<User> debtors) {
        super(name, price, paid);
        setDebts(price, debtors);

    }

    private void setDebts(double price, ArrayList<User> debtors) {
        // divide price by debtors and person who paid
        double debt = price / (debtors.size() + 1);

        for (User debtor : debtors)
            debts.put(debtor, debt);

    }



}
