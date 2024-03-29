package graphics.panels.subPanels;

import graphics.ViewFrame;
import logic.controllers.ControllerGroups;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Select users and enter a group name.
 */
public class PanelGroupCreate extends JPanel {
    private final JButton addGroupButton;
    private final JButton createGroupButton;
    private final JButton cancelButton;
    private final JCheckBox selectAllBox;
    private final JTextField groupNameField;
    private final JLabel groupNameLabel;
    private Boolean waitingToCreateGroup;
    private final ControllerGroups groupsController;
    private Set<Integer> usersInGroup;
    private final PanelUsersList panelUsersList;
    private final JList<String> userJList;
    private boolean usersSelected;
    private final ViewFrame frame;

    public PanelGroupCreate(ViewFrame frame) {
        this.frame = frame;
        addGroupButton = new JButton("add group");
        createGroupButton = new JButton("create group");
        createGroupButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        selectAllBox = new JCheckBox("Select all");
        groupNameField = new JTextField();
        groupNameLabel = new JLabel("Group name: ");
        waitingToCreateGroup = true;
        groupsController = ControllerGroups.getGroupController();
        panelUsersList = new PanelUsersList(frame);
        panelUsersList.setSelectMultiple();
        userJList = panelUsersList.getuserJList();
        usersSelected = false;
        usersInGroup = new HashSet<>();

        createListeners();

        //BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        //this.setLayout(layout);
        this.add(addGroupButton);
    }

    private void createListeners() {
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waitingToCreateGroup = false;
                changeUI();
            }
        });

        // https://stackoverflow.com/questions/17132452/java-check-if-jtextfield-is-empty-or-not
        groupNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFieldsFilledIn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFieldsFilledIn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFieldsFilledIn();
            }
        });

        createGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGroup();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetComponents();
                changeUI();
            }
        });

        userJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    usersSelected = true;
                    checkFieldsFilledIn();
                }
            }
        });

        selectAllBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersSelected = true;
                checkFieldsFilledIn();
            }
        });
    }

    private void checkFieldsFilledIn() {
        if (!groupNameField.getText().isEmpty() && usersSelected) {
            createGroupButton.setEnabled(true);
        } else {
            createGroupButton.setEnabled(false);
        }
    }

    private void changeUI() {
        if (waitingToCreateGroup) {
            this.removeAll();
            this.updateUI();
            this.add(addGroupButton);
        }
        else {
            this.removeAll();
            this.updateUI();

            JLabel explainLabel = new JLabel("Fill in the name of the group and select the users.");

            JPanel middlePanelLeft = new JPanel();
            GridLayout gridLayout = new GridLayout(2, 2);
            middlePanelLeft.setLayout(gridLayout);
            middlePanelLeft.add(groupNameLabel);
            middlePanelLeft.add(groupNameField);

            JPanel middlePanelRight = new JPanel();
            middlePanelRight.setLayout(new GridBagLayout());
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridwidth = GridBagConstraints.REMAINDER;
            middlePanelRight.add(selectAllBox, gbc1);
            middlePanelRight.add(userJList, gbc1);

            JPanel middlePanel = new JPanel();
            middlePanel.add(middlePanelLeft);
            middlePanel.add(middlePanelRight);

            JPanel bottomPanel = new JPanel();
            BoxLayout boxLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
            bottomPanel.setLayout(boxLayout);
            bottomPanel.add(createGroupButton);
            bottomPanel.add(cancelButton);


            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.anchor = GridBagConstraints.CENTER;
            this.setLayout(new GridBagLayout());
            this.add(explainLabel, gbc2);
            this.add(middlePanel, gbc2);
            this.add(bottomPanel, gbc2);
        }
    }

    private  void createNewGroup() {
        String groupname = groupNameField.getText();
        boolean createGroup = false;
        boolean errorThrown = false;
        if (groupsController.doesGroupExist(groupname) && !errorThrown) {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            JOptionPane.showMessageDialog(this, "This group name exits.\nChoose another one.", "Group exists", JOptionPane.ERROR_MESSAGE);
            errorThrown = true;
        }
        else if (userJList.getSelectedValuesList().size() <= 1 && !selectAllBox.isSelected() && !errorThrown) {
            JOptionPane.showMessageDialog(this, "Select at least 2 users to create a group.", "Not enough users selected", JOptionPane.ERROR_MESSAGE);
            errorThrown = true;
        }
        else if (frame.getUserList().size() <= 1 && selectAllBox.isSelected() && !errorThrown) {
            JOptionPane.showMessageDialog(this, "Not enough users to create a group.", "Not enough users", JOptionPane.ERROR_MESSAGE);
            errorThrown = true;
        }
        else {
            createGroup = true;
        }
        if (createGroup) {
            usersInGroup = new HashSet<>();
            if (!selectAllBox.isSelected()) {
                for (String userName : userJList.getSelectedValuesList()) {
                    usersInGroup.add(frame.getUserMap().get(userName));
                }
            }
            else {
                for (Object listObject : frame.getUserList().toArray()) {
                    usersInGroup.add(frame.getUserMap().get(listObject.toString()));
                }
            }
            int returnCode = groupsController.createGroup(groupname, usersInGroup);
            if (returnCode == -1) {
                String message = "The group name " + groupname + " already exists!";
                JOptionPane.showMessageDialog(this, message, "Group name exists", JOptionPane.ERROR_MESSAGE);
            }
            else {
                resetComponents();
            }
        }
        changeUI();
    }

    private void resetComponents() {
        groupNameField.setText("");
        waitingToCreateGroup = true;
        usersSelected = false;
        selectAllBox.setSelected(false);
        createGroupButton.setEnabled(false);
    }
}
