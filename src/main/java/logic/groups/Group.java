package logic.groups;

import logic.MoneyTrackerLogger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Group {
    private final Logger logger = MoneyTrackerLogger.getInstance().getLogger(this.getClass().getName());
    private final String name;
    private final Set<Integer> participants = new HashSet<>();
    private Set<Integer> tickets = new HashSet<>();
    HashMap<Integer, Double> groupBalances = new HashMap<>();

    /**
     * HashMap with as key a user (A) hash and
     * as value a Map.Entry where the first is the user to whom the user (A) needs to pay and how much.
     */
    HashMap<Integer, Map.Entry<Integer, Double>> whoPaysWhoHowMuch = new HashMap<>();

    public Group(String name) {
        this.name = name;
    }

    public void addParticipant(int newParticipant) {
        participants.add(newParticipant);
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getParticipants() {
        return participants;
    }

    public Set<Integer> getTickets() {
        return tickets;
    }

    public HashMap<Integer, Double> getGroupBalances() {
        return groupBalances;
    }

    public void addTicket(int ticketHash) {
        tickets.add(ticketHash);
    }

    public void removeTicket(int ticketHash) {
        tickets.remove(ticketHash);
    }

    public void removeAllTickets() {
        tickets = new HashSet<>();
    }

    public void setGroupBalances(HashMap<Integer, Double> balances) {
        this.groupBalances = balances;
    }

    public void setWhoPaysWhoHowMuch(HashMap<Integer, Map.Entry<Integer, Double>> whoPaysWhoHowMuch){
        this.whoPaysWhoHowMuch = whoPaysWhoHowMuch;
    }

    public HashMap<Integer, Map.Entry<Integer, Double>> getWhoPaysWhoHowMuch() {
        return whoPaysWhoHowMuch;
    }

    public HashMap<Integer, Map.Entry<Integer, Double>> updateAndGetWhoPaysWhoHowMuch() {
        GroupBalancer.createBalance(this);
        logger.finer("Balance created (normally) - group class");
        GroupBalancer.calculateWhoPaysWho(this);
        logger.finer("debts calculated (normally) - group class");
        return whoPaysWhoHowMuch;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }
}
