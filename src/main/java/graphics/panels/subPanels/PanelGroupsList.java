package graphics.panels.subPanels;

import graphics.ViewFrame;
import logic.MoneyTrackerLogger;
import logic.controllers.ControllerGroups;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class PanelGroupsList extends JPanel{
        private final Logger logger = MoneyTrackerLogger.getInstance().getLogger(this.getClass().getName());
        private final JList<String> groupJList;
        private final JButton removeButton;
        private String selectedGroup;
        private final ControllerGroups groupController;
        private final ViewFrame frame;

        public PanelGroupsList(ViewFrame frame) {
            this.frame = frame;
            groupController = ControllerGroups.getGroupController();
            groupJList = new JList<>(this.frame.getGroupList());
            groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

            removeButton = new JButton("Remove selected group");
            removeButton.setEnabled(false);

            createListeners();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.CENTER;
            this.setLayout(new GridBagLayout());
            this.add(groupJList, gbc);
            this.add(removeButton, gbc);
        }

        private void createListeners() {
            groupJList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() == false) {
                        if (groupJList.getSelectedIndex() == -1) {
                            //No selection, disable remove button.
                            removeButton.setEnabled(false);
                        }
                        else {
                            //Selection, enable the remove button.
                            removeButton.setEnabled(true);
                            selectedGroup = groupJList.getSelectedValue();
                            logger.finer("selectedGroup: " + selectedGroup);
                        }
                    }
                }
            });

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (groupController.removeGroup(selectedGroup) == -1) {
                        JOptionPane.showMessageDialog(frame, "The group has one or more undivided tickets.\nPlease resolve this first.", "Error group is not even", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
}
