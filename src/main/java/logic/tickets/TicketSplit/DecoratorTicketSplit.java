package logic.tickets.TicketSplit;

import logic.tickets.Ticket;
import logic.tickets.TicketInfo;
import logic.tickets.TicketEvents.TypeEvents;

import java.util.HashMap;
import java.util.Set;

public abstract class DecoratorTicketSplit implements Ticket {
    protected TicketInfo eventTicket;

    /**
     * balance of debtors in ticket.
     * negative balance means debt
     */
    protected HashMap<Integer, Double> balances;

    /**
     * list of people with debt to payer
     */
    protected Set<Integer> debtors;
    protected boolean balancesInitialized = false;

    public DecoratorTicketSplit (TicketInfo eventTicket) {
        this.eventTicket = eventTicket;
        balances = new HashMap<>();
        balances.put(eventTicket.getWhoPaid(), eventTicket.getTotal());
    }


    public TicketInfo getEventTicket() {
        return eventTicket;
    }

    // ToDo: add java doc or remove

    /**
     * @return
     */
    public Set<Integer> getDebtors() {
        return debtors;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return eventTicket.getName();
    }

    /**
     * @return
     */
    @Override
    public double getTotal() {
        return eventTicket.getTotal();
    }

    /**
     * @return
     */
    @Override
    public int getWhoPaid() {
        return eventTicket.getWhoPaid();
    }

    /**
     * @return
     */

    public HashMap<Integer, Double> getBalances() {
        System.out.println(this.getClass() + ", balance: " + balances);
        System.out.println(this.getClass() + ", toString: " + toString());
        return balances;
    }

    /**
     * @return
     */
    @Override
    public TypeEvents getEventType() {
        return eventTicket.getEventType();
    }

    @Override
    public String toString() {
        return getEventType() + " TicketInfo{" +
                "name='" + getName() + '\'' +
                ", total=" + getTotal() +
                ", paid=" + getWhoPaid() +
                ", balances=" + balances +
                '}';
    }

    @Override
    public void addPayment(int user, double payment) {
        double userCurrentBalance = balances.get(user);
        balances.replace(user, userCurrentBalance + payment);

        double payerCurrentBalance = balances.get(getWhoPaid());
        balances.replace(getWhoPaid(), payerCurrentBalance - payment);

    }

    @Override
    public boolean isEven() {
        for (double debt : balances.values()) {
            if (Math.round(debt) != 0.00) {
                return false;
            }
        }
        return true;
    }
}
