package graphics.panels.subPanels;

import logic.controllers.ControllerUsers;
import logic.groups.Group;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PanelBalanceView extends JPanel {
    public PanelBalanceView() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
    }

    public void setGroup(Group group) {
        ControllerUsers userController = ControllerUsers.getUserController();
        HashMap<Integer, Map.Entry<Integer, Double>> groupDebts = group.updateAndGetWhoPaysWhoHowMuch();
        System.out.println(groupDebts);

        this.removeAll();
        this.updateUI();

        this.add(new JLabel("Overview of who needs to pay what amount and to who."));

        JPanel debtPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(groupDebts.size()+1, 3);
        debtPanel.setLayout(gridLayout);
        debtPanel.add(new JLabel("Person who needs to pay"));
        debtPanel.add(new JLabel("Amount to pay"));
        debtPanel.add(new JLabel("To who the person needs to pay"));
        for (int userHash : groupDebts.keySet()) {
            String payerName = userController.getUser(userHash).getName();
            Double debt = groupDebts.get(userHash).getValue();
            String receiverName = userController.getUser(groupDebts.get(userHash).getKey()).getName();
            debtPanel.add(new JLabel(payerName));
            debtPanel.add(new JLabel(debt.toString()));
            debtPanel.add(new JLabel(receiverName));
        }

        this.add(debtPanel);
    }
}
