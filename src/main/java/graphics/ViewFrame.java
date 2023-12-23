package graphics;

import graphics.panels.PanelBalances;
import graphics.panels.PanelGroups;
import graphics.panels.PanelTickets;
import graphics.panels.PanelUsers;
import logic.controllers.ControllerUsers;
import logic.groups.Group;
import logic.tickets.TicketInfo;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Objects;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private DefaultListModel<String> userList;
    private HashMap<String, Integer> userMap;
    private DefaultListModel<String> groupList;
    private DefaultListModel<String> ticketList;
    private ControllerUsers userController;
    private JTabbedPane tabbedPane;

    // Panels for the tabbed pane
    private PanelGroups groupsPanel;
    private PanelUsers usersPanel;
    private PanelTickets ticketsPanel;
    private PanelBalances balancesPanel;

    public ViewFrame() {
        super("MoneyTrackerApp");
    }

    public void init() {
        userController = ControllerUsers.getUserController();

        userList = new DefaultListModel<>();
        userMap = new HashMap<>();
        groupList = new DefaultListModel<>();
        ticketList = new DefaultListModel<>();

        createPanels();
        createTabbedPane();

        // https://alvinalexander.com/java/java-set-jframe-size/
        // https://stackoverflow.com/questions/9924282/auto-resize-jtabbedpane
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000, 800));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    private void createPanels() {
        groupsPanel = new PanelGroups(this);
        usersPanel = new PanelUsers(this);
        ticketsPanel = new PanelTickets(this);
        balancesPanel = new PanelBalances(this);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Groups", groupsPanel);
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Tickets", ticketsPanel);
        tabbedPane.addTab("Balances", balancesPanel);
        tabbedPane.setEnabled(true);
        tabbedPane.setAutoscrolls(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), "new user")) {
            User user = (User) evt.getNewValue();
            String name = user.getName();
            userMap.put(name, userController.getUserHash(user));
            userList.addElement(name);
        }
        else if (Objects.equals(evt.getPropertyName(), "remove user")) {
            User user = (User) evt.getOldValue();
            userList.removeElement(user.getName());
        }
        else if (Objects.equals(evt.getPropertyName(), "new group")) {
            Group group = (Group) evt.getNewValue();
            groupList.addElement(group.getName());
        }
        else if (Objects.equals(evt.getPropertyName(), "remove group")) {
            Group group = (Group) evt.getOldValue();
            groupList.removeElement(group.getName());
        }
        else if (Objects.equals(evt.getPropertyName(), "new ticket")) {
            TicketInfo ticket = (TicketInfo) evt.getNewValue();
            ticketList.addElement(ticket.getName());
        }
        else if (Objects.equals(evt.getPropertyName(), "remove ticket")) {
            TicketInfo ticket = (TicketInfo) evt.getOldValue();
            ticketList.removeElement(ticket.getName());
        }
    }

    public DefaultListModel<String> getUserList() {
        return userList;
    }

    public HashMap<String, Integer> getUserMap() {
        return userMap;
    }

    public DefaultListModel<String> getGroupList() {
        return groupList;
    }

    public DefaultListModel<String> getTicketList() {
        return ticketList;
    }
}
