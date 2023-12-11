package logic.controllers;

import logic.users.User;

import java.util.HashMap;
import java.util.Set;

/**
 * This class only has static methodes.
 */
public class ControllerHelperFunctions {

    public static HashMap<Integer, User> convertHashToUsers(Set<Integer> hashSet, ControllerUsers controllerUsers) {
        HashMap<Integer, User> userSet = new HashMap<>();
        for(int key : hashSet) {
            userSet.put(key, controllerUsers.getUser(key));
        }
        return userSet;
    }

}
