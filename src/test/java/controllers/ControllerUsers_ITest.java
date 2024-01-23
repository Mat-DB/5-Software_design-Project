package controllers;

import logic.controllers.ControllerGroups;
import logic.controllers.ControllerUsers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ControllerUsers_ITest {
    public ControllerUsers_ITest() {
        // Initially left blank
    }

    /**
     * This test will check that -1 is returned if a user that you want to delete
     * is part of one or more groups.
     */
    @Test
    public void removeUserInGroup() {
        String groupName = "friends";
        ControllerUsers controllerUsers = ControllerUsers.getUserController();
        ControllerGroups controllerGroups = ControllerGroups.getGroupController();
        // Step 1, Add at least 2 users.
        int user1 = controllerUsers.createUser("User 1", "Test");
        int user2 = controllerUsers.createUser("test user 2", "last name");

        // Step 2, add them to a group
        Set<Integer> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        controllerGroups.createGroup(groupName, users);

        // Step 3, try to remove one of the users.
        int returnCode = controllerUsers.removeUser(user1);
        Assert.assertEquals("Testing correct return code when a user is part of a none empty group - should return -1",
                -1, returnCode);
    }

    /**
     * This test will check that 0 is returned if a user that you want to delete
     * is part of no group(s).
     */
    @Test
    public void removeUserNoGroup() {
        ControllerUsers controllerUsers = ControllerUsers.getUserController();
        // Step 1, Add at least 2 users.
        int user1 = controllerUsers.createUser("User 1", "Test");

        // Step 2, try to remove the user.
        int returnCode = controllerUsers.removeUser(user1);
        Assert.assertEquals("Testing correct return code when a user is part of an empty group - should return 0",
                0, returnCode);
    }
}
