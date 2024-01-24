package logic.users;

public class User {
    private final String firstName;
    private final String lastName;
    private final int id;

    public User(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getNameForDB() {
        return firstName + lastName;
    }

    public String getName() {
        if (id == 1) {
            return firstName + " " + lastName;
        } else {
            return firstName + " " + lastName + " " + id;
        }
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
