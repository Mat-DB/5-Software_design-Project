package logic.controllers;

import logic.tickets.TicketInfo;
import logic.users.User;

import java.util.HashMap;
import java.util.Set;

/**
 * This class only has static methods to convert Sets to HashMaps.
 */
public class ControllerHelperFunctions {

    /**
     * Convert a Set of user hash ID's (Integers) to a HashMap of ID's and Users.
     * @param hashSet Set of user hash ID's
     * @return HashMap with the Integer as key and the corresponding User as value
     */
    public static HashMap<Integer, User> convertHashesToUsers(Set<Integer> hashSet) {
        ControllerUsers controllerUsers = ControllerUsers.getUserController();
        HashMap<Integer, User> userSet = new HashMap<>();
        for(int key : hashSet) {
            userSet.put(key, controllerUsers.getUser(key));
        }
        return userSet;
    }

    /**
     * Convert a set of ticket hash ID's (Integers) to a HashMap of ID's and Tickets.
     * @param hashSet Set of ticket hash ID's
     * @return HashMap with the Integer as key and the corresponding Ticket as value
     */
    public static HashMap<Integer, TicketInfo> convertHashesToTickets(Set<Integer> hashSet) {
        ControllerTickets controllerTicket = ControllerTickets.getTicketController();
        HashMap<Integer, TicketInfo> ticketSet = new HashMap<>();
        for(int key : hashSet) {
            ticketSet.put(key, controllerTicket.getTicket(key));
        }
        return ticketSet;
    }
}
