package graphics.panels.subPanels;

import logic.controllers.ControllerUsers;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class PanelUserCreate extends JPanel {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private JButton addUserButton;
    private JButton createUserButton;
    private JButton cancelButton;
    private JTextField fieldFirstName;
    private JTextField fieldLastName;
    private JLabel labelFirstName;
    private JLabel labelLastName;
    private Boolean waitingToCreateUser;
    private ControllerUsers userController;

    public PanelUserCreate() {
        addUserButton = new JButton("add user");
        createUserButton = new JButton("create user");
        createUserButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        fieldFirstName = new JTextField();
        fieldLastName = new JTextField();
        labelFirstName = new JLabel("Firstname: ");
        labelLastName = new JLabel("Lastname: ");
        waitingToCreateUser = true;
        userController = ControllerUsers.getUserController();

        createListeners();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(addUserButton);
    }

    private void createListeners() {
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waitingToCreateUser = false;
                changeUI();
            }
        });

        // https://stackoverflow.com/questions/17132452/java-check-if-jtextfield-is-empty-or-not
        fieldFirstName.getDocument().addDocumentListener(new DocumentListener() {
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

        // https://stackoverflow.com/questions/17132452/java-check-if-jtextfield-is-empty-or-not
        fieldLastName.getDocument().addDocumentListener(new DocumentListener() {
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

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waitingToCreateUser = true;
                changeUI();
            }
        });
    }

    private void checkFieldsFilledIn() {
        if (!fieldFirstName.getText().isEmpty() && !fieldLastName.getText().isEmpty()) {
            createUserButton.setEnabled(true);
        } else {
            createUserButton.setEnabled(false);
        }
    }

    private void changeUI() {
        if (waitingToCreateUser) {
            this.removeAll();
            this.updateUI();
            this.add(addUserButton);
        }
        else {
            this.removeAll();
            this.updateUI();
            JPanel topPanel = new JPanel();
            GridLayout gridLayout = new GridLayout(2, 2);
            topPanel.setLayout(gridLayout);
            topPanel.add(labelFirstName);
            topPanel.add(fieldFirstName);
            topPanel.add(labelLastName);
            topPanel.add(fieldLastName);

            JPanel bottomPanel = new JPanel();
            BoxLayout layout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
            bottomPanel.setLayout(layout);
            bottomPanel.add(createUserButton);
            bottomPanel.add(cancelButton);

            this.add(topPanel);
            this.add(bottomPanel);
        }
    }

    private  void createNewUser() {
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        boolean createUser = false;
        logger.finer(String.valueOf(userController.doesUserExist(firstName, lastName)));
        if (userController.doesUserExist(firstName, lastName)) {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            int answer = JOptionPane.showConfirmDialog(this, "This user already exits.\nDo you want to create it again?");
            if (answer == JOptionPane.YES_NO_OPTION) {
                createUser = true;
            }
        } else {
            createUser = true;
        }
        if (createUser) {
            userController.createUser(firstName, lastName);
            fieldFirstName.setText("");
            fieldLastName.setText("");
            waitingToCreateUser = true;
            createUserButton.setEnabled(false);
        }
        changeUI();
    }
}
