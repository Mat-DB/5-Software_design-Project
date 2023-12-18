package graphics.panels.subPanels;

import graphics.panels.PanelTickets;
import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.tickets.Ticket;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PanelTicketCreateConfirm extends JPanel {
    private JLabel overviewLabel;
    private JLabel nameLabel;
    private JLabel ownerLabel;
    private JLabel totalAmountLabel;
    private HashMap<Integer, ArrayList<JLabel>> debtorLabels;
    private JButton confirmButton;
    private JButton cancelButton;
    private PanelTickets panelTickets;
    private ControllerUsers usersController;
    private ControllerTickets ticketController;

    public PanelTicketCreateConfirm(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;
        usersController = ControllerUsers.getUserController();
        ticketController = ControllerTickets.getTicketController();
        overviewLabel = new JLabel("Overview of the ticket. Check if everything is correct.");
    }

    public void init() {
        this.removeAll();
        this.updateUI();

        nameLabel = new JLabel("Ticket - " + panelTickets.getTicketName());
        User owner = panelTickets.getOwner();
        String ownerName;
        ownerName = owner.getName();
        ownerLabel = new JLabel("Person who paid: " + ownerName);
        totalAmountLabel = new JLabel("Total amount: " + panelTickets.getAmount() + " euro");
        debtorLabels = new HashMap<>();
        int debtorLabelNum = 1;
        ArrayList<JLabel> debtorSetFirst = new ArrayList<>();
        debtorSetFirst.add(new JLabel("Debtor"));
        debtorSetFirst.add(new JLabel("Debt"));
        debtorLabels.put(debtorLabelNum, debtorSetFirst);

        Ticket ticket = ticketController.getTicket(panelTickets.getCurrentTicket());
        HashMap<Integer, Double> balance = ticket.getBalances();
        // https://java2blog.com/format-double-to-2-decimal-places-java/
        DecimalFormat df = new DecimalFormat("#.##");
        for (int debtorHash : ticket.getDebtors()) {
            debtorLabelNum += 1;
            ArrayList<JLabel> debtorSet = new ArrayList<>();
            debtorSet.add(new JLabel(usersController.getUser(debtorHash).getName()));
            debtorSet.add(new JLabel(df.format(balance.get(debtorHash)) + " euro"));
            debtorLabels.put(debtorLabelNum, debtorSet);
        }
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");

        // Put everything on the panel
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.add(overviewLabel);
        this.add(nameLabel);
        this.add(ownerLabel);
        this.add(totalAmountLabel);

        createListeners();

        JPanel debtorsPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(ticket.getDebtors().size()+1, 2);
        debtorsPanel.setLayout(gridLayout);
        for (int i : debtorLabels.keySet()) {
            for (JLabel label : debtorLabels.get(i)) {
                debtorsPanel.add(label);
            }
        }

        this.add(debtorsPanel);

        JPanel buttonPanel = new JPanel();
        boxLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxLayout);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel);
    }

    private void createListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearComponents();
                panelTickets.confirmTicket();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearComponents();
                panelTickets.setStartState();
            }
        });
    }

    private void clearComponents() {
        debtorLabels.clear();
    }
}
