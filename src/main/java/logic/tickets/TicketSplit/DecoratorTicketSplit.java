package logic.tickets.TicketSplit;

import logic.tickets.Ticket;
import logic.tickets.TicketInfo;
import logic.tickets.TicketEvents.TypeEvents;

import java.util.HashMap;
import java.util.Set;

/**
 * A decorator for an event ticket to make the ticket splittable.
 */
public abstract class DecoratorTicketSplit implements Ticket {
    protected TicketInfo eventTicket;

    /**
     * Balance of debtors in ticket.
     * Negative balance means debt.
     */
    protected HashMap<Integer, Double> balances;

    /**
     * List of people with debt to payer.
     */
    protected Set<Integer> debtors;
    protected boolean balancesInitialized = false;

    /**
     * Constructor for the decorator.
     * @param eventTicket event ticket to decorate as splittable ticket.
     */
    public DecoratorTicketSplit (TicketInfo eventTicket) {
        this.eventTicket = eventTicket;
        balances = new HashMap<>();
        balances.put(eventTicket.getWhoPaid(), eventTicket.getTotal());
    }


    /**
     * Gets event ticket.
     *
     * @return the event ticket
     */
    public TicketInfo getEventTicket() {
        return eventTicket;
    }

    /**
     * Returns the debtors.
     * @return debtors
     */
    public Set<Integer> getDebtors() {
        return debtors;
    }

    /**
     * Returns the ticket name.
     * @return name
     */
    @Override
    public String getName() {
        return eventTicket.getName();
    }

    /**
     * Returns the ticket total.
     * @return total
     */
    @Override
    public double getTotal() {
        return eventTicket.getTotal();
    }

    /**
     * Returns the ID of the person who paid the ticket. (Ask the user controller who this is.)
     * @return payer
     */
    @Override
    public int getWhoPaid() {
        return eventTicket.getWhoPaid();
    }

    /**
     * Returns the balance of the ticket, who needs to pay who and how much.
     * @return balance
     */
    public HashMap<Integer, Double> getBalances() {
        System.out.println(this.getClass() + ", balance: " + balances);
        System.out.println(this.getClass() + ", toString: " + toString());
        return balances;
    }

    /**
     * Returns the type of event.
     * @return event type
     */
    @Override
    public TypeEvents getEventType() {
        return eventTicket.getEventType();
    }

    /**
     * Overwritten the toString implementation.
     * @return string
     */
    @Override
    public String toString() {
        return getEventType() + " TicketInfo{" +
                "name='" + getName() + '\'' +
                ", total=" + getTotal() +
                ", paid=" + getWhoPaid() +
                ", balances=" + balances +
                '}';
    }

    /**
     * Add an extra payment if one is forgotten.
     * @param user    the user
     * @param payment amount
     */
    @Override
    public void addPayment(int user, double payment) {
        double userCurrentBalance = balances.get(user);
        balances.replace(user, userCurrentBalance + payment);

        double payerCurrentBalance = balances.get(getWhoPaid());
        balances.replace(getWhoPaid(), payerCurrentBalance - payment);

    }

    /**
     * The isEven function checks if the balances of all users in the group are even.
     * @return True if all the balances are 0
     */
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
