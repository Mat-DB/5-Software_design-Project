package ticket;

import logic.users.User;
import logic.tickets.EventTicket;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TypeEvents;
import logic.tickets.TicketSplit.TicketEvenSplit;
import logic.tickets.TicketSplit.TypeSplit;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.*;

//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//// Run with PowerMock, an extended version of Mockito
//@RunWith(PowerMockRunner.class)
//// Prepare class RegistrationController for testing by injecting mocks
//@PrepareForTest(TicketAirplane.class)
public class TicketEvenSplit_UTest {
    public TicketEvenSplit_UTest()
    {
        // Intentionally left blank
    }

    User gones;

    int gonesHash;
    EventTicket airplaneTicket;
    TicketEvenSplit airplaneEvenSplitTicket;
    @Before
    public void initialize() {
        gones = new User("Gones", "Anseel", 1);
        gonesHash = (gones.getName() + gones.getID()).hashCode();
        airplaneTicket = new TicketAirplane("Airplane Prague", 100.0, gonesHash);
        airplaneEvenSplitTicket = new TicketEvenSplit(airplaneTicket);
    }
    @Test
    public void t_getName() {

        Assert.assertEquals("Testing name - input=Airplane Prague", "Airplane Prague", airplaneEvenSplitTicket.getName());
    }

    @Test
    public void t_whoPaid() {


        Assert.assertEquals("Testing user - input=Gones Anseel id 1", gonesHash, airplaneEvenSplitTicket.whoPaid());
    }

    @Test
    public void t_getTotal() {

        Assert.assertEquals("Testing total - input=100.0", 100.0, airplaneEvenSplitTicket.getTotal(), 0.1);
    }

    @Test
    public void t_getEvent() {
        Assert.assertEquals("Testing event - input=TypeEvents.AIRPLANE", TypeEvents.AIRPLANE, airplaneEvenSplitTicket.getEvent());
    }

    @Test
    public void t_getType() {
        Assert.assertEquals("Testing event - input=TypeSplit.EVEN_SPLIT", TypeSplit.EVEN_SPLIT, airplaneEvenSplitTicket.getSplitType());
    }

    @Test
    public void t_getEventTicket() {
        Assert.assertEquals("Testing event - input=airplaneTicket", airplaneTicket, airplaneEvenSplitTicket.getEventTicket());
    }

    @Test
    public void t_setInitialBalances() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);

        airplaneEvenSplitTicket.setInitialBalances(debtors);

        // 100EU split evenly amongst 2, gives each a debts of 50EU.
        // Gones paid the ticket, so his balance is 50EU positive,
        // while Matthias has a balance of -50EU because he is 50EU in debt.
        Assert.assertEquals("balance Gones = +50EU", 50.0, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);
        Assert.assertEquals("balance Matthias = -50EU", -50.0, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);

        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        debtors.add(stijnHash);
        airplaneEvenSplitTicket.setInitialBalances(debtors);

        // balances can only be initialized once. Adding new debtors is done via addDebtor()
        Assert.assertFalse("ticketBalances.find(stijn) = false", airplaneEvenSplitTicket.getBalances().containsKey(stijnHash));

        Assert.assertEquals("balance Gones = +50EU", 50.0, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);
        Assert.assertEquals("balance Matthias = -50EU", -50.0, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);

    }

    @Test
    public void t_addDebtor() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getID() + matthias.getName()).hashCode();
        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);

        airplaneEvenSplitTicket.setInitialBalances(debtors);

        // 100EU split evenly amongst 2, gives each a debts of 50EU.
        // Gones paid the ticket, so his balance is 50EU positive,
        // while Matthias has a balance of -50EU because he is 50EU in debt.
        Assert.assertEquals("balance Gones = +50EU", 50.0, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);
        Assert.assertEquals("balance Matthias = -50EU", -50.0, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);

        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        airplaneEvenSplitTicket.addDebtor(stijnHash);

        Assert.assertTrue("ticketBalances.find(stijn) = false", airplaneEvenSplitTicket.getBalances().containsKey(stijnHash));

        Assert.assertEquals("balance Gones = +66.67EU", 66.67, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);
        Assert.assertEquals("balance Matthias = -33.33EU", -33.33, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);
        Assert.assertEquals("balance Stijn = -33.33EU", -33.33, airplaneEvenSplitTicket.getBalances().get(stijnHash), .1);

    }

    @Test
    public void t_getDebtors() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);
        debtors.add(stijnHash);

        airplaneEvenSplitTicket.setInitialBalances(debtors);

        Set<Integer> assumedDebtors = airplaneEvenSplitTicket.getDebtors();

        Assert.assertEquals("Debtors", debtors, assumedDebtors);
    }

    @Test
    public void t_addPayment() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);
        debtors.add(stijnHash);

        airplaneEvenSplitTicket.setInitialBalances(debtors);

        airplaneEvenSplitTicket.addPayment(stijnHash, 33.33);
        Assert.assertEquals("balance stijn = 0.0", 0.0, airplaneEvenSplitTicket.getBalances().get(stijnHash), .1);
        Assert.assertEquals("balance Matthias = -33.33EU", -33.33, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);
        Assert.assertEquals("balance Gones = +33.33EU", 33.33, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);

        airplaneEvenSplitTicket.addPayment(matthiasHash, 33.33);
        Assert.assertEquals("balance stijn = 0.0", 0.0, airplaneEvenSplitTicket.getBalances().get(stijnHash), .1);
        Assert.assertEquals("balance Matthias = 0", 0.0, airplaneEvenSplitTicket.getBalances().get(matthiasHash), .1);
        Assert.assertEquals("balance Gones = 0", 0.0, airplaneEvenSplitTicket.getBalances().get(gonesHash), .1);

    }

    @Test
    public void t_isEven() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        Set<Integer> debtors = new HashSet<>();
        debtors.add(matthiasHash);
        debtors.add(stijnHash);

        airplaneEvenSplitTicket.setInitialBalances(debtors);

        airplaneEvenSplitTicket.addPayment(stijnHash, 33.33);
        Assert.assertFalse("balance = not even", airplaneEvenSplitTicket.isEven());

        airplaneEvenSplitTicket.addPayment(matthiasHash, 33.33);
        Assert.assertTrue("balance = even", airplaneEvenSplitTicket.isEven());
    }




}
