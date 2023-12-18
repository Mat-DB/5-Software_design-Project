package logic.groups;

import logic.controllers.ControllerTickets;
import logic.tickets.SplittableTicket;

import java.util.*;
import java.util.stream.Collectors;

public class GroupBalancer {

    public static void createBalance(Group group, ControllerTickets controllerTickets) {
        Set<Integer> participants = group.getParticipants();
        Set<Integer> tickets = group.getTickets();
        HashMap<Integer, Double> groupBalances = new HashMap<>();

        // get balance of ticket, add balance per user to the hashmap
        for (int key : tickets) {
            SplittableTicket actualTicket = (SplittableTicket) controllerTickets.getTicket(key);
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

        group.setGroupBalances(groupBalances);

    }

    public static void calculateWhoPaysWho(Group group, ControllerTickets controllerTickets) {
        Set<Integer> participants = group.getParticipants();
        Set<Integer> tickets = group.getTickets();
        HashMap<Integer, Double> groupBalances = group.getGroupBalances();
        ArrayList<Double> list = new ArrayList<>();

        List<Map.Entry<Integer, Double>> sortedBalances = sortedListByValueOfHashmap(groupBalances, true);
        System.out.println(sortedBalances);

        HashMap<Integer, Map.Entry<Integer, Double>> whoPaysWhoHowMuch = new HashMap<>();

        // position in sortBalances of person with the highest debt.
        int highestDebtorIndex = sortedBalances.size() - 1;
        // position in sortBalances of person with the highest credit.
        int highestCreditorIndex = 0;

        while(!sortedBalances.isEmpty()) {
            System.out.println(sortedBalances);

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

        group.setWhoPaysWhoHowMuch(whoPaysWhoHowMuch);

    }

    //https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    // function to sort hashmap by values
    public static List<Map.Entry<Integer, Double>> sortedListByValueOfHashmap(HashMap<Integer, Double> hm, boolean descending)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());
        if (descending) {
            Collections.reverse(list);
        }

        return list;
        // put data from sorted list to hashmap
//        HashMap<Integer, Double> temp = new LinkedHashMap<>();
//        for (Map.Entry<Integer, Double> aa : list) {
//            temp.put(aa.getKey(), aa.getValue());
//        }
//        return temp;
    }
}
