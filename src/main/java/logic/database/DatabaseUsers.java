package logic.database;

import logic.users.User;

import java.util.HashMap;

public class DatabaseUsers extends Database<User> {
    private DatabaseUsers() {
        super();
    }
    private static DatabaseUsers instance;

    public static DatabaseUsers getUserDatabase() {
        if (instance == null) {
            instance = new DatabaseUsers();
        }
        return instance;
    }
    public void addUser(User user){
        int id = (user.getName()+user.getID()).hashCode();
        addEntry(id, user);
    }

    public User getUser(String fullUserName) {
        return getEntry(getUserHash(fullUserName, 1));
    }

    public HashMap<String, Integer> getUsers() {
        HashMap<String, Integer> userNames = new HashMap<>();
        for (int key : db.keySet()) {
            User user = getEntry(key);
            userNames.put(user.getName(), user.getID());
        }
        return userNames;
    }

    public void removeUser(User user) {
        int id = getUserHash(user.getName(), user.getID());
        removeEntry(id);
    }

    public int userNameOccurrences(String fullUserName, int id){
        int hash = getUserHash(fullUserName, id);
        if (getEntry(hash) != null){
            id = userNameOccurrences(fullUserName, id+1);
        }
        return id;
    }

    private int getUserHash(String fullName, int id) {
        return (fullName+id).hashCode();
    }
}
