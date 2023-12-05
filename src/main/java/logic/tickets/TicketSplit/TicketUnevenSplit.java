package logic.tickets.TicketSplit;

import logic.users.User;
import logic.tickets.EventTicket;

import java.util.HashMap;
import java.util.HashSet;

public class TicketUnevenSplit extends DecoratorTicketSplit {
    public TicketUnevenSplit(EventTicket eventTicket) {
        super(eventTicket);
    }


    /**
     * initial balances when the ticket is first made
     * @param debts
     */
    public void setInitialBalances(HashMap<User, Double> debts) {
        debtors = new HashSet<>();
        if(balancesInitialized) {
            return;
        }

        // update balance for payer
        double payerBalance = eventTicket.getTotal();
        if (debts.get(whoPaid()) != null) {
            payerBalance -= debts.get(whoPaid());
        }
        debts.put(whoPaid(), payerBalance);

        // make debts negative numbers
        for (User key : debts.keySet()) {
            if (key != whoPaid()) {
                debts.replace(key, -debts.get(key));
                debtors.add(key);
            }
        }

        // add other debtors
        this.balances = debts;
        balancesInitialized = true;

    }

    @Override
    public TypeSplit getSplitType() {
        return TypeSplit.UNEVEN_SPLIT;
    }

    @Override
    public String toString() {
        return "Uneven Split " + super.toString();
    }
}
