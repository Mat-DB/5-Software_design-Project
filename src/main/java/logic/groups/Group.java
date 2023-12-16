package logic.groups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Group {
    String name;

    Set<Integer> participants = new HashSet<>();

    Set<Integer> tickets = new HashSet<>();

    HashMap<Integer, Double> groupBalances = new HashMap<>();
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


    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }
}
