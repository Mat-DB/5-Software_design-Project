package logic.groups;

import logic.users.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {
    String name;

    Set<User> participants = new HashSet<>();
    public Group(String name) {
        this.name = name;
    }

    public void addParticipant(User newParticipant) {
        participants.add(newParticipant);
    }

    public User getParticipant(String firstName, String lastName) {
        for (User user : participants) {
            if (Objects.equals(user.getName(), firstName + " " + lastName))
                return user;
        }
        return null;
    }
}
