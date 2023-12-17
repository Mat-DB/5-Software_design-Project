package graphics.panels.subPanels;

import graphics.panels.PanelTickets;
import logic.controllers.ControllerUsers;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public PanelTicketCreateConfirm(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;
    }

    public void init() {
        usersController = ControllerUsers.getUserController();
        overviewLabel = new JLabel("Overview of the ticket. Check if everything is correct.");
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
        for (int debtorHash : panelTickets.getDebtors().keySet()) {
            debtorLabelNum += 1;
            ArrayList<JLabel> debtorSet = new ArrayList<>();
            debtorSet.add(new JLabel(usersController.getUser(debtorHash).getName()));
            debtorSet.add(new JLabel(panelTickets.getDebtors().get(debtorHash) + " euro"));
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
        GridLayout gridLayout = new GridLayout(panelTickets.getDebtors().size()+1, 2);
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
