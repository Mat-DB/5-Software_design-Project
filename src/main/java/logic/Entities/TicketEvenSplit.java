package logic.Entities;

import java.util.ArrayList;

public class TicketEvenSplit extends Ticket {

    //ArrayList<User> debtors;

    public TicketEvenSplit(double price, User paid, ArrayList<User> debtors) {
        super(price, paid);
        setDebts(price, debtors);

    }

    private void setDebts(double price, ArrayList<User> debtors) {
        // divide price by debtors and person who paid
        double debt = price / (debtors.size() + 1);

        for (User debtor : debtors)
            debts.put(debtor, debt);

    }



}
