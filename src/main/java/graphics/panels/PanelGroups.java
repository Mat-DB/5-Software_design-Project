package graphics.panels;

import graphics.panels.subPanels.PanelGroupsList;

import javax.swing.*;

public class PanelGroups extends JPanel {
    private PanelGroupsList panelGroupsList;
    public PanelGroups() {
        panelGroupsList = new PanelGroupsList();

        this.add(panelGroupsList);
    }
}
