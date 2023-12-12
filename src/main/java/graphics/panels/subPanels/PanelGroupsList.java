package graphics.panels.subPanels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelGroupsList extends JPanel{
        private JList<String> groupJList;
        private DefaultListModel<String> groupList;
        private JButton removeButton;
        private String selectedGroup;

        public PanelGroupsList() {
            groupList = new DefaultListModel<>();
            groupJList = new JList<>(groupList);
            groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            groupJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

            removeButton = new JButton("Remove selected group");
            removeButton.setEnabled(false);

            createListeners();

            BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(layout);
            this.add(groupJList);
            this.add(removeButton);
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
                        }
                    }
                }
            });

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // ToDo: implement
                    System.out.println("Group should be deleted now.");
                }
            });
        }
}
