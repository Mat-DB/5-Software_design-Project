package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelGroupCreate;
import graphics.panels.subPanels.PanelGroupsList;

import javax.swing.*;

public class PanelGroups extends JPanel {
    public PanelGroups(ViewFrame frame) {
        PanelGroupsList panelGroupsList = new PanelGroupsList(frame);
        PanelGroupCreate panelGroupCreate = new PanelGroupCreate(frame);

        this.add(panelGroupsList);
        this.add(panelGroupCreate);
    }
}
