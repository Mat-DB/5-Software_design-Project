package logic.controllers;

import logic.database.DatabaseUsers;
import logic.users.User;

import java.util.HashMap;

/**
* This is the controller of the user.
* Properties:
* - Singleton
 * - Manges the user database
*/
public class ControllerUsers {
    private static ControllerUsers controller;
    private DatabaseUsers userDB;

    private ControllerUsers() {
        userDB = DatabaseUsers.getUserDatabase();
    }

    public static ControllerUsers getUserController() {
        if (controller == null) {
            controller = new ControllerUsers();
        }
        return controller;
    }

    public User createUser(String firstName, String lastname) {
        int existingID = userDB.userNameOccurrences(firstName+lastname, 1);
        User newUser = new User(firstName, lastname, existingID+1);
        userDB.addUser(newUser);
        return newUser;
    }

    public void removeUser(User user) {
        userDB.removeUser(user);
    }


    public HashMap<String, Integer> getUsers() {
        return userDB.getUsers();
    }
}
