package logic.users;

import java.util.ArrayList;

public class User {
    String firstName, lastName;
    ArrayList<Integer> tickets;
    int id;

    public User(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFullName() {
        return firstName + lastName;
    }
    public String getName() {
        return firstName + " " + lastName;
    }

    public ArrayList<Integer> getTickets() {
        return tickets;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
