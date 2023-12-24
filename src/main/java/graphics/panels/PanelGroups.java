package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelGroupCreate;
import graphics.panels.subPanels.PanelGroupsList;

import javax.swing.*;

public class PanelGroups extends JPanel {
    public PanelGroups(ViewFrame frame) {
        JLabel explainLabel = new JLabel("Overview of all the groups. You can remove and add groups.");
        PanelGroupsList panelGroupsList = new PanelGroupsList(frame);
        PanelGroupCreate panelGroupCreate = new PanelGroupCreate(frame);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(panelGroupsList);
        bottomPanel.add(panelGroupCreate);

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.add(explainLabel, JPanel.CENTER_ALIGNMENT);
        this.add(bottomPanel);
    }
}
