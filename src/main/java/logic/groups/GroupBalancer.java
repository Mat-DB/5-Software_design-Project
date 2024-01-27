package logic.groups;

import logic.controllers.ControllerTickets;
import logic.tickets.Ticket;

import java.util.*;
import java.util.logging.Logger;

public class GroupBalancer {

    public static HashMap<Integer, Double> createBalance(Group group) {
        ControllerTickets controllerTickets = ControllerTickets.getTicketController();
        Set<Integer> participants = group.getParticipants();
        Set<Integer> tickets = group.getTickets();
        HashMap<Integer, Double> groupBalances = new HashMap<>();

        // get balance of ticket, add balance per user to the hashmap
        for (int key : tickets) {
            Ticket actualTicket = controllerTickets.getTicket(key);
            HashMap<Integer, Double> balances = actualTicket.getBalances();
            for (int user : participants) {
                // no value in ticket : return
                if (balances.get(user) == null) {
                    continue;
                }
                // value in ticket, no value in groupBalance : add new <key,value>
                if (groupBalances.get(user) == null) {
                    groupBalances.put(user, balances.get(user));
                    continue;
                }
                // value in ticket, value in ticket : update ticket
                groupBalances.put(user, balances.get(user) + groupBalances.get(user));
            }
        }
        return groupBalances;
    }

    public static HashMap<Integer, Map.Entry<Integer, Double>> calculateWhoPaysWho(Group group) {
        Logger logger = Logger.getLogger("GroupBalancer.calculateWhoPaysWho");
        HashMap<Integer, Double> groupBalances = group.getGroupBalances();
        List<Map.Entry<Integer, Double>> sortedBalances = sortedListByValueOfHashmap(groupBalances, true);
        logger.finer(sortedBalances.toString());

        HashMap<Integer, Map.Entry<Integer, Double>> whoPaysWhoHowMuch = new HashMap<>();

        // position in sortBalances of person with the highest debt.
        int highestDebtorIndex = sortedBalances.size() - 1;
        // position in sortBalances of person with the highest credit.
        int highestCreditorIndex = 0;

        while(!sortedBalances.isEmpty()) {
            logger.finer(sortedBalances.toString());

            Map.Entry<Integer, Double> highestDebtorEntry = sortedBalances.get(highestDebtorIndex);
            Map.Entry<Integer, Double> highestCreditorEntry = sortedBalances.get(highestCreditorIndex);

            // new credit = credit + debt (debt is negative)
            double newCreditHighestCreditor = highestCreditorEntry.getValue() + highestDebtorEntry.getValue();
            highestCreditorEntry.setValue(newCreditHighestCreditor);
            sortedBalances.remove(highestCreditorIndex);
            sortedBalances.add(highestCreditorIndex, highestCreditorEntry);

            // add transaction to whoPaysWhoHowMuch
            whoPaysWhoHowMuch.put(highestDebtorEntry.getKey(), Map.entry(highestCreditorEntry.getKey(), (double) -Math.round(highestDebtorEntry.getValue() * 1000)/1000.00));

            // remove debtor
            sortedBalances.remove(highestDebtorIndex);

            // creditor at zero :  remove creditor
            if (newCreditHighestCreditor == 0) {
                sortedBalances.remove(highestCreditorIndex);
            }
            // creditor now debtor : sort list
            if (newCreditHighestCreditor < 0) {
                sortedBalances.sort(Map.Entry.comparingByValue());
                Collections.reverse(sortedBalances);
            }
            // update index
            highestDebtorIndex = sortedBalances.size() - 1;
        }
        return whoPaysWhoHowMuch;
    }

    //https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    // function to sort hashmap by values
    public static List<Map.Entry<Integer, Double>> sortedListByValueOfHashmap(HashMap<Integer, Double> hm, boolean descending) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());
        if (descending) {
            Collections.reverse(list);
        }
        return list;
    }
}
