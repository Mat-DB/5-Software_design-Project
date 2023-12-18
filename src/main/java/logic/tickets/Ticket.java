package logic.tickets;

import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

public interface Ticket extends TicketInfo {
    HashMap<Integer, Double> getBalances();

    Set<Integer> getDebtors();

    void addPayment(int user, double payment);

    boolean isEven();

    TypeSplit getSplitType();
}
