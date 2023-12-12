package graphics.panels;

import graphics.panels.subPanels.PanelUserCreate;
import graphics.panels.subPanels.PanelUsersList;
import logic.users.User;

import javax.swing.*;

public class PanelUsers extends JPanel {
    private PanelUsersList panelUsersList;
    private PanelUserCreate panelUserCreate;
    public PanelUsers() {
        panelUsersList = new PanelUsersList();
        panelUserCreate = new PanelUserCreate();

        this.add(panelUsersList);
        this.add(panelUserCreate);
    }

    public void userAdded(User user) {
        panelUsersList.addUser(user);
    }

    public void userRemoved(User user) {
        panelUsersList.removeUser(user);
    }
}
