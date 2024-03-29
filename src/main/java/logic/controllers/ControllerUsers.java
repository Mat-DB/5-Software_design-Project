package logic.controllers;

import logic.MoneyTrackerLogger;
import logic.database.DatabaseUsers;
import logic.users.User;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

/**
* This is the controller of the user.
* Properties:
* - Singleton
 * - Manges the user database
*/
public class ControllerUsers {
    private final Logger logger = MoneyTrackerLogger.getInstance().getLogger(this.getClass().getName());
    private static ControllerUsers controller;
    private final DatabaseUsers userDB;
    private final ControllerGroups controllerGroups;

    private ControllerUsers() {
        userDB = DatabaseUsers.getUserDatabase();
        controllerGroups = ControllerGroups.getGroupController();
    }

    public static ControllerUsers getUserController() {
        if (controller == null) {
            controller = new ControllerUsers();
        }
        return controller;
    }

    /**
     * Function to add new user to User Database.
     * First searches for the first available ID for a user with (firstname, lastname).
     * Creates user with available ID and adds user to database.
     * Return key for user in database.
     * @param firstName first name of user to add
     * @param lastname last name of user to add
     * @return key for user in user database.
     */
    public int createUser(String firstName, String lastname) {
        int existingID = userDB.userNameOccurrences(firstName+lastname, 1);
        User newUser = new User(firstName, lastname, existingID);
        logger.finer(newUser.toString());
        return userDB.addUser(newUser);
    }

    /** Try to remove a user.
     * @param userHash hash ID of the user
     * @return 0 if successful else -1
     */
    public int removeUser(int userHash) {
        Set<Integer> groups = controllerGroups.findUserInGroup(userHash);
        if (groups.isEmpty()) {
            userDB.removeUser(userHash);
            return 0;
        }
        else {
            return -1;
        }
    }

    public HashMap<String, Integer> getUsers() {
        return userDB.getUsers();
    }

    public User getUser(int userHash) {
        return userDB.getUser(userHash);
    }

    public int getUserHash(User user) {
        return userDB.getUserHash(user.getNameForDB(), user.getID());
    }

    public int getUserHash(String nameAndID) {
        return userDB.getUserHash(nameAndID);
    }

    public boolean doesUserExist(String firstName, String lastName) {
        int hash = userDB.getUserHash(firstName+lastName,1);
        return userDB.getUser(hash) != null;
    }
}
