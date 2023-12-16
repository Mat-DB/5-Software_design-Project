package graphics;

import graphics.panels.PanelBalances;
import graphics.panels.PanelGroups;
import graphics.panels.PanelTickets;
import graphics.panels.PanelUsers;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Objects;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private PanelGroups groupsPanel;
    private PanelUsers usersPanel;
    private PanelTickets ticketsPanel;
    private PanelBalances balancesPanel;
    private DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private DefaultListModel<String> groupList;
    private ControllerUsers userController;

    public ViewFrame() {
        super("MoneyTrackerApp");
    }

    public void initialize() {
        //this.setSize(500, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userController = ControllerUsers.getUserController();

        JTabbedPane tabbedPane = new JTabbedPane();
        userList = new DefaultListModel<>();
        userMap = new HashMap<>();
        groupList = new DefaultListModel<>();
        groupsPanel = new PanelGroups(this);
        usersPanel = new PanelUsers(this);
        ticketsPanel = new PanelTickets(this);
        balancesPanel = new PanelBalances();

        tabbedPane.addTab("Groups", groupsPanel);
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Tickets", ticketsPanel);
        tabbedPane.addTab("Balances", balancesPanel);
        tabbedPane.setPreferredSize(new Dimension(800, 500));

        JPanel panel = new JPanel();
        panel.add(tabbedPane);
        this.add(panel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), "new user")) {
            User user = (User) evt.getNewValue();
//            if (user.getID() == 1) {
//                String name = user.getName();
//                userMap.put(name, userController.getUserHash(user));
//                userList.addElement(name);
//            }
//            else {
//                String name = user.getName() + " " + user.getID();
//                userMap.put(name, userController.getUserHash(user));
//                userList.addElement(name);
//            }
            // Test
            String name = user.getName();
            userMap.put(name, userController.getUserHash(user));
            userList.addElement(name);
            // End test
        }
        else if (Objects.equals(evt.getPropertyName(), "remove user")) {
            User user = (User) evt.getOldValue();
//            if (user.getID() == 1) {
//                userList.removeElement(user.getName());
//            }
//            else {
//                userList.removeElement(user.getName() + " " + user.getID());
//            }
            // Test
            userList.removeElement(user.getName());
            // End test
        }
        else if (Objects.equals(evt.getPropertyName(), "new group")) {
            Group group = (Group) evt.getNewValue();
            groupList.addElement(group.getName());
        }
        else if (Objects.equals(evt.getPropertyName(), "remove group")) {
            Group group = (Group) evt.getOldValue();
            groupList.removeElement(group.getName());
        }
    }

    public DefaultListModel<String> getUserList() {
        return userList;
    }

    public DefaultListModel<String> getGroupList() {
        return groupList;
    }

    public HashMap<String, Integer> getUserMap() {
        return userMap;
    }
}
