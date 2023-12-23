package graphics.panels.subPanels;

import graphics.panels.PanelBalances;
import logic.controllers.ControllerGroups;
import logic.controllers.ControllerUsers;
import logic.groups.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PanelBalanceView extends JPanel {
    private Group group;
    private final JButton resolveDebtsButton;
    private final JButton backButton;
    private final JLabel resolveDebtsLabel;
    private final PanelBalances panelBalances;
    private final ControllerGroups controllerGroups;

    public PanelBalanceView(PanelBalances panelBalances) {
        this.panelBalances = panelBalances;
        controllerGroups = ControllerGroups.getGroupController();

        // https://stackoverflow.com/questions/32628964/how-to-align-multiple-elements-below-each-other
        this.setLayout(new GridBagLayout());

        String resolveDebtsButtonText = "Debts are evened";
        resolveDebtsButton = new JButton(resolveDebtsButtonText);
        backButton = new JButton("Back");
        resolveDebtsLabel = new JLabel("When pressing the '" + resolveDebtsButtonText + "' the corresponding tickets will be removed.");

        createListeners();
    }

    public void setGroup(Group group) {
        this.group = group;
        ControllerUsers userController = ControllerUsers.getUserController();
        HashMap<Integer, Map.Entry<Integer, Double>> groupDebts = group.updateAndGetWhoPaysWhoHowMuch();

        this.removeAll();
        this.updateUI();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.add(new JLabel("Overview of who needs to pay what amount and to who."), gbc);
        this.add(new JLabel(" "), gbc);

        // https://java2blog.com/format-double-to-2-decimal-places-java/
        DecimalFormat df = new DecimalFormat("#.##");
        if (!groupDebts.isEmpty()) {
            JPanel debtPanel = new JPanel();
            GridLayout gridLayout = new GridLayout(groupDebts.size() + 1, 3);
            debtPanel.setLayout(gridLayout);
            debtPanel.add(new JLabel("Person who needs to pay"));
            debtPanel.add(new JLabel("Amount to pay"));
            debtPanel.add(new JLabel("To who the person needs to pay"));
            for (int userHash : groupDebts.keySet()) {
                String payerName = userController.getUser(userHash).getName();
                Double debt = groupDebts.get(userHash).getValue();
                String receiverName = userController.getUser(groupDebts.get(userHash).getKey()).getName();
                debtPanel.add(new JLabel(payerName));
                debtPanel.add(new JLabel(df.format(debt) + " euro"));
                debtPanel.add(new JLabel(receiverName));
            }
            this.add(debtPanel, gbc);
            this.add(new JLabel(" "), gbc);
            this.add(resolveDebtsLabel, gbc);
            this.add(getButtonPanel(), gbc);
        }
        else {
            //BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            //this.setLayout(boxLayout);
            this.add(new JLabel("There are no tickets for this group."), gbc);
            this.add(backButton, gbc);
        }
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxLayout);

        buttonPanel.add(resolveDebtsButton);
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private void createListeners() {
        resolveDebtsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBalances.setStartState();
                controllerGroups.removeTicketsInGroup(controllerGroups.getGroupHash(group.getName()));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBalances.setStartState();
            }
        });
    }
}
