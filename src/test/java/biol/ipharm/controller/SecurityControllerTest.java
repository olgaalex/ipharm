package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.Customer;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.service.CustomerService;
import biol.ipharm.service.OrderingService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class SecurityControllerTest extends SampleBaseTestCase {

    private SecurityController securityController;

    @Mock
    private OrderingService mockOrderingService;
    @Mock
    private CustomerService mockCustomerService;

    @Before
    public void setUp() {
        securityController = new SecurityController(mockOrderingService, mockCustomerService);
    }

    @Test
    public void showUserIndexPage() {
        Customer customer = new Customer();
        when(mockCustomerService.getAuthorizedUser()).thenReturn(customer);
        List<TheOrder> orderList = new ArrayList<>();
        when(mockOrderingService.getOrdersForCustomer(customer)).thenReturn(orderList);
        Model model = new ExtendedModelMap();

        String actualViewName = securityController.showUserIndexPage(model);

        assertTrue(model.containsAttribute("orderList"));
        assertEquals(orderList, model.asMap().get("orderList"));
        assertEquals("user/index", actualViewName);
    }

    @Test
    public void showAdminIndexPage() {
        assertEquals("admin/index", securityController.showAdminIndexPage());
    }

    @Test
    public void login() {
        assertEquals("login", securityController.login());
    }

    @Test
    public void loginError() {
        Model model = new ExtendedModelMap();

        String actualViewName = securityController.loginError(model);

        assertTrue(model.containsAttribute("loginError"));
        assertTrue((boolean) model.asMap().get("loginError"));
        assertEquals("login", actualViewName);

    }
}
