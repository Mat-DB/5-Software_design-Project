package graphics.panels.subPanels;

import graphics.panels.PanelTickets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTicketCreateConfirm extends JPanel {
    private JButton confirmButton;
    private JButton cancelButton;
    private final PanelTickets panelTickets;

    public PanelTicketCreateConfirm(PanelTickets panelTickets) {
        this.panelTickets = panelTickets;
    }

    public void init() {
        this.removeAll();
        this.updateUI();

        String title = "Overview of the ticket. Check if everything is correct.";
        PanelTicketView ticketView = new PanelTicketView(title, panelTickets.getTicketName());

        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        createListeners();

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.add(ticketView);

        JPanel buttonPanel = new JPanel();
        BoxLayout boxLayout2 = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxLayout2);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        this.add(buttonPanel);
    }

    private void createListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTickets.confirmTicket();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelTickets.setStartState();
            }
        });
    }
}
