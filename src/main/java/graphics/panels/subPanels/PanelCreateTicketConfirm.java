package graphics.panels.subPanels;

import graphics.panels.PanelTickets;
import logic.controllers.ControllerUsers;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

public class PanelCreateTicketConfirm extends JPanel {
    private JLabel overviewLabel;
    private JLabel nameLabel;
    private JLabel ownerLabel;
    private JLabel totalAmountLabel;
    private HashMap<JLabel, JLabel> debtorLabels;
    private JButton confirmButton;
    private JButton cancelButton;
    private PanelTickets panelTickets;
    private ControllerUsers usersController;

    public PanelCreateTicketConfirm(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;
    }

    public void init() {
        usersController = ControllerUsers.getUserController();
        overviewLabel = new JLabel("Overview of the ticket. Check if everything is correct.");
        nameLabel = new JLabel("Ticket - " + panelTickets.getTicketName());
        User owner = panelTickets.getOwner();
        String ownerName;
        if (owner.getID() == 1) {
            ownerName = owner.getName();
        } else {
            ownerName = owner.getName() + " " + owner.getID();
        }
        ownerLabel = new JLabel("Person who paid: " + ownerName);
        totalAmountLabel = new JLabel("Total amount: " + panelTickets.getAmount() + " euro");
        debtorLabels.put(new JLabel("Debtor"), new JLabel("Debt"));
        for (int debtorHash : panelTickets.getDebtors().keySet()) {
            String debtorName = usersController.getUser(debtorHash).getName();
            String debtorAmount = String.valueOf(panelTickets.getDebtors().get(debtorHash));
            debtorLabels.put(new JLabel(debtorName), new JLabel(debtorAmount));
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

        JPanel debtorsPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(panelTickets.getDebtors().size()+1, 2);
        debtorsPanel.setLayout(gridLayout);
        for (JLabel debtorName : debtorLabels.keySet()) {
            debtorsPanel.add(debtorName);
            debtorsPanel.add(debtorLabels.get(debtorName));
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
                panelTickets.confirmTicket();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTickets.setStarteState();
            }
        });
    }
}
