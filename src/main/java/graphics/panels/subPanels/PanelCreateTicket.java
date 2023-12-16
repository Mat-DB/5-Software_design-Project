package graphics.panels.subPanels;

import graphics.panels.TicketPanelStates;
import logic.controllers.ControllerGroups;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCreateTicket extends JPanel {
    private TicketPanelStates state;
    private JButton previousButton;
    private JButton nextButton;
    private JButton cancelButton;
    private JList<String> groupJList;
    private DefaultListModel<String> groupList;
    private ControllerGroups groupController;
    private String selectedGroup;

    public PanelCreateTicket() {
        groupController = ControllerGroups.getGroupController();
        state = TicketPanelStates.GROUP;
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        cancelButton = new JButton("Cancel");
        groupList = new DefaultListModel<>();
        groupJList = new JList<>(groupList);

        createListeners();

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        addButtons();

        changeUI();

        //addButtons();
        //this.setSize(300, 400);
        //this.setVisible(true);
    }

    private void changeUI() {
//        switch (state){
//            case GROUP -> {
//                groupList.removeAllElements();
//                for (Group group : groupController.getGroups()) {
//                    groupList.addElement(group.getName());
//                }
//                groupList.addElement("test");
//                this.removeAll();
//                this.add(groupJList);
//                addButtons();
//            }
//            case USERS -> {
//                System.out.println("USER");
//            }
//            case TICKET -> {
//                System.out.println("TICKET");
//            }
//            case CONFIRM -> {
//                System.out.println("CONFIRM");
//            }
//            case END -> {
//                this.removeAll();
//                this.setVisible(false);
//            }
//        }
    }

    private void createListeners() {
        groupJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false && state == TicketPanelStates.GROUP) {
                    if (groupJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        nextButton.setEnabled(false);
                    }
                    else {
                        //Selection, enable the remove button.
                        nextButton.setEnabled(true);
                        selectedGroup = groupList.get(e.getFirstIndex());
                    }
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                state = switch (state){
//                    case GROUP -> CreateTicketStates.USERS;
//                    case USERS -> CreateTicketStates.TICKET;
//                    case TICKET -> CreateTicketStates.CONFIRM;
//                    case CONFIRM -> CreateTicketStates.END;
//                    case END -> CreateTicketStates.GROUP;
//                };
                updateUI();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                state = switch (state){
//                    case GROUP -> CreateTicketStates.GROUP;
//                    case USERS -> CreateTicketStates.GROUP;
//                    case TICKET -> CreateTicketStates.USERS;
//                    case CONFIRM -> CreateTicketStates.TICKET;
//                    case END -> CreateTicketStates.GROUP;
//                };
                updateUI();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //state = TicketPanelStates.END;
                updateUI();
            }
        });
    }

    private void addButtons() {
        this.add(previousButton);
        this.add(nextButton);
        this.add(cancelButton);
    }
}
