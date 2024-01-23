package logic.tickets;

import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

/**
 * The interface Ticket.
 */
public interface Ticket extends TicketInfo {
    /**
     * Gets balances.
     *
     * @return the balances
     */
    HashMap<Integer, Double> getBalances();

    /**
     * Gets debtors.
     *
     * @return the debtors
     */
    Set<Integer> getDebtors();

    /**
     * Add payment.
     *
     * @param user    the user
     * @param payment the payment
     */
    void addPayment(int user, double payment);

    /**
     * Is even boolean.
     *
     * @return the boolean
     */
    boolean isEven();

    /**
     * Gets split type.
     *
     * @return the split type
     */
    TypeSplit getSplitType();
}
