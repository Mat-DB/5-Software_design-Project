package logic;


import logic.entities.tickets.Ticket;
import logic.entities.tickets.TicketEvenSplit;
import logic.entities.tickets.TicketUnevenSplit;
import logic.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MoneyTrackerApplication {
    public MoneyTrackerApplication() {
        init();
    }

    public void init() {

    }

    public void run() {
        /*\
         TODO : split run into state machine coupled with buttons/use-cases
                    - create group
                    - add people to group
                    - add ticket : price, person who paid, people who are in debt, how much each debts is, ...
                        - > 'update depts'
                    - update debts of group members
                    - someone pays
                        - > 'update debts'

         */
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
}
