package logic.entities;

public class User {
    String firstName, lastName;
    int id;

    public User(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getID() {
        return id;
    }
}
