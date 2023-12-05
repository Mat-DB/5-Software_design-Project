package graphics;

//import database.RegistrationDB;
//import observers.Observer;
//import observers.RegistrationObserver;
//import view.panels.ListPanel;
//import view.panels.RegistrationButtonPanel;
//import view.panels.RegistrationInfoLabel;

import javax.swing.*;
import java.awt.*;

public class ViewFrame extends JFrame
{
    // Get your controller in this private field
//    ListPanel panel;
//    RegistrationButtonPanel buttons;
//    Observer registrationObserver;
//    RegistrationInfoLabel registrationInfoLabel;

    public ViewFrame()
    {
        super("MoneyTrackerApp");
    }

    public void initialize()
    {
        this.setSize(500, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();

//        registrationObserver = new RegistrationObserver(RegistrationDB.getInstance());
//
//        // Pass the controller to the ButtonPanel
//
//        buttons = new RegistrationButtonPanel();
//        panel = new ListPanel();
//
//        c.gridy = 0;
//        this.add(panel, c);
//        c.gridy = 0;
//        this.add(buttons, c);
//
//        registrationInfoLabel = new RegistrationInfoLabel(RegistrationDB.getInstance());
//        c.gridy = 2;
//        c.gridx = 1;
//        c.anchor = GridBagConstraints.PAGE_END;
//        this.add(registrationInfoLabel, c);
        this.setVisible(true);
    }

    public void homescreen() {

    }
}
