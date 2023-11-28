package logic.entities.tickets;

import logic.entities.User;

import java.util.HashMap;
import java.util.Set;

public abstract class Ticket {
    String name;
    String description;
    double price;

    User paid;

    HashMap<User, Double> debts;

    public Ticket(String name, double price, User paid) {
        this.name = name;
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

    public String getName() {
        return name;
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

