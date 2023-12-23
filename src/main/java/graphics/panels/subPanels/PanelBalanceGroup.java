package graphics.panels.subPanels;

import graphics.ViewFrame;
import graphics.panels.PanelBalances;
import logic.controllers.ControllerGroups;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelBalanceGroup extends JPanel {
    private JLabel infoLabel;
    private JList<String> groupJList;
    private String selectedGroup;
    private final PanelBalances panelBalances;
    private JButton checkButton;
    private final ViewFrame frame;

    public PanelBalanceGroup(ViewFrame frame, PanelBalances panelBalances) {
        this.frame = frame;
        this.panelBalances = panelBalances;

        init();

        // https://stackoverflow.com/questions/32628964/how-to-align-multiple-elements-below-each-other
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.add(infoLabel, gbc);
        this.add(groupJList, gbc);
        this.add(checkButton, gbc);
    }

    private void init() {
        infoLabel = new JLabel("Select the group you want to check the balance for.");
        groupJList = new JList<>(frame.getGroupList());
        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        checkButton = new JButton("Check group");
        checkButton.setEnabled(false);

        createListeners();
    }

    private void createListeners() {
        groupJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (groupJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        checkButton.setEnabled(false);
                    }
                    else {
                        //Selection, enable the remove button.
                        checkButton.setEnabled(true);
                        selectedGroup = frame.getGroupList().get(e.getFirstIndex());
                    }
                }
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBalances.setGroup(ControllerGroups.getGroupController().getGroup(selectedGroup));
                clearComponents();
            }
        });
    }

    private void clearComponents() {
        groupJList.clearSelection();
    }
}
