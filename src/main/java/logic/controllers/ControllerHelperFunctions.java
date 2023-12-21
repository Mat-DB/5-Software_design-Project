package logic.controllers;

import logic.tickets.TicketInfo;
import logic.users.User;

import java.util.HashMap;
import java.util.Set;

/**
 * ToDo: Is this class still needed?
 * This class only has static methods.
 */
public class ControllerHelperFunctions {

    public static HashMap<Integer, User> convertHashesToUsers(Set<Integer> hashSet, ControllerUsers controllerUsers) {
        HashMap<Integer, User> userSet = new HashMap<>();
        for(int key : hashSet) {
            userSet.put(key, controllerUsers.getUser(key));
        }
        return userSet;
    }

    public static HashMap<Integer, TicketInfo> convertHashesToTickets(Set<Integer> hashSet, ControllerTickets controllerTicket) {
        HashMap<Integer, TicketInfo> ticketSet = new HashMap<>();
        for(int key : hashSet) {
            ticketSet.put(key, controllerTicket.getTicket(key));
        }
        return ticketSet;
    }

}
