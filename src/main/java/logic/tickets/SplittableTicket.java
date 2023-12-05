package logic.tickets;

import logic.users.User;
import logic.tickets.TicketSplit.TypeSplit;

import java.util.HashMap;
import java.util.Set;

public interface SplittableTicket extends EventTicket {
    HashMap<User, Double> getBalances();

    Set<User> getDebtors();

    void addPayment(User payer, double payment);

    boolean isEven();

    TypeSplit getSplitType();
}
