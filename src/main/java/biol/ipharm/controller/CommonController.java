package biol.ipharm.controller;

import biol.ipharm.service.IndexPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Olga
 */
@Controller
public class CommonController {

    private final IndexPageService indexPageService;

    @Autowired
    public CommonController(IndexPageService indexPageService) {
        this.indexPageService = indexPageService;
    }

    @RequestMapping({"/", "/index"})
    public String showIndexPage(Model model) {
        model.addAttribute("productGroupAndSubgroupMap", indexPageService.getMapGroupedByProductGroupName());
        return "index";
    }

    @RequestMapping("/about")
    public String showAboutProjectPage() {
        return "about";
    }

    @RequestMapping("/shipping-and-payment")
    public String showShippingAndPaymentPage() {
        return "shipping-and-payment";
    }
}
