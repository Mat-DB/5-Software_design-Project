package ticket;

import logic.tickets.TicketSplit.TicketUnevenSplit;
import logic.users.User;
import logic.tickets.EventTicket;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TypeEvents;
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
public class TicketUnevenSplit_UTest {
    public TicketUnevenSplit_UTest() {
        // Intentionally left blank
    }

    User gones;
    int gonesHash;
    EventTicket airplaneTicket;
    TicketUnevenSplit airplaneUnevenSplit;

    @Before
    public void initialize() {
        gones = new User("Gones", "Anseel", 1);
        gonesHash = (gones.getName() + gones.getID()).hashCode();
        airplaneTicket = new TicketAirplane("Airplane Prague", 100.0, gonesHash);
        airplaneUnevenSplit = new TicketUnevenSplit(airplaneTicket);
    }

    @Test
    public void t_getName() {

        Assert.assertEquals("Testing name - input=Airplane Prague", "Airplane Prague", airplaneUnevenSplit.getName());
    }

    @Test
    public void t_whoPaid() {


        Assert.assertEquals("Testing user - input=Gones Anseel id 1", gonesHash, airplaneUnevenSplit.whoPaid());
    }

    @Test
    public void t_getTotal() {

        Assert.assertEquals("Testing total - input=100.0", 100.0, airplaneUnevenSplit.getTotal(), 0.1);
    }

    @Test
    public void t_getEvent() {
        Assert.assertEquals("Testing event - input=TypeEvents.AIRPLANE", TypeEvents.AIRPLANE, airplaneUnevenSplit.getEvent());
    }

    @Test
    public void t_getType() {
        Assert.assertEquals("Testing event - input=TypeSplit.EVEN_SPLIT", TypeSplit.UNEVEN_SPLIT, airplaneUnevenSplit.getSplitType());
    }

    @Test
    public void t_getEventTicket() {
        Assert.assertEquals("Testing event - input=airplaneTicket", airplaneTicket, airplaneUnevenSplit.getEventTicket());
    }

    @Test
    public void t_setInitialBalances() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        HashMap<Integer, Double> debtors = new HashMap<>();
        debtors.put(gonesHash, 40.0);
        debtors.put(matthiasHash, 60.0);

        airplaneUnevenSplit.setInitialBalances(debtors);

        // Gones paid the ticket, but has debt of 40 so his balance is 60EU positive,
        // while Matthias has a balance of -60EU because he is 60EU in debt.
        Assert.assertEquals("balance Gones = +50EU", 60.0, airplaneUnevenSplit.getBalances().get(gonesHash), .1);
        Assert.assertEquals("balance Matthias = -50EU", -60.0, airplaneUnevenSplit.getBalances().get(matthiasHash), .1);

    }

    @Test
    public void t_getDebtors() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        HashMap<Integer, Double> debtors = new HashMap<>();
        debtors.put(gonesHash, 30.0);
        debtors.put(matthiasHash, 40.0);
        debtors.put(stijnHash, 30.0);

        airplaneUnevenSplit.setInitialBalances(debtors);

        // debtors returned by ticket
        Set<Integer> actualDebtors = airplaneUnevenSplit.getDebtors();

        //
        Set<Integer> expectedDebtors = debtors.keySet();
        expectedDebtors.remove(airplaneUnevenSplit.whoPaid());

        Assert.assertEquals("Debtors", expectedDebtors, actualDebtors);
    }

    @Test
    public void t_addPayment() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        HashMap<Integer, Double> debtors = new HashMap<>();
        debtors.put(gonesHash, 30.0);
        debtors.put(matthiasHash, 40.0);
        debtors.put(stijnHash, 30.0);

        airplaneUnevenSplit.setInitialBalances(debtors);

        airplaneUnevenSplit.addPayment(stijnHash, 30.0);
        Assert.assertEquals("balance stijn = 0.0", 0.0, airplaneUnevenSplit.getBalances().get(stijnHash), .1);
        Assert.assertEquals("balance Matthias = -33.33EU", -40.0, airplaneUnevenSplit.getBalances().get(matthiasHash), .1);
        Assert.assertEquals("balance Gones = +33.33EU", 40.0, airplaneUnevenSplit.getBalances().get(gonesHash), .1);

        airplaneUnevenSplit.addPayment(matthiasHash, 40.0);
        Assert.assertEquals("balance stijn = 0.0", 0.0, airplaneUnevenSplit.getBalances().get(stijnHash), .1);
        Assert.assertEquals("balance Matthias = 0", 0.0, airplaneUnevenSplit.getBalances().get(matthiasHash), .1);
        Assert.assertEquals("balance Gones = 0", 0.0, airplaneUnevenSplit.getBalances().get(gonesHash), .1);

    }

    @Test
    public void t_isEven() {
        User matthias = new User("Matthias", "De Beukelaer", 2);
        int matthiasHash = (matthias.getName() + matthias.getID()).hashCode();
        User stijn = new User("Stijn", "VR", 3);
        int stijnHash = (stijn.getName() + stijn.getID()).hashCode();
        HashMap<Integer, Double> debtors = new HashMap<>();
        debtors.put(gonesHash, 30.0);
        debtors.put(matthiasHash, 40.0);
        debtors.put(stijnHash, 30.0);

        airplaneUnevenSplit.setInitialBalances(debtors);

        airplaneUnevenSplit.addPayment(stijnHash, 30.0);
        Assert.assertFalse("balance = not even", airplaneUnevenSplit.isEven());

        airplaneUnevenSplit.addPayment(matthiasHash, 40.0);
        Assert.assertTrue("balance = even", airplaneUnevenSplit.isEven());
    }

    // addDebtor() in an unevensplit ticket??
    @Test
    public void t_addDebtor() {
//        User matthias = new User("Matthias", "De Beukelaer", 2);
//        Set<User> debtors = new HashSet<>();
//        debtors.add(matthias);
//
//        //airplaneUnevenSplit.setInitialBalances(debtors);
//
//        // 100EU split evenly amongst 2, gives each a debts of 50EU.
//        // Gones paid the ticket, so his balance is 50EU positive,
//        // while Matthias has a balance of -50EU because he is 50EU in debt.
//        Assert.assertEquals("balance Gones = +50EU", 50.0, airplaneUnevenSplit.getBalances().get(gones), .1);
//        Assert.assertEquals("balance Matthias = -50EU", -50.0, airplaneUnevenSplit.getBalances().get(matthias), .1);
//
//        User stijn = new User("Stijn", "VR", 3);
//        //airplaneUnevenSplit.addDebtor(stijn);
//
//        Assert.assertTrue("ticketBalances.find(stijn) = false", airplaneUnevenSplit.getBalances().containsKey(stijn));
//
//        Assert.assertEquals("balance Gones = +66.67EU", 66.67, airplaneUnevenSplit.getBalances().get(gones), .1);
//        Assert.assertEquals("balance Matthias = -33.33EU", -33.33, airplaneUnevenSplit.getBalances().get(matthias), .1);
//        Assert.assertEquals("balance Stijn = -33.33EU", -33.33, airplaneUnevenSplit.getBalances().get(stijn), .1);

    }
}
