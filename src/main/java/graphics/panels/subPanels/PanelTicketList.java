package graphics.panels.subPanels;

import graphics.ViewFrame;
import graphics.panels.PanelTickets;
import logic.controllers.ControllerTickets;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTicketList extends JPanel {
    private JList<String> ticketJList;
    private JButton removeButton;
    private int selectedTicket;
    private ControllerTickets ticketsController;
    private ViewFrame frame;
    private PanelTickets panelTickets;

    public PanelTicketList(ViewFrame frame, PanelTickets panelTickets) {
        this.frame = frame;
        this.panelTickets = panelTickets;
        ticketsController = ControllerTickets.getTicketController();

        ticketJList = new JList<>(frame.getTicketList());
        ticketJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        removeButton = new JButton("Remove selected ticket");
        removeButton.setEnabled(false);

        createListeners();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(ticketJList);
        this.add(removeButton);
    }

    private void createListeners() {
        ticketJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (ticketJList.getSelectedIndex() == -1) {
                        //No selection, disable remove button.
                        removeButton.setEnabled(false);
                        panelTickets.clearSelectedTicketName();
                    }
                    else {
                        //Selection, enable the remove button.
                        removeButton.setEnabled(true);
                        selectedTicket = ticketsController.getTicketHash(ticketJList.getSelectedValue());
                        panelTickets.setSelectedTicketName(ticketJList.getSelectedValue());
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketsController.removeTicket(selectedTicket);
            }
        });
    }
}
