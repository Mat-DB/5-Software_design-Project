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

    public int addUser(User user){
        int hash = (user.getFullName()+user.getID()).hashCode();
        System.out.println(hash);
        addEntry(hash, user);
        return hash;
    }

    public User getUser(String fullUserName) {
        return getEntry(getUserHash(fullUserName, 1));
    }

    public User getUser(int userHash) {
        return getEntry(userHash);
    }

    public HashMap<String, Integer> getUsers() {
        HashMap<String, Integer> userNames = new HashMap<>();
        for (int key : db.keySet()) {
            User user = getEntry(key);
            userNames.put(user.getFullName(), user.getID());
        }
        return userNames;
    }

    public void removeUser(User user) {
        int id = getUserHash(user.getName(), user.getID());
        removeEntry(id);
    }

    public int userNameOccurrences(String fullUserName, int id){
        int newID = 1;
        int hash = getUserHash(fullUserName, id);
        System.out.println(hash);
//        System.out.println(getEntry(hash));
        if (getEntry(hash) != null){
            newID += userNameOccurrences(fullUserName, id+1);
        }
        return newID;
    }

    private int getUserHash(String fullName, int id) {
        return (fullName+id).hashCode();
    }
}
