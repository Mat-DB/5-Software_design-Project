package graphics.panels.subPanels;

import graphics.ViewFrame;
import graphics.panels.PanelBalances;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelBalanceGroup extends JPanel {
    private JLabel infoLabel;
    private JList<String> groupJList;
    private String selectedGroup;
    private PanelBalances panelBalances;
    private JButton nextButton;
    private JButton cancelButton;
    private ViewFrame frame;

    public PanelBalanceGroup(ViewFrame frame, PanelBalances panelBalances) {
        this.frame = frame;
        this.panelBalances = panelBalances;

        init();

        JPanel topPanel = new JPanel();
        topPanel.add(groupJList);

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.add(topPanel);

        JPanel buttonPanel = new JPanel();
        boxLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxLayout);
        buttonPanel.add(nextButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel);
    }

    private void init() {
        groupJList = new JList<>(frame.getGroupList());
        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        cancelButton = new JButton("Cancel");

        createListeners();
    }

    private void createListeners() {
        groupJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (groupJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        nextButton.setEnabled(false);
                    }
                    else {
                        //Selection, enable the remove button.
                        nextButton.setEnabled(true);
                        selectedGroup = frame.getGroupList().get(e.getFirstIndex());
                    }
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ticketsPanel.setGroupSelectedAndName(groupController.getGroup(selectedGroup), ticketNameField.getText());
                clearComponents();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearComponents();
                panelBalances.setStartState();
            }
        });
    }

    private void clearComponents() {
        groupJList.clearSelection();
    }
}
