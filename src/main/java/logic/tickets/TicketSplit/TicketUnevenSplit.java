package logic.tickets.TicketSplit;

import logic.tickets.TicketInfo;

import java.util.HashMap;
import java.util.HashSet;

public class TicketUnevenSplit extends DecoratorTicketSplit {
    public TicketUnevenSplit(TicketInfo eventTicket) {
        super(eventTicket);
    }


    /**
     * initial balances when the ticket is first made
     * @param debts
     */
    public void setInitialBalances(HashMap<Integer, Double> debts) {
        debtors = new HashSet<>();
        if(balancesInitialized) {
            return;
        }

        // update balance for payer
        double payerBalance = eventTicket.getTotal();
        if (debts.get(getWhoPaid()) != null) {
            payerBalance -= debts.get(getWhoPaid());
        }
        debts.put(getWhoPaid(), payerBalance);

        // make debts negative numbers
        for (int key : debts.keySet()) {
            if (key != getWhoPaid()) {
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
