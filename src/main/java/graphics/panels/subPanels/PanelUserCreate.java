package graphics.panels.subPanels;

import logic.controllers.ControllerUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelUserCreate extends JPanel {
    private JButton addUserButton;
    private JButton createUserButton;
    private JTextField fieldFirstName;
    private JTextField fieldLastName;
    private JLabel labelFirstName;
    private JLabel labelLastName;
    private Boolean waitingToCreateUser;
    private ControllerUsers userController;

    public PanelUserCreate() {
        addUserButton = new JButton("add user");
        createUserButton = new JButton("create user");
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

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewUser();
            }
        });
    }

    private void changeUI() {
        if (waitingToCreateUser) {
            this.removeAll();
            this.add(addUserButton);
        }
        else {
            this.removeAll();
            JPanel panel = new JPanel();
            GridLayout gridLayout = new GridLayout(2, 2);
            panel.setLayout(gridLayout);
            panel.add(labelFirstName);
            panel.add(fieldFirstName);
            panel.add(labelLastName);
            panel.add(fieldLastName);

            this.add(panel);
            this.add(createUserButton);
        }
    }

    private  void createNewUser() {
        String firstName = fieldFirstName.getText();
        String lastName = fieldLastName.getText();
        Boolean createUser = false;
        System.out.println(userController.doesUserExist(firstName, lastName));
        if (userController.doesUserExist(firstName, lastName)) {
            int answer = JOptionPane.showConfirmDialog(this, "This user already exits.\nDo you want to create it a again?");
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
        }
        changeUI();
    }
}
