package biol.ipharm.controller;

import biol.ipharm.entity.Customer;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.service.CustomerService;
import biol.ipharm.service.OrderingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Olga
 */
@Controller
public class SecurityController {

    private static final String LOGIN_PAGE = "login";

    private final OrderingService orderingService;
    private final CustomerService customerService;

    @Autowired
    public SecurityController(OrderingService orderingService, CustomerService customerService) {
        this.orderingService = orderingService;
        this.customerService = customerService;
    }

    @RequestMapping("/user/index")
    public String showUserIndexPage(Model model) {
        Customer customer = customerService.getAuthorizedUser();
        List<TheOrder> orderList = orderingService.getOrdersForCustomer(customer);
        model.addAttribute("orderList", orderList);
        return "user/index";
    }

    @RequestMapping("/admin/index")
    public String showAdminIndexPage() {
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login() {
        return LOGIN_PAGE;
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return LOGIN_PAGE;
    }
}
