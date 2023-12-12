package graphics.panels.subPanels;

import logic.controllers.ControllerUsers;
import logic.users.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PanelUsersList extends JPanel {
    private JList<String> userJList;
    private DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private JButton removeButton;
    private int selectedUser;
    private ControllerUsers userController;

    public PanelUsersList() {
        userController = ControllerUsers.getUserController();

        userList = new DefaultListModel<>();
        userJList = new JList<>(userList);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userMap = new HashMap<>();

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
                        selectedUser = userMap.get(userList.get(e.getFirstIndex()));
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

    public void addUser(User user) {
        if (user.getID() == 1) {
            String name = user.getName();
            userMap.put(name, userController.getUserHash(user));
            userList.addElement(name);
        }
        else {
            String name = user.getName() + " " + user.getID();
            userMap.put(name, userController.getUserHash(user));
            userList.addElement(name);
        }
    }

    public void removeUser(User user) {
        if (user.getID() == 1) {
            userList.removeElement(user.getName());
        }
        else {
            userList.removeElement(user.getName() + " " + user.getID());
        }
    }
}
