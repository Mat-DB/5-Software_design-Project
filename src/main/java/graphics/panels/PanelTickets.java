package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.*;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TicketUnevenSplit;
import logic.tickets.TicketSplit.TypeSplit;
import logic.users.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * 1. Click create ticket button
 * 2. Select group
 * 3. Select owner, set amount and even or uneven split
 * 4. Select debtors and if uneven set amounts
 * 5. Confirm if everything is correct
 */
public class PanelTickets extends JPanel {
    private TicketPanelStates state;
    private PanelTicketList panelTicketList;

    // Sub panels to create a new ticket
    private PanelTicketCreateGroup panelGetGroup;
    private PanelTicketCreateOwner panelGetOwnerInfo;
    private PanelTicketCreateDebtors panelGetDebtors;
    private PanelTicketCreateConfirm panelConfirmTicket;

    // New ticket info
    private Group selectedGroup;
    private User selectedOwner;
    private double amount;
    private String ticketName;
    private TypeSplit splitType;
    private TypeEvents eventType;
    private HashMap<Integer, Double> debtors;

    // Buttons
    private JButton createButton;
    private JButton viewButon;

    // Controllers
    private ControllerTickets ticketsController;
    private ControllerUsers usersController;

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
        viewButon = new JButton("View a ticket");

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

        viewButon.addActionListener(new ActionListener() {
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
                this.add(viewButon);
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

    public void setProupSelectedAndName(Group group, String ticketName) {
        this.selectedGroup = group;
        this.ticketName = ticketName;
        panelGetOwnerInfo.setGroup(group);

        state = TicketPanelStates.OWNER;
        changeUI();
    }

    public void setOwnerInfo(User owner, Double amount, TypeSplit splitType, TypeEvents eventType) {
        this.selectedOwner = owner;
        this.amount = amount;
        this.splitType = splitType;
        this.eventType = eventType;

        state = TicketPanelStates.DEBTORS;
        changeUI();
    }

    public void setDebtors(HashMap<Integer, Double> debtorsIn) {
        debtors = debtorsIn;
        if (splitType == TypeSplit.EVEN_SPLIT) {
            double amountPP = amount/(debtors.size()+1);
            for (int debtor : debtors.keySet()) {
                debtors.put(debtor, amountPP);
            }
        }
        state = TicketPanelStates.CONFIRM;
        changeUI();
    }

    public void setStarteState() {
        state = TicketPanelStates.START;
        changeUI();
    }

    public void confirmTicket() {
        int ticketHash = ticketsController.createTicket(ticketName, amount, usersController.getUserHash(selectedOwner), eventType, splitType);
        if (splitType == TypeSplit.EVEN_SPLIT) {
            TicketEvenSplit ticket = (TicketEvenSplit) ticketsController.getTicket(ticketHash);
            ticket.setInitialBalances(debtors.keySet());
        } else {
            TicketUnevenSplit ticket = (TicketUnevenSplit) ticketsController.getTicket(ticketHash);
            ticket.setInitialBalances(debtors);
        }
        state = TicketPanelStates.START;
        changeUI();
    }

    public User getOwner() {
        return selectedOwner;
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

    public HashMap<Integer, Double> getDebtors() {
        return debtors;
    }
}
