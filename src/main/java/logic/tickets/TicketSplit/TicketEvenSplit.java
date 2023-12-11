package logic.tickets.TicketSplit;

import logic.tickets.EventTicket;

import java.util.Set;

public class TicketEvenSplit extends DecoratorTicketSplit {
    public TicketEvenSplit(EventTicket eventTicket) {
        super(eventTicket);

    }

    /**
     * initial balances when ticket is first made.
     * @param debtors list of people who owe money to the payer
     */
    public void setInitialBalances(Set<Integer> debtors) {
        if(balancesInitialized) {
            return;
        }
        double ticketTotal = eventTicket.getTotal();

        this.debtors = debtors;

        // divide price by debtors and person who paid
        double debt = ticketTotal / (debtors.size() + 1);

        // initial balance of payer is total of ticket minus his own debt
        balances.replace(getWhoPaid(), ticketTotal - debt);

        // add other debtors to balances
        for (int debtor : debtors) {
            balances.put(debtor, -debt);
        }
        balancesInitialized = true;
    }

    /**
     * Adding a debtor when the balances are already initialized.
     * @param newDebtor new user to add to ticket.
     */
    public void addDebtor(int newDebtor) {
        debtors.add(newDebtor);
        double ticketTotal = eventTicket.getTotal();

        // divide price by debtors and person who paid
        double debt = ticketTotal / (debtors.size() + 1);

        // initial balance of payer is total of ticket minus his own debt
        balances.put(getWhoPaid(), ticketTotal - debt);

        // add other debtors to balances
        for (int debtor : debtors) {
            balances.put(debtor, -debt);
        }
    }

    @Override
    public TypeSplit getSplitType() {
        return TypeSplit.EVEN_SPLIT;
    }

    @Override
    public String toString() {
        return "Even split " + super.toString();
    }
}
