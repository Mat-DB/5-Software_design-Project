package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelUserCreate;
import graphics.panels.subPanels.PanelUsersList;

import javax.swing.*;

public class PanelUsers extends JPanel {
    public PanelUsers(ViewFrame frame) {
        PanelUsersList panelUsersList = new PanelUsersList(frame);
        PanelUserCreate panelUserCreate = new PanelUserCreate();

        this.add(panelUsersList);
        this.add(panelUserCreate);
    }
}
