package logic.database;

import logic.users.User;

public class DatabaseUsers extends Database<User> {
    private static DatabaseUsers instance;
    private DatabaseUsers() {}

    public static DatabaseUsers getUserDatabase() {
        if (instance == null) {
            instance = new DatabaseUsers();
        }
        return instance;
    }
    public void addTicket(User user){
        int id = (user.getName()+user.getID()).hashCode();
        addEntry(id, user);
    }

    public int userNameOccurences(String fullName, int id){
        int hash = (fullName+id).hashCode();
        if (db.get(hash) != null){
            id = userNameOccurences(fullName, id+1);
        }
        return id;
    }
}
