package biol.ipharm.controller.admin;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class UserControllerAdminTest extends SampleBaseTestCase {

    private UserControllerAdmin userControllerAdmin;

    @Mock
    private CustomerService mockCustomerService;
    @Mock
    private OrderingService mockOrderingService;

    @Before
    public void setUp() {
        userControllerAdmin = spy(new UserControllerAdmin(mockCustomerService, mockOrderingService));
    }

    @Test
    public void showAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        when(mockCustomerService.getAllCustomers()).thenReturn(customerList);
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.showAllCustomer(model);

        assertTrue(model.containsAttribute("customerList"));
        assertEquals(customerList, model.asMap().get("customerList"));
        assertEquals("admin/user/user-list", actualViewName);
    }

    @Test
    public void showUserOrderList() {
        int userIdToGetOrderList = 6;
        Customer customer = new Customer();
        when(mockCustomerService.findCustomerById(userIdToGetOrderList)).thenReturn(customer);
        List<TheOrder> orderList = new ArrayList<>();
        when(mockOrderingService.getOrdersForCustomer(customer)).thenReturn(orderList);
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.showUserOrderList(userIdToGetOrderList, model);

        assertTrue(model.containsAttribute("customer"));
        assertEquals(customer, model.asMap().get("customer"));
        assertTrue(model.containsAttribute("orderList"));
        assertEquals(orderList, model.asMap().get("orderList"));
        assertEquals("admin/user/user-order-list", actualViewName);
    }

    @Test
    public void showUserOrderDeails() {
        int orderId = 4;
        TheOrder order = new TheOrder();
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.showUserOrderDeails(orderId, model);

        assertTrue(model.containsAttribute("order"));
        assertEquals(order, model.asMap().get("order"));
        assertEquals("admin/user/user-order-details", actualViewName);
    }

    @Test
    public void removeOrderItem() {
        int orderId = 5;
        int orderItemIdToRemove = 1;
        TheOrder order = new TheOrder();
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.removeOrderItem(model, orderId, orderItemIdToRemove);

        verify(mockOrderingService).removeOrderItem(orderItemIdToRemove);
        assertTrue(model.containsAttribute("orderId"));
        assertEquals(orderId, model.asMap().get("orderId"));
        assertEquals("redirect:/admin/user/user-order-details", actualViewName);
    }

    @Test
    public void removeOrder() {
        int userIdToGetOrderList = 6;
        int orderIdToRemove = 5;
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.removeOrder(userIdToGetOrderList, orderIdToRemove, model);

        verify(mockOrderingService).removeOrder(orderIdToRemove);
        assertTrue(model.containsAttribute("userIdToGetOrderList"));
        assertEquals(userIdToGetOrderList, model.asMap().get("userIdToGetOrderList"));
        assertEquals("redirect:/admin/user/user-order-list", actualViewName);
    }

    @Test
    public void updateQuantity() {
        int orderItemId = 2;
        int orderId = 5;
        int quantity = 6;
        TheOrder order = new TheOrder();
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = userControllerAdmin.updateQuantity(model, orderItemId, orderId, quantity);

        verify(mockOrderingService).updateQuantity(orderItemId, quantity);
        assertTrue(model.containsAttribute("orderId"));
        assertEquals(orderId, model.asMap().get("orderId"));
        assertEquals("redirect:/admin/user/user-order-details", actualViewName);
    }
}
