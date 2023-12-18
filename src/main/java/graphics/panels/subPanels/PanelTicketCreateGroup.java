package graphics.panels.subPanels;

import graphics.ViewFrame;
import graphics.panels.PanelTickets;
import logic.controllers.ControllerGroups;
import logic.controllers.ControllerTickets;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class create the JPanel to select a group for a new ticket.
 */
public class PanelTicketCreateGroup extends JPanel {
    private JList<String> groupJList;
    private ControllerGroups groupController;
    private String selectedGroup;
    private boolean isGroupSelected;
    private PanelTickets ticketsPanel;
    private JLabel ticketNameLabel;
    private JTextField ticketNameField;
    private JButton nextButton;
    private JButton cancelButton;
    private ViewFrame frame;

    public PanelTicketCreateGroup(ViewFrame frame, PanelTickets panelTickets) {
        this.frame = frame;
        this.ticketsPanel = panelTickets;

        init();

        JPanel topPanel = new JPanel();
        topPanel.add(groupJList);

        JPanel namePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(namePanel, BoxLayout.Y_AXIS);
        namePanel.setLayout(boxLayout);
        namePanel.add(ticketNameLabel);
        namePanel.add(ticketNameField);

        topPanel.add(namePanel);

        boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.add(topPanel);

        JPanel buttonPanel = new JPanel();
        boxLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxLayout);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel);
    }

    private void init() {
        isGroupSelected = false;
        groupController = ControllerGroups.getGroupController();
        groupJList = new JList<>(frame.getGroupList());
        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ticketNameField = new JTextField();
        ticketNameLabel = new JLabel("Enter ticket name: ");
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        cancelButton = new JButton("Cancel");

        createListeners();
    }

    private void createListeners() {
        groupJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (groupJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        isGroupSelected = false;
                        checkAllFilledIn();
                    }
                    else {
                        //Selection, enable the remove button.
                        isGroupSelected = true;
                        checkAllFilledIn();
                        selectedGroup = frame.getGroupList().get(e.getFirstIndex());
                    }
                }
            }
        });

        ticketNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkAllFilledIn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkAllFilledIn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkAllFilledIn();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!doesTicketNameExists()) {
                    ticketsPanel.setGroupSelectedAndName(groupController.getGroup(selectedGroup), ticketNameField.getText());
                    clearComponents();
                }
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
        groupJList.clearSelection();
        ticketNameField.setText("");
    }

    private boolean doesTicketNameExists() {
        boolean exists = false;
        // If ticket name already exists
        if (ControllerTickets.getTicketController().doesTicketNameExist(ticketNameField.getText())) {
            exists = true;
            JOptionPane.showMessageDialog(this, "This ticket name already exists. Please choose another one.", "TicketInfo name exists", JOptionPane.ERROR_MESSAGE);
        }
        return exists;
    }

    private void checkAllFilledIn() {
        if (isGroupSelected && !(ticketNameField.getText().isEmpty())) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }
}
