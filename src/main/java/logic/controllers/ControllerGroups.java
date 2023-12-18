package logic.controllers;

import logic.database.DatabaseGroups;
import logic.groups.Group;

import java.util.ArrayList;
import java.util.Set;

public class ControllerGroups {
    private static ControllerGroups controller;
    private DatabaseGroups groupDB;

    private ControllerGroups() {
        groupDB = DatabaseGroups.getGroupDatabase();
    }

    public static ControllerGroups getGroupController() {
        if (controller == null) {
            controller = new ControllerGroups();
        }
        return controller;
    }

    /**
     * Create a new group.
     * @param groupName
     * @return hash of the group, key in the database. -1 if failed.
     */
    public int createGroup(String groupName, Set<Integer> users) {
        if (groupDB.getGroup(groupName) == null) {
            Group group = new Group(groupName);
            for (int user : users) {
                group.addParticipant(user);
            }
            groupDB.addGroup(group);
            return groupDB.getGroupHash(groupName);
        }
        return -1;
    }

    public void removeGroup(String groupName) {
        groupDB.removeGroup(groupDB.getGroupHash(groupName));
    }

    public ArrayList<Group> getGroups() {
        return groupDB.getGroups();
    }

    public boolean doesGroupExist(String groupName) {
        if (groupDB.getGroup(groupName) == null) {
            return false;
        }
        return true;
    }

    public Group getGroup(String groupName) {
        return groupDB.getGroup(groupName);
    }

    public Set<Integer> getGroupMembers(String groupName) {
        return this.getGroup(groupName).getParticipants();
    }
}
