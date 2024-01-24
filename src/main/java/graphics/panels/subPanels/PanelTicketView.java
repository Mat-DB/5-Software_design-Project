package graphics.panels.subPanels;

import logic.controllers.ControllerTickets;
import logic.controllers.ControllerUsers;
import logic.tickets.Ticket;
import logic.users.User;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class PanelTicketView extends JPanel {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final JLabel overviewLabel;
    private HashMap<Integer, ArrayList<JLabel>> debtorLabels;
    private final Ticket ticket;
    private final ControllerUsers usersController;
    private final ControllerTickets ticketController;

    public PanelTicketView(String title, String ticketName) {
        overviewLabel = new JLabel(title);
        usersController = ControllerUsers.getUserController();
        ticketController = ControllerTickets.getTicketController();
        ticket = ticketController.getTicket(ticketName);
        logger.info("\n\nPanelTicketView, TEST POINT\n\n");
//        System.out.println("Ticket name: '" + ticketName + "'");
//        System.out.println("PanelTicketView, ticket.getName(): " + ticket.getName());
//        System.out.println("TEST POINT 2");
        init();
    }

    private void init() {
        // https://java2blog.com/format-double-to-2-decimal-places-java/
        DecimalFormat df = new DecimalFormat("#.##");

        User owner = usersController.getUser(ticket.getWhoPaid());
        String nameL   = "Ticket name:     " + ticket.getName();
        String ownerL  = "Person who paid: " + owner.getName();
        String eventL  = "Event type:      " + ticket.getEventType();
        String splitL  = "Split type:      " + ticket.getSplitType();
        String amountL = "Total amount:    " + df.format(ticket.getTotal()) + " euro";

        logger.info(nameL + ownerL + eventL + splitL + amountL);

        JLabel nameLabel = new JLabel(nameL);
        JLabel ownerLabel = new JLabel(ownerL);
        JLabel eventTypeLabel = new JLabel(eventL);
        JLabel splitTypeLabel = new JLabel(splitL);
        JLabel totalAmountLabel = new JLabel(amountL);

        debtorLabels = new HashMap<>();
        int debtorLabelNum = 1;

        ArrayList<JLabel> debtorSetFirst = new ArrayList<>();
        debtorSetFirst.add(new JLabel("Debtor"));
        debtorSetFirst.add(new JLabel("Debt"));
        debtorLabels.put(debtorLabelNum, debtorSetFirst);

        HashMap<Integer, Double> balance = ticket.getBalances();
        logger.info("balance: " + balance);
        for (int debtorHash : ticket.getDebtors()) {
            debtorLabelNum += 1;
            ArrayList<JLabel> debtorSet = new ArrayList<>();
            debtorSet.add(new JLabel(usersController.getUser(debtorHash).getName()));
            logger.info("balance.get(debtorHash).getClass(): " + balance.get(debtorHash).getClass());
            logger.info("balance.get(debtorHash): " + balance.get(debtorHash));
            debtorSet.add(new JLabel(df.format(balance.get(debtorHash)) + " euro"));
            debtorLabels.put(debtorLabelNum, debtorSet);
        }

        // Put everything on the panel
        // https://stackoverflow.com/questions/4135680/vertical-align-of-gridbaglayout-panel-on-borderlayout-center
        // https://docs.oracle.com/javase/tutorial//uiswing/layout/gridbag.html
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        //gbc.fill = GridBagConstraints.BOTH;
        this.add(overviewLabel, gbc);
        this.add(new JLabel(" "), gbc);
        this.add(nameLabel, gbc);
        this.add(ownerLabel, gbc);
        this.add(eventTypeLabel, gbc);
        this.add(splitTypeLabel, gbc);
        this.add(totalAmountLabel, gbc);
        this.add(new JLabel(" "), gbc);

        JPanel debtorsPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(debtorLabels.size(), 2);
        debtorsPanel.setLayout(gridLayout);
        for (int i : debtorLabels.keySet()) {
            for (JLabel label : debtorLabels.get(i)) {
                debtorsPanel.add(label);
            }
        }
        this.add(debtorsPanel, gbc);
    }
}
