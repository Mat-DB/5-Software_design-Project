package logic.controllers;

import logic.database.DatabaseGroups;
import logic.groups.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ControllerGroups {
    private static ControllerGroups controller;
    private final DatabaseGroups groupDB;
    private final ControllerTickets controllerTickets;

    private ControllerGroups() {
        groupDB = DatabaseGroups.getGroupDatabase();
        controllerTickets = ControllerTickets.getTicketController();
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

    public Group getGroup(int groupHash) {
        return groupDB.getGroup(groupHash);
    }

    public Set<Integer> getGroupMembers(String groupName) {
        return this.getGroup(groupName).getParticipants();
    }

    /**
     * If all the debts are evened this method can remove all tickets and remove them from the group.
     * @param groupHash the groupHash where all the debts are evened
     */
    public void removeTicketsInGroup(int groupHash) {
        Group group = groupDB.getGroup(groupHash);
        Set<Integer> ticketsInGroup = group.getTickets();
        for (int ticketHash : ticketsInGroup) {
            controllerTickets.removeTicket(ticketHash);
        }
        group.removeAllTickets();
        group.setGroupBalances(new HashMap<>());
    }

    /**
     * Try to remove a group.
     * @param groupHash hash of the group you want to delete
     * @return 0 if no tickets are linked to the group else -1
     */
    public int removeGroup(int groupHash) {
        return removeGroupLogic(groupHash);
    }

    public int removeGroup(String groupName) {
        return removeGroupLogic(groupDB.getGroupHash(groupName));
    }

    private int removeGroupLogic(int groupHash) {
        Group group = groupDB.getGroup(groupHash);
        if (!group.getTickets().isEmpty()) {
            return -1;
        }
        else {
            groupDB.removeGroup(groupHash);
            return 0;
        }
    }


    /**
     * Force to remove a group and the tickets linked to this group.
     * @param groupHash hash of the group you want to delete including tickets linked to this group
     * @return 0 if successful else -1
     */
    public int forceRemoveGroupAndTickets(int groupHash) {
        removeTicketsInGroup(groupHash);
        return removeGroup(groupHash);
    }

    /**
     * Check if a user is part of a group
     * @param userHash user to check
     * @return groupHash(es), empty if user is part of no group
     */
    public Set<Integer> findUserInGroup(int userHash) {
        Set<Integer> groupSet = new HashSet<>();
        for (Group group : groupDB.getGroups()) {
            if (group.getParticipants().contains(userHash)) {
                groupSet.add(groupDB.getGroupHash(group.getName()));
            }
        }
        return groupSet;
    }

    public Set<Integer> findTicketInGroup(int ticketHash) {
        Set<Integer> groupSet = new HashSet<>();
        for (Group group : groupDB.getGroups()) {
            if (group.getTickets().contains(ticketHash)) {
                groupSet.add(groupDB.getGroupHash(group.getName()));
            }
        }
        return groupSet;
    }

    public int getGroupHash(String groupName) {
        return groupDB.getGroupHash(groupName);
    }
}
