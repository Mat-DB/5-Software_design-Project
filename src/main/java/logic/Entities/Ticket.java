package logic.Entities;

import java.util.HashMap;
import java.util.Set;

public abstract class Ticket {
    double price;

    User paid;

    HashMap<User, Double> debts;

    public Ticket(double price, User paid) {
        this.price = price;
        this.paid = paid;
        debts = new HashMap<>();
    }

    public Set<User> getDebtors() {
        return debts.keySet();
    }

    public HashMap<User, Double> getDebtorsAndDebt() {
        return debts;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                ", paid=" + paid +
                ", debts=" + debts +
                '}';
    }
}

