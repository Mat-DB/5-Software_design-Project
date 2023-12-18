package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelUserCreate;
import graphics.panels.subPanels.PanelUsersList;

import javax.swing.*;

public class PanelUsers extends JPanel {
    private PanelUsersList panelUsersList;
    private PanelUserCreate panelUserCreate;
    public PanelUsers(ViewFrame frame) {
        panelUsersList = new PanelUsersList(frame);
        panelUserCreate = new PanelUserCreate();

        this.add(panelUsersList);
        this.add(panelUserCreate);
    }
}
