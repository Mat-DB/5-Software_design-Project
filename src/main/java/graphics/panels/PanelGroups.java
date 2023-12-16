package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelGroupCreate;
import graphics.panels.subPanels.PanelGroupsList;
import logic.groups.Group;

import javax.swing.*;

public class PanelGroups extends JPanel {
    private PanelGroupsList panelGroupsList;
    private PanelGroupCreate panelGroupCreate;
    public PanelGroups(ViewFrame frame) {
        panelGroupsList = new PanelGroupsList(frame);
        panelGroupCreate = new PanelGroupCreate(frame);

        //BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        //this.setLayout(layout);
        this.add(panelGroupsList);
        this.add(panelGroupCreate);
    }
}
