package biol.ipharm.controller.admin;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.service.OrderingService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class OrderControllerAdminTest extends SampleBaseTestCase {

    private final TheOrder order = new TheOrder();
    private OrderControllerAdmin orderControllerAdmin;

    @Mock
    private OrderingService mockOrderingService;

    @Before
    public void setUp() {
        orderControllerAdmin = new OrderControllerAdmin(mockOrderingService);
    }

    @Test
    public void showAllOrders() {
        List<TheOrder> orderList = new ArrayList<>();
        when(mockOrderingService.getAllOrders()).thenReturn(orderList);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerAdmin.showAllOrders(model);

        assertTrue(model.containsAttribute("orderList"));
        assertEquals(orderList, model.asMap().get("orderList"));
        assertEquals("admin/order/order-list", actualViewName);
    }

    @Test
    public void showAdminOrderDetails() {
        int orderId = 5;
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerAdmin.showAdminOrderDetails(orderId, model);

        assertTrue(model.containsAttribute("order"));
        assertEquals(order, model.asMap().get("order"));
        assertEquals("admin/order/order-details", actualViewName);
    }

    @Test
    public void removeOrderItem() {
        int orderId = 5;
        int orderItemIdToRemove = 1;
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerAdmin.removeOrderItem(model, orderId, orderItemIdToRemove);

        verify(mockOrderingService).removeOrderItem(orderItemIdToRemove);
        assertTrue(model.containsAttribute("orderId"));
        assertEquals(orderId, model.asMap().get("orderId"));
        assertEquals("redirect:/admin/order/order-details", actualViewName);
    }

    @Test
    public void removeOrder() {
        int orderIdToRemove = 5;

        String actualViewName = orderControllerAdmin.removeOrder(orderIdToRemove);

        verify(mockOrderingService).removeOrder(orderIdToRemove);
        assertEquals("redirect:/admin/order/order-list", actualViewName);
    }

    @Test
    public void updateQuantity() {
        int orderItemId = 2;
        int orderId = 5;
        int quantity = 6;
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerAdmin.updateQuantity(model, orderItemId, orderId, quantity);

        verify(mockOrderingService).updateQuantity(orderItemId, quantity);
        assertTrue(model.containsAttribute("orderId"));
        assertEquals(orderId, model.asMap().get("orderId"));
        assertEquals("redirect:/admin/order/order-details", actualViewName);
    }
}
