package logic;


import logic.Entities.Ticket;
import logic.Entities.TicketEvenSplit;
import logic.Entities.TicketUnevenSplit;
import logic.Entities.User;

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
        User matthias = new User("Matthias", "DeBeukelaer", 2);
        User gones = new User("Gones", "Anseel", 1);
        debtors.add(matthias);

        Ticket ticket = new TicketEvenSplit(5, gones, debtors);

        System.out.println(ticket);



        HashMap<User, Double> debts = new HashMap<>();
        debts.put(matthias, 5.0);

        ticket = new TicketUnevenSplit(5, new User("Gones", "Anseel", 1), debts);

        System.out.println(ticket);


    }
}
