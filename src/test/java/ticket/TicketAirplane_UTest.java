package ticket;

import logic.users.User;
import logic.tickets.TicketEvents.TicketAirplane;
import logic.tickets.TicketEvents.TypeEvents;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//// Run with PowerMock, an extended version of Mockito
//@RunWith(PowerMockRunner.class)
//// Prepare class RegistrationController for testing by injecting mocks
//@PrepareForTest(TicketAirplane.class)
public class TicketAirplane_UTest {
    public TicketAirplane_UTest()
    {
        // Intentionally left blank
    }

    User gones;
    int gonesHash;
    TicketAirplane airplaneTicket;
    @Before
    public void initialize() {
         gones = new User("Gones", "Anseel", 1);
         gonesHash = (gones.getName() + gones.getID()).hashCode();
         airplaneTicket = new TicketAirplane("Airplane Prague", 100.0, gonesHash);
    }
    @Test
    public void t_getName() {
        Assert.assertEquals("Testing name - input=Airplane Prague", "Airplane Prague", airplaneTicket.getName());
    }

    @Test
    public void t_whoPaid() {
        Assert.assertEquals("Testing user - input=Gones Anseel id 1", gonesHash, airplaneTicket.whoPaid());
    }

    @Test
    public void t_getTotal() {
        Assert.assertEquals("Testing total - input=100.0", 100.0, airplaneTicket.getTotal(), 0.1);
    }

    @Test
    public void t_getEvent() {
        Assert.assertEquals("Testing event - input=TypeEvents.AIRPLANE", TypeEvents.AIRPLANE, airplaneTicket.getEvent());
    }
}
