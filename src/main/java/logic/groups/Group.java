package logic.groups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Group {
    private String name;

    private Set<Integer> participants = new HashSet<>();

    private Set<Integer> tickets = new HashSet<>();

    HashMap<Integer, Double> groupBalances = new HashMap<>();

    HashMap<Integer, Map.Entry<Integer, Double>> whoPaysWhoHowMuch = new HashMap<>();
    public Group(String name) {
        this.name = name;
    }

    public void addParticipant(int newParticipant) {
        participants.add(newParticipant);
    }

    public int getParticipant(int userHash) {
        //return participants.stream();
        return 0;
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

    public void addTicket(int hash) {
        tickets.add(hash);
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
    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }
}
