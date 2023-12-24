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
import java.util.HashSet;
import java.util.Set;

/**
 * 4. Select debtors and if uneven set amounts
 */
public class PanelTicketCreateDebtors extends JPanel {
    private final PanelTickets panelTickets;
    private final JLabel selectUserLabel;
    private final JButton nextButton;
    private final JButton cancelButton;
    private final JCheckBox selectAllBox;
    private final JList<String> userJList;
    private final DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private final ControllerUsers usersController;
    private HashMap<JLabel, JTextField> userDebtsJStuff;
    private HashMap<Integer, Double> debts;
    private boolean usersSelected;

    public PanelTicketCreateDebtors(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;

        usersController = ControllerUsers.getUserController();

        usersSelected = false;
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        selectAllBox = new JCheckBox("Select all");
        userList = new DefaultListModel<>();
        userJList = new JList<>(userList);
        userJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userMap = new HashMap<>();
        selectUserLabel = new JLabel("Select the users who need to pay.");
        userDebtsJStuff = new HashMap<>();
        debts = new HashMap<>();

        createListeners();
    }

    public void init() {
        this.removeAll();
        this.updateUI();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(selectUserLabel);
        this.add(selectAllBox);
        this.add(userJList);
        this.add(getButtonPanel());

        //resetComponents();
        userList.removeAllElements();
        userMap = new HashMap<>();
        for (int userHash : panelTickets.getGroup().getParticipants()) {
            User user = usersController.getUser(userHash);
            userList.addElement(user.getName());
            userMap.put(user.getName(), usersController.getUserHash(user));
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

        selectAllBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButton.setEnabled(true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetComponents();
                panelTickets.setStartState();
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

    private void resetComponents() {
        usersSelected = false;
        debts = new HashMap<>();
        userMap = new HashMap<>();
        userList.removeAllElements();
        userJList.clearSelection();
        selectAllBox.setSelected(false);
        nextButton.setEnabled(false);
    }

    /**
     * Change/update the UI if needed.
     */
    private void changeUI() {
        if (panelTickets.getSplitType() == TypeSplit.UNEVEN_SPLIT) { //UNEVEN SPLIT
            this.removeAll();
            this.updateUI();
            userDebtsJStuff = new HashMap<>();
            if (!selectAllBox.isSelected()) {
                for (String userName : userJList.getSelectedValuesList()) {
                    userDebtsJStuff.put(new JLabel(userName), new JTextField());
                }
            }
            else {
                for (Object listObject : userList.toArray()) {
                    String userName = listObject.toString();
                    userDebtsJStuff.put(new JLabel(userName), new JTextField());
                }
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

        } else { // EVEN SPLIT
            Set<Integer> debts1 = new HashSet<>();
            if (!selectAllBox.isSelected()) {
                for (String userName : userJList.getSelectedValuesList()) {
                    if (usersController.getUserHash(panelTickets.getOwner()) != userMap.get(userName)) {
                        debts1.add(userMap.get(userName));
                    }
                }
            }
            else {
                for (Object listObject : userList.toArray()) {
                    String userName = listObject.toString();
                    if (usersController.getUserHash(panelTickets.getOwner()) != userMap.get(userName)) {
                        debts1.add(userMap.get(userName));
                    }
                }
            }
            panelTickets.setDebtorsEvenSplit(debts1);
            resetComponents();
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
                } catch (Exception e){
                    isNumber = false;
                }
            }
        }
        if (!allFiledIn) { // Not all filled in
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            JOptionPane.showMessageDialog(this, "Please fill in the debts of all users", "Set all debts", JOptionPane.WARNING_MESSAGE);
        }
        else if(!isNumber) { // Not all are a number
            JOptionPane.showMessageDialog(this, "Not all amounts are numbers", "Not all numbers", JOptionPane.ERROR_MESSAGE);
        }
        else { // All filled in and are numbers
            for (JLabel label : userDebtsJStuff.keySet()) {
                double amount = Double.parseDouble(userDebtsJStuff.get(label).getText());
                System.out.println(this.getClass() + ", amount: " + amount);
                debts.put(userMap.get(label.getText()), amount);
                total += amount;
            }
        }
        if (!(total == panelTickets.getAmount())) {
            String message = "The sum does not equal the ticket total,\nthe total should be " + panelTickets.getAmount();
            JOptionPane.showMessageDialog(this, message, "Total does not match", JOptionPane.ERROR_MESSAGE);
        }
        else { // UNEVEN SPLIT
            System.out.println(this.getClass() + ", debts: " + debts);
            panelTickets.setDebtorsUnevenSplit(debts);
            resetComponents();
        }
    }
}
