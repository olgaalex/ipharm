package biol.ipharm.controller.admin;

import biol.ipharm.entity.TheOrder;
import biol.ipharm.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class OrderControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__ORDER_LIST_PAGE = "admin/order/order-list";
    private static final String ADMIN__REDIRECT_ORDER_LIST_PAGE = REDIRECT + ADMIN__ORDER_LIST_PAGE;
    private static final String ADMIN__ORDER_DETAILS_PAGE = "admin/order/order-details";
    private static final String ADMIN__REDIRECT_ORDER_DETAILS_PAGE = REDIRECT + ADMIN__ORDER_DETAILS_PAGE;

    private final OrderingService orderingService;

    @Autowired
    public OrderControllerAdmin(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @RequestMapping(value = "/admin/order/order-list", method = RequestMethod.GET)
    public String showAllOrders(Model model) {
        model.addAttribute("orderList", orderingService.getAllOrders());
        return ADMIN__ORDER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/order/order-details", method = RequestMethod.GET)
    public String showAdminOrderDetails(@RequestParam("orderId") int orderId, Model model) {
        TheOrder order = orderingService.getOrderById(orderId);
        model.addAttribute("order", order);
        return ADMIN__ORDER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/admin/order/remove-order-item", method = RequestMethod.GET)
    public String removeOrderItem(Model model, @RequestParam("orderId") int orderId,
            @RequestParam("orderItemIdToRemove") int orderItemIdToRemove) {

        orderingService.removeOrderItem(orderItemIdToRemove);
        model.addAttribute("orderId", orderId);
        return ADMIN__REDIRECT_ORDER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/admin/order/remove-order", method = RequestMethod.GET)
    public String removeOrder(@RequestParam("orderIdToRemove") int orderIdToRemove) {
        orderingService.removeOrder(orderIdToRemove);
        return ADMIN__REDIRECT_ORDER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/order/update-order-item-quantity", method = RequestMethod.POST)
    public String updateQuantity(Model model,
            @RequestParam("orderItemId") int orderItemId,
            @RequestParam("orderId") int orderId,
            @RequestParam("quantity") int quantity) {

        orderingService.updateQuantity(orderItemId, quantity);
        model.addAttribute("orderId", orderId);
        return ADMIN__REDIRECT_ORDER_DETAILS_PAGE;
    }
}
