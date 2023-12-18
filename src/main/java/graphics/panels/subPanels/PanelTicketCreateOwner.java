package graphics.panels.subPanels;

import graphics.panels.PanelTickets;
import logic.controllers.ControllerGroups;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TypeSplit;
import logic.users.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * 3. Select owner, set amount and even or uneven split
 */
public class PanelTicketCreateOwner extends JPanel {
    private JLabel selectOwnerLabel1;
    private JLabel selectOwnerLabel2;
    private JLabel selectOwnerLabel3;
    private JButton nextButton;
    private JButton cancelButton;
    private JList<String> userJList;
    private DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private ControllerGroups groupController;
    private ControllerUsers userController;
    private PanelTickets ticketsPanel;
    private JLabel amountLabel;
    private JTextField amountField;
    private JComboBox spiltTypeComboBox;
    private JComboBox eventTypeComboBox;
    private boolean isOwnerSelected;


    public PanelTicketCreateOwner(PanelTickets panelTickets) {
        this.ticketsPanel = panelTickets;

        init();

        JPanel amountPanel = new JPanel();
        BoxLayout layout = new BoxLayout(amountPanel, BoxLayout.X_AXIS);
        amountPanel.setLayout(layout);
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);

        JPanel rightSidePanel = new JPanel();
        layout = new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS);
        rightSidePanel.setLayout(layout);
        rightSidePanel.add(amountPanel);
        rightSidePanel.add(spiltTypeComboBox);
        rightSidePanel.add(eventTypeComboBox);

        JPanel topPanel = new JPanel();
        layout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
        topPanel.setLayout(layout);
        topPanel.add(userJList);
        topPanel.add(rightSidePanel);

        JPanel buttonPanel = new JPanel();
        layout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(layout);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(selectOwnerLabel1);
        this.add(selectOwnerLabel2);
        this.add(selectOwnerLabel3);
        this.add(topPanel);
        this.add(buttonPanel);
    }

    private void init() {
        groupController = ControllerGroups.getGroupController();
        userController = ControllerUsers.getUserController();

        userMap = new HashMap<>();
        userList = new DefaultListModel<>();
        userJList = new JList<>(userList);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        amountLabel = new JLabel("Enter amount: ");
        amountField = new JTextField();
        spiltTypeComboBox = new JComboBox(TypeSplit.values());
        eventTypeComboBox = new JComboBox(TypeEvents.values());
        selectOwnerLabel1 = new JLabel("Select the user that has paid the ticket,");
        selectOwnerLabel2 = new JLabel("enter the total amount of the ticket");
        selectOwnerLabel3 = new JLabel("and select the correct type of split and event type!");

        createListeners();
    }

    public void setGroup(Group group) {
        userList.removeAllElements();
        for (int member : groupController.getGroupMembers(group.getName())) {
            System.out.println("user hash: " + member);
            System.out.printf("user: " + userController.getUser(member));
            User user = userController.getUser(member);
            userList.addElement(user.getName());
            userMap.put(user.getName(), userController.getUserHash(user));
        }
    }

    private void createListeners() {
        userJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (userJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        isOwnerSelected = false;
                    } else {
                        //Selection, enable the remove button.
                        isOwnerSelected = true;
                    }
                    checkAllFiledIn();
                }
            }
        });

        // https://stackoverflow.com/questions/17132452/java-check-if-jtextfield-is-empty-or-not
        amountField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkAllFiledIn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkAllFiledIn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkAllFiledIn();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketsPanel.setOwnerInfo(
                        userController.getUser(userMap.get(userJList.getSelectedValue())),
                        Double.parseDouble(amountField.getText()),
                        TypeSplit.valueOf(String.valueOf(spiltTypeComboBox.getSelectedItem())),
                        TypeEvents.valueOf(String.valueOf(eventTypeComboBox.getSelectedItem()))
                );
                clearComponents();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearComponents();
                ticketsPanel.setStartState();
            }
        });
    }

    private void clearComponents() {
        isOwnerSelected = false;
        userJList.clearSelection();
        userList.removeAllElements();
        userMap.clear();
        amountField.setText("");
    }

    private void checkAllFiledIn() {
        boolean isNumer = true;
        try {
            Double.parseDouble(amountField.getText());
        } catch (NumberFormatException exception){
            isNumer = false;
        }
        if (isOwnerSelected && isNumer) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }
}
