package biol.ipharm.controller.admin;

import biol.ipharm.entity.Customer;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.service.CustomerService;
import biol.ipharm.service.OrderingService;
import java.util.List;
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
public class UserControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__USER_ORDER_LIST_PAGE = "admin/user/user-order-list";
    private static final String ADMIN__REDIRECT_USER_ORDER_LIST_PAGE = REDIRECT + ADMIN__USER_ORDER_LIST_PAGE;
    private static final String ADMIN__USER_ORDER_DETAILS_PAGE = "admin/user/user-order-details";
    private static final String ADMIN__REDIRECT_USER_ORDER_DETAILS_PAGE = REDIRECT + ADMIN__USER_ORDER_DETAILS_PAGE;

    private final CustomerService customerService;
    private final OrderingService orderingService;

    @Autowired
    public UserControllerAdmin(CustomerService customerService, OrderingService orderingService) {
        this.customerService = customerService;
        this.orderingService = orderingService;
    }

    @RequestMapping(value = "/admin/user/user-list", method = RequestMethod.GET)
    public String showAllCustomer(Model model) {
        model.addAttribute("customerList", customerService.getAllCustomers());
        return "admin/user/user-list";
    }

    @RequestMapping(value = "/admin/user/user-order-list", method = RequestMethod.GET)
    public String showUserOrderList(@RequestParam("userIdToGetOrderList") int userIdToGetOrderList, Model model) {
        Customer customer = customerService.findCustomerById(userIdToGetOrderList);
        model.addAttribute("customer", customer);
        List<TheOrder> orderList = orderingService.getOrdersForCustomer(customer);
        model.addAttribute("orderList", orderList);
        return ADMIN__USER_ORDER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/user/user-order-details", method = RequestMethod.GET)
    public String showUserOrderDeails(@RequestParam("orderId") int orderId, Model model) {
        TheOrder order = orderingService.getOrderById(orderId);
        model.addAttribute("order", order);
        return ADMIN__USER_ORDER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/admin/user/remove-user-order-item", method = RequestMethod.GET)
    public String removeOrderItem(Model model, @RequestParam("orderId") int orderId,
            @RequestParam("orderItemIdToRemove") int orderItemIdToRemove) {

        orderingService.removeOrderItem(orderItemIdToRemove);
        model.addAttribute("orderId", orderId);
        return ADMIN__REDIRECT_USER_ORDER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/admin/user/remove-user-order", method = RequestMethod.GET)
    public String removeOrder(@RequestParam("userIdToGetOrderList") int userIdToGetOrderList,
            @RequestParam("orderIdToRemove") int orderIdToRemove, Model model) {

        orderingService.removeOrder(orderIdToRemove);
        model.addAttribute("userIdToGetOrderList", userIdToGetOrderList);
        return ADMIN__REDIRECT_USER_ORDER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/user/update-user-order-item-quantity", method = RequestMethod.POST)
    public String updateQuantity(Model model,
            @RequestParam("orderItemId") int orderItemId,
            @RequestParam("orderId") int orderId,
            @RequestParam("quantity") int quantity) {

        orderingService.updateQuantity(orderItemId, quantity);
        model.addAttribute("orderId", orderId);
        return ADMIN__REDIRECT_USER_ORDER_DETAILS_PAGE;
    }
}
