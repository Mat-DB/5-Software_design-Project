package logic.database;

import logic.MoneyTrackerLogger;
import logic.users.User;

import java.util.HashMap;
import java.util.logging.Logger;

public class DatabaseUsers extends Database<User> {
    private final Logger logger = MoneyTrackerLogger.getInstance().getLogger(this.getClass().getName());
    private static DatabaseUsers instance;

    private DatabaseUsers() {
        super();
    }

    public static DatabaseUsers getUserDatabase() {
        if (instance == null) {
            instance = new DatabaseUsers();
        }
        return instance;
    }

    public int addUser(User user){
        int hash = getUserHash(user.getNameForDB(), user.getID());
        addEntry(hash, user);
        observable.firePropertyChange("new user", null, user);
        return hash;
    }

    public User getUser(int userHash) {
        return getEntry(userHash);
    }

    public HashMap<String, Integer> getUsers() {
        HashMap<String, Integer> userNames = new HashMap<>();
        for (int key : db.keySet()) {
            User user = getEntry(key);
            userNames.put(user.getNameForDB(), user.getID());
        }
        return userNames;
    }

    public void removeUser(int hash) {
        User user = getEntry(hash);
        observable.firePropertyChange("remove user", user, null);
        removeEntry(hash);
    }

    public int userNameOccurrences(String fullUserName, int id){
        int newID = 1;
        int hash = getUserHash(fullUserName, id);
        logger.finer(String.valueOf(hash));
        if (getEntry(hash) != null){
            newID += userNameOccurrences(fullUserName, id+1);
        }
        return newID;
    }

    public int getUserHash(String fullName, int id) {
        return getUserHash(fullName + " " + id);
    }

    public int getUserHash(String userNameAndID) {
        return userNameAndID.hashCode();
    }
}
