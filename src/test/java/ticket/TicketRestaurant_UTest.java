package ticket;

import logic.controllers.ControllerUsers;
import logic.users.User;
import logic.tickets.TicketEvents.TicketRestaurant;
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
public class TicketRestaurant_UTest {
    public TicketRestaurant_UTest()
    {
        // Intentionally left blank
    }

    User matthias;

    int matthiasHash;
    TicketRestaurant restaurantTicket;
    @Before
    public void initialize() {
        matthias = new User("Matthias", "De Beukelaer", 1);
        matthiasHash = ControllerUsers.getUserController().getUserHash(matthias);
        restaurantTicket = new TicketRestaurant("Restaurant B-day", 100.0, matthiasHash);
    }
    @Test
    public void t_getName() {

        Assert.assertEquals("Testing name - input=Restaurant B-day", "Restaurant B-day", restaurantTicket.getName());
    }

    @Test
    public void t_whoPaid() {
        Assert.assertEquals("Testing user - input=matthias", matthiasHash, restaurantTicket.getWhoPaid());
    }

    @Test
    public void t_getTotal() {

        Assert.assertEquals("Testing total - input=100.0", 100.0, restaurantTicket.getTotal(), 0.1);
    }

    @Test
    public void t_getEvent() {
        Assert.assertEquals("Testing event - input=TypeEvents.Restaurant", TypeEvents.RESTAURANT, restaurantTicket.getEventType());
    }
}