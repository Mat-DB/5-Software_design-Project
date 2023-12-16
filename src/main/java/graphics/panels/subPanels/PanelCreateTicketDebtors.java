package graphics.panels.subPanels;

import graphics.panels.PanelTickets;
import logic.controllers.ControllerUsers;
import logic.tickets.TicketSplit.TypeSplit;
import logic.users.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * 4. Select debtors and if uneven set amounts
 */
public class PanelCreateTicketDebtors extends JPanel {
    private PanelTickets panelTickets;
    private JLabel selectUserLabel;
    private JButton nextButton;
    private JButton cancelButton;
    private JList<String> userJList;
    private DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private ControllerUsers usersController;
    private HashMap<JLabel, JTextField> userDebtsJStuff;
    private HashMap<Integer, Double> debts;
    private boolean usersSelected;

    public PanelCreateTicketDebtors(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;

        usersController = ControllerUsers.getUserController();

        usersSelected = false;
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        userList = new DefaultListModel<>();
        userJList = new JList<>(userList);
        userJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userMap = new HashMap<>();
        selectUserLabel = new JLabel("Select the users who need to pay.");
        userDebtsJStuff = new HashMap<>();
        debts = new HashMap<>();

        createListeners();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(selectUserLabel);
        this.add(userJList);
        this.add(getButtonPanel());
    }

    public void init() {
        userJList.removeAll();
        for (int userHash : panelTickets.getGroup().getParticipants()) {
            if (userHash != usersController.getUserHash(panelTickets.getOwner())) {
                User user = usersController.getUser(userHash);
                userList.addElement(user.getName());
                userMap.put(user.getName(), usersController.getUserHash(user));
            }
        }
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        BoxLayout layout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(layout);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void createListeners() {
        userJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    nextButton.setEnabled(true);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTickets.setStarteState();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usersSelected) {
                    changeUI();
                } else {
                    collectUserDebts();
                }
            }
        });
    }

    /**
     * Change/update the UI if needed.
     */
    private void changeUI() {
        if (panelTickets.getSplitType() == TypeSplit.UNEVEN_SPLIT) {
            this.removeAll();
            this.updateUI();
            userDebtsJStuff.clear();
            for (String user : userJList.getSelectedValuesList()) {
                userDebtsJStuff.put(new JLabel(user), new JTextField());
            }
            JLabel amountLabel = new JLabel("Enter the amount each user needs to pay.");

            BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(layout);
            this.add(amountLabel);
            GridLayout gridLayout = new GridLayout(userDebtsJStuff.size(), 2);
            JPanel amountPanel = new JPanel();
            amountPanel.setLayout(gridLayout);
            for (JLabel label : userDebtsJStuff.keySet()) {
                JTextField textField = userDebtsJStuff.get(label);
                amountPanel.add(label);
                amountPanel.add(textField);
            }
            this.add(amountPanel);
            this.add(getButtonPanel());

            usersSelected = true;

        } else {
            for (String user : userJList.getSelectedValuesList()) {
                debts.put(userMap.get(user), 0.0);
            }
            panelTickets.setDebtors(debts);
            userJList.clearSelection();
        }
    }

    private void collectUserDebts() {
        boolean allFiledIn = true;
        boolean isNumber = true;
        double total = 0;
        for (JTextField textField : userDebtsJStuff.values()) {
            if (textField.getText().isEmpty()) {
                allFiledIn = false;
            } else {
                try {
                    Double.parseDouble(textField.getText());
                } catch (NumberFormatException exception){
                    isNumber = false;
                }
            }
        }
        if (!allFiledIn) {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            JOptionPane.showMessageDialog(this, "Please fill in the debts of all users", "Set all debts", JOptionPane.WARNING_MESSAGE);
        }
        else if(!isNumber) {
            JOptionPane.showMessageDialog(this, "Not all amounts are numbers", "Not all numbers", JOptionPane.ERROR_MESSAGE);
        } else {
            for (JLabel label : userDebtsJStuff.keySet()) {
                debts.put(usersController.getUserHash(label.getText()), Double.parseDouble(userDebtsJStuff.get(label).getText()));
                total += Double.parseDouble(userDebtsJStuff.get(label).getText());
            }
        }
        if (!(total == panelTickets.getAmount())) {
            JOptionPane.showMessageDialog(this, "The sum does not equal the ticket total", "Total does not match", JOptionPane.ERROR_MESSAGE);
        } else {
            panelTickets.setDebtors(debts);
            usersSelected = false;
            userJList.clearSelection();
        }
    }
}
