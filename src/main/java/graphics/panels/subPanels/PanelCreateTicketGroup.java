package graphics.panels.subPanels;

import graphics.ViewFrame;
import graphics.panels.PanelTickets;
import logic.controllers.ControllerGroups;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class create the JPanel to select a group for a new ticket.
 */
public class PanelCreateTicketGroup extends JPanel {
    private JList<String> groupJList;
    private ControllerGroups groupController;
    private String selectedGroup;
    private boolean isGroupSelected;
    private PanelTickets ticketsPanel;
    private JTextField ticketNameField;
    private JButton nextButton;
    private JButton cancelButton;
    private ViewFrame frame;

    public PanelCreateTicketGroup(ViewFrame frame, PanelTickets panelTickets) {
        this.frame = frame;
        this.ticketsPanel = panelTickets;

        init();

        JPanel buttonPanel = new JPanel();
        BoxLayout layout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(layout);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(groupJList);
        this.add(buttonPanel);
    }

    private void init() {
        isGroupSelected = false;
        groupController = ControllerGroups.getGroupController();
        groupJList = new JList<>(frame.getGroupList());
        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ticketNameField = new JTextField();
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

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketsPanel.setProupSelectedAndName(groupController.getGroup(selectedGroup), ticketNameField.getText());
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketsPanel.setStarteState();
            }
        });
    }

    private void checkAllFilledIn() {
        if (isGroupSelected && !ticketNameField.getText().isEmpty()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }
}
