package graphics.panels;

import graphics.ViewFrame;
import graphics.panels.subPanels.PanelBalanceGroup;
import graphics.panels.subPanels.PanelBalanceView;
import logic.groups.Group;

import javax.swing.*;

public class PanelBalances extends JPanel {
    private BalancesPanelStates state;
    private Group group;
    private ViewFrame frame;
    private final PanelBalanceGroup panelGetGroup;
    private final PanelBalanceView panelBalanceView;

    public PanelBalances(ViewFrame frame) {
        this.frame = frame;

        state = BalancesPanelStates.SELECT_GROUP;
        panelGetGroup = new PanelBalanceGroup(frame, this);
        panelBalanceView = new PanelBalanceView();

        changeUI();
    }

    private void changeUI() {
        switch (state) {
            case SELECT_GROUP -> {
                this.removeAll();
                this.updateUI();
                this.add(panelGetGroup);
            }
            case CALCULATE_AND_VIEW_BALANCE -> {
                this.removeAll();
                this.updateUI();
                this.add(panelBalanceView);
                panelBalanceView.setGroup(group);
            }
        }
    }

    public void setStartState() {
        state = BalancesPanelStates.SELECT_GROUP;
        changeUI();
    }

    public void setGroup(Group group) {
        this.group = group;
        state = BalancesPanelStates.CALCULATE_AND_VIEW_BALANCE;
        changeUI();
    }
}
