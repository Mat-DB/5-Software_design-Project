package graphics.panels.subPanels;

import graphics.ViewFrame;
import logic.controllers.ControllerUsers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelUsersList extends JPanel {
    private JList<String> userJList;
    private JButton removeButton;
    private int selectedUser;
    private ControllerUsers userController;
    private ViewFrame frame;

    public PanelUsersList(ViewFrame frame) {
        this.frame = frame;
        userController = ControllerUsers.getUserController();

        userJList = new JList<>(frame.getUserList());
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        removeButton = new JButton("Remove selected user");
        removeButton.setEnabled(false);

        createListeners();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(userJList);
        this.add(removeButton);
    }

    private void createListeners() {
        userJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (userJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        removeButton.setEnabled(false);
                    }
                    else {
                        //Selection, enable the remove button.
                        removeButton.setEnabled(true);
                        selectedUser = frame.getUserMap().get(frame.getUserList().get(userJList.getSelectedIndex()));
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userController.removeUser(selectedUser);
            }
        });
    }

    public void setSelectMultiple() {
        userJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public JList<String> getuserJList() {
        return userJList;
    }
}
