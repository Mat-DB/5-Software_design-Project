package graphics;

import graphics.panels.PanelBalances;
import graphics.panels.PanelGroups;
import graphics.panels.PanelTickets;
import graphics.panels.PanelUsers;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private PanelGroups groupsPanel;
    private PanelUsers usersPanel;
    private PanelTickets ticketsPanel;
    private PanelBalances balancesPanel;

    public ViewFrame() {
        super("MoneyTrackerApp");
    }

    public void initialize() {
        //this.setSize(500, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        groupsPanel = new PanelGroups();
        usersPanel = new PanelUsers();
        ticketsPanel = new PanelTickets();
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
            usersPanel.userAdded(user);
        }
        else if (Objects.equals(evt.getPropertyName(), "remove user")) {
            User user = (User) evt.getOldValue();
            usersPanel.userRemoved(user);
        }
    }
}
