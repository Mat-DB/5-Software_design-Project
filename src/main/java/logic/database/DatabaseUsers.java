package logic.database;

import logic.entities.User;

public class DatabaseUsers extends Database<User> {
    private static DatabaseUsers userDB;
    private DatabaseUsers() {}

    public static DatabaseUsers getUserDatabase() {
        if (userDB == null) {
            userDB = new DatabaseUsers();
        }
        return userDB;
    }
    public void addTicket(User user){
        int id = (user.getName()+user.getID()).hashCode();
        addEntry(id, user);
    }

    public int userNameOccurences(String fullName, int id){
        int hash = (fullName+id).hashCode();
        if (db.get(hash) != null){
            userNameOccurences(fullName, id+1);
        }
        return id;
    }
}
