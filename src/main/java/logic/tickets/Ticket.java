package logic.tickets;

import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

/**
 * The interface Ticket.
 */
public interface Ticket extends TicketInfo {
    /**
     * Get the balances.
     * @return the balances
     */
    HashMap<Integer, Double> getBalances();

    /**
     * Get the debtors.
     * @return the debtors
     */
    Set<Integer> getDebtors();

    /**
     * Add a payment.
     * @param user    the user
     * @param payment the payment
     */
    void addPayment(int user, double payment);

    /**
     * The isEven function checks if the balances of all users in the group are even.
     * @return the boolean
     */
    boolean isEven();

    /**
     * Get the split type.
     * @return the split type
     */
    TypeSplit getSplitType();
}
