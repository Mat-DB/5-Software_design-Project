package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.*;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TypeSplit;
import logic.users.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

/**
 * 1. Click create ticket button
 * 2. Select group
 * 3. Select owner, set amount and even or uneven split
 * 4. Select debtors and if uneven set amounts
 * 5. Confirm if everything is correct
 */
public class PanelTickets extends JPanel {
    private TicketPanelStates state;
    private final PanelTicketList panelTicketList;

    // Sub panels to create a new ticket
    private final PanelTicketCreateGroup panelGetGroup;
    private final PanelTicketCreateOwner panelGetOwnerInfo;
    private final PanelTicketCreateDebtors panelGetDebtors;
    private final PanelTicketCreateConfirm panelConfirmTicket;

    // New ticket info
    private int currentTicket = 0;
    private Group selectedGroup;
    private User owner;
    private double amount;
    private String ticketName;
    private TypeSplit splitType;
    private TypeEvents eventType;

    // Buttons
    private final JButton createButton;
    private final JButton viewButton;

    // Controllers
    private final ControllerTickets ticketsController;
    private final ControllerUsers usersController;

    public PanelTickets(ViewFrame frame) {
        state = TicketPanelStates.START;
        ticketsController = ControllerTickets.getTicketController();
        usersController = ControllerUsers.getUserController();
        panelTicketList = new PanelTicketList(frame);
        panelGetGroup = new PanelTicketCreateGroup(frame,this);
        panelGetOwnerInfo = new PanelTicketCreateOwner(this);
        panelGetDebtors = new PanelTicketCreateDebtors(this);
        panelConfirmTicket = new PanelTicketCreateConfirm(this);

        createButton = new JButton("Create new ticket");
        viewButton = new JButton("View a ticket");

        changeUI();
        createListeners();
    }

    private void createListeners() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = TicketPanelStates.GROUP;
                changeUI();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = TicketPanelStates.VIEW;
                changeUI();
            }
        });
    }

    private void changeUI() {
        switch (state) {
            case START -> {
                this.removeAll();
                this.updateUI();
                this.add(panelTicketList);
                this.add(createButton);
                this.add(viewButton);
            }
            case VIEW -> {
                System.out.println("VIEW mode");
            }
            case GROUP -> {
                this.removeAll();
                this.updateUI();
                this.add(panelTicketList);
                this.add(panelGetGroup);
            }
            case OWNER -> {
                this.removeAll();
                this.updateUI();
                this.add(panelTicketList);
                this.add(panelGetOwnerInfo);
            }
            case DEBTORS -> {
                this.removeAll();
                this.updateUI();
                this.add(panelTicketList);
                this.add(panelGetDebtors);
                panelGetDebtors.init();
            }
            case CONFIRM -> {
                this.removeAll();
                this.updateUI();
                this.add(panelTicketList);
                this.add(panelConfirmTicket);
                panelConfirmTicket.init();
            }
        }
    }

    public void setGroupSelectedAndName(Group group, String ticketName) {
        this.selectedGroup = group;
        this.ticketName = ticketName;
        panelGetOwnerInfo.setGroup(group);

        state = TicketPanelStates.OWNER;
        changeUI();
    }

    public void setOwnerInfo(User owner, Double amount, TypeSplit splitType, TypeEvents eventType) {
        this.owner = owner;
        this.amount = amount;
        this.splitType = splitType;
        this.eventType = eventType;

        state = TicketPanelStates.DEBTORS;
        changeUI();
    }

    public void setDebtorsUnevenSplit(HashMap<Integer, Double> debtorsIn) {
        currentTicket = ticketsController.createUnevenSplitTicket(ticketName, amount, usersController.getUserHash(owner), eventType, debtorsIn);
        state = TicketPanelStates.CONFIRM;
        changeUI();
    }

    public void setDebtorsEvenSplit(Set<Integer> debtorsIn) {
        currentTicket = ticketsController.createEvenSplitTicket(ticketName, amount, usersController.getUserHash(owner), eventType, debtorsIn);
        state = TicketPanelStates.CONFIRM;
        changeUI();
    }

    public void setStartState() {
        ticketsController.removeTicket(currentTicket);
        currentTicket = 0;
        state = TicketPanelStates.START;
        changeUI();
    }

    public void confirmTicket() {
        state = TicketPanelStates.START;
        changeUI();
    }

    public User getOwner() {
        return owner;
    }

    public TypeSplit getSplitType() {
        return splitType;
    }

    public Group getGroup() {
        return selectedGroup;
    }

    public double getAmount() {
        return amount;
    }

    public String getTicketName() {
        return ticketName;
    }

    public int getCurrentTicket() {
        return currentTicket;
    }
}
