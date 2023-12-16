package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelGroupCreate;
import graphics.panels.subPanels.PanelGroupsList;

import javax.swing.*;

public class PanelGroups extends JPanel {
    private PanelGroupsList panelGroupsList;
    private PanelGroupCreate panelGroupCreate;
    public PanelGroups(ViewFrame frame) {
        panelGroupsList = new PanelGroupsList(frame);
        panelGroupCreate = new PanelGroupCreate(frame);

        this.add(panelGroupsList);
        this.add(panelGroupCreate);
    }
}
