package biol.ipharm.controller;

import biol.ipharm.service.BasketService;
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
public class BasketController {

    private static final String BASKET_PAGE = "basket";
    private static final String PRODUCT_PAGE = "product";
    private static final String REDIRECT = "redirect:/";
    private static final String REDIRECT_PRODUCT_PAGE = REDIRECT + PRODUCT_PAGE;

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @RequestMapping("/show-basket")
    public String showBasketPage() {
        return BASKET_PAGE;
    }

    @RequestMapping(value = "/add-to-basket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam("productId") int productId, Model model) {
        basketService.addToBasket(productId);
        model.addAttribute("productId", productId);
        return REDIRECT_PRODUCT_PAGE;
    }

    @RequestMapping(value = "/add-to-basket", method = RequestMethod.GET)
    public String addToBasketGet(@RequestParam("productId") int productId, Model model) {
        model.addAttribute("productId", productId);
        return PRODUCT_PAGE;
    }

    @RequestMapping(value = "/remove-from-basket", method = RequestMethod.GET)
    public String removeFromBasket(@RequestParam("productIdToRemove") int productIdToRemove) {
        basketService.remove(productIdToRemove);
        return BASKET_PAGE;
    }

    @RequestMapping(value = "/update-quantity", method = RequestMethod.POST)
    public String updateQuantity(@RequestParam("productId") int productId,
            @RequestParam("quantity") int quantity) {
        basketService.updateQuantity(productId, quantity);
        return BASKET_PAGE;
    }
}
