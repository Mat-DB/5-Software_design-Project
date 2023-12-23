package ticket;

import logic.controllers.ControllerUsers;
import logic.users.User;
import logic.tickets.TicketEvents.TicketCustom;
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
public class TicketCustom_UTest {
    public TicketCustom_UTest()
    {
        // Intentionally left blank
    }

    User gones;
    int gonesHash;
    TicketCustom customTicket;
    @Before
    public void initialize() {
        gones = new User("Gones", "Anseel", 1);
        gonesHash = ControllerUsers.getUserController().getUserHash(gones);
        customTicket = new TicketCustom("Custom Suit", 100.0, gonesHash);
    }
    @Test
    public void t_getName() {
        Assert.assertEquals("Testing name - input=Custom Suit", "Custom Suit", customTicket.getName());
    }

    @Test
    public void t_whoPaid() {
        Assert.assertEquals("Testing user - input=matthias", gonesHash, customTicket.getWhoPaid());
    }

    @Test
    public void t_getTotal() {

        Assert.assertEquals("Testing total - input=100.0", 100.0, customTicket.getTotal(), 0.1);
    }

    @Test
    public void t_getEvent() {
        Assert.assertEquals("Testing event - input=TypeEvents.Custom", TypeEvents.CUSTOM, customTicket.getEventType());
    }
}
