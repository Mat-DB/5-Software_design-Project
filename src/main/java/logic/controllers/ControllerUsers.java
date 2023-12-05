package logic.controllers;

import logic.database.DatabaseUsers;
import logic.users.User;

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
        int existingID = userDB.userNameOccurences(firstName+lastname, 1);

        User newUser = new User(firstName, lastname, existingID+1);
        userDB.addEntry(newUser.getID(), newUser);

        return newUser;
    }
}
