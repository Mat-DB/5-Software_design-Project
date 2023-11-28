package logic;

import logic.entities.User;
import logic.entities.tickets.Ticket;
import logic.entities.tickets.TicketEvenSplit;
import logic.entities.tickets.TicketUnevenSplit;

import java.util.ArrayList;
import java.util.HashMap;

public class testFunctions {

    public static void testTicketSplit() {
        ArrayList<User> debtors = new ArrayList<>();
        User matthias = new User("Matthias", "De Beukelaer", 2);
        User gones = new User("Gones", "Anseel", 1);
        debtors.add(matthias);

        Ticket ticket = new TicketEvenSplit("test even split", 5, gones, debtors);

        System.out.println(ticket);

        HashMap<User, Double> debts = new HashMap<>();
        debts.put(matthias, 5.0);

        ticket = new TicketUnevenSplit("test uneven split", 5, gones, debts);

        System.out.println(ticket);
    }

    public static void testTicketFactory() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        User gones = new User("Gones", "Anseel", 1);


    }
}
