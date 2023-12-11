package logic.tickets.TicketSplit;

import logic.tickets.EventTicket;
import logic.tickets.SplittableTicket;
import logic.tickets.Ticket;
import logic.tickets.TicketEvents.TypeEvents;

import java.util.HashMap;
import java.util.Set;

public abstract class DecoratorTicketSplit implements SplittableTicket {
    protected EventTicket eventTicket;

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

    public DecoratorTicketSplit (EventTicket eventTicket) {
        this.eventTicket = eventTicket;
        balances = new HashMap<>();
        balances.put(eventTicket.whoPaid(), eventTicket.getTotal());
    }


    public Ticket getEventTicket() {
        return eventTicket;
    }

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
    public int whoPaid() {
        return eventTicket.whoPaid();
    }

    /**
     * @return
     */

    public HashMap<Integer, Double> getBalances() {
        return balances;
    }

    /**
     * @return
     */
    @Override
    public TypeEvents getEvent() {
        return eventTicket.getEvent();
    }

    @Override
    public String toString() {
        return getEvent() + " Ticket{" +
                "name='" + getName() + '\'' +
                ", total=" + getTotal() +
                ", paid=" + whoPaid() +
                ", balances=" + balances +
                '}';
    }

    @Override
    public void addPayment(int user, double payment) {
        double userCurrentBalance = balances.get(user);
        balances.replace(user, userCurrentBalance + payment);

        double payerCurrentBalance = balances.get(whoPaid());
        balances.replace(whoPaid(), payerCurrentBalance - payment);

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
