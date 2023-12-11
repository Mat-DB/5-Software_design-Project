package logic.tickets;

import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

public interface SplittableTicket extends EventTicket {
    HashMap<Integer, Double> getBalances();

    Set<Integer> getDebtors();

    void addPayment(int user, double payment);

    boolean isEven();

    TypeSplit getSplitType();
}
