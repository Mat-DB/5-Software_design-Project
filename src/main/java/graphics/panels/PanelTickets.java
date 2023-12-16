package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelCreateTicketDebtors;
import graphics.panels.subPanels.PanelCreateTicketGroup;
import graphics.panels.subPanels.PanelCreateTicketOwner;
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
    private PanelCreateTicketGroup panelGetGroup;
    private PanelCreateTicketOwner panelGetOwnerInfo;
    private PanelCreateTicketDebtors panelGetDebtors;

    // New ticket info
    private Group selectedGroup;
    private User selectedOwner;
    private double amount;
    private String ticketName;
    private TypeSplit splitType;
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
        panelGetGroup = new PanelCreateTicketGroup(frame,this);
        panelGetOwnerInfo = new PanelCreateTicketOwner(this);
        panelGetDebtors = new PanelCreateTicketDebtors(this);

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
                this.add(createButton);
                this.add(viewButon);
            }
            case VIEW -> {
                System.out.println("VIEW mode");
            }
            case GROUP -> {
                this.removeAll();
                this.updateUI();
                this.add(panelGetGroup);
            }
            case OWNER -> {
                this.removeAll();
                this.updateUI();
                this.add(panelGetOwnerInfo);
            }
            case DEBTORS -> {
                this.removeAll();
                this.updateUI();
                this.add(panelGetDebtors);
                panelGetDebtors.init();
            }
            case CONFIRM -> {
                System.out.println("CONFIM mode");
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

    public void setOwnerInfo(User owner, Double amount, TypeSplit splitType) {
        this.selectedOwner = owner;
        this.amount = amount;
        this.splitType = splitType;

        state = TicketPanelStates.DEBTORS;
        changeUI();
    }

    public void setDebtors(HashMap<Integer, Double> debtorsIn) {
        debtors = debtorsIn;
        if (splitType == TypeSplit.EVEN_SPLIT) {
            double amountPP = amount/debtors.size();
            for (int debtor : debtors.keySet()) {
                debtors.put(debtor, amountPP);
            }
        }
        state = TicketPanelStates.CONFIRM;
    }

    public void setStarteState() {
        state = TicketPanelStates.START;
        changeUI();
    }

    public void confirmTicket() {
        int ticketHash = ticketsController.createTicket(ticketName, amount, usersController.getUserHash(selectedOwner), TypeEvents.CUSTOM, splitType);
        if (splitType == TypeSplit.EVEN_SPLIT) {
            TicketEvenSplit ticket = (TicketEvenSplit) ticketsController.getTicket(ticketHash);
            ticket.setInitialBalances(debtors.keySet());
        } else {
            TicketUnevenSplit ticket = (TicketUnevenSplit) ticketsController.getTicket(ticketHash);
            ticket.setInitialBalances(debtors);
        }
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
