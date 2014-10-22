package biol.ipharm.controller.user;

import biol.ipharm.entity.TheOrder;
import biol.ipharm.entity.enums.PaymentMethod;
import biol.ipharm.entity.enums.ShippingMethod;
import biol.ipharm.form.PaymentMethodForm;
import biol.ipharm.form.ShippingAddressForm;
import biol.ipharm.form.ShippingMethodForm;
import biol.ipharm.service.OrderingService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class OrderControllerUser {

    private static final String REDIRECT = "redirect:/";
    private static final String USER__SHIPPING_METHOD_PAGE = "user/shipping-method";
    private static final String USER__SHIPPING_ADDRESS_PAGE = "user/shipping-address";
    private static final String USER__REDIRECT_SHIPPING_ADDRESS_PAGE = REDIRECT + USER__SHIPPING_ADDRESS_PAGE;
    private static final String USER__PAYMENT_METHOD_PAGE = "user/payment-method";
    private static final String USER__REDIRECT_PAYMENT_METHOD_PAGE = REDIRECT + USER__PAYMENT_METHOD_PAGE;
    private static final String USER__CONFIRM_ORDER_PAGE = "user/confirm-order";
    private static final String USER__REDIRECT_CONFIRM_ORDER_PAGE = REDIRECT + USER__CONFIRM_ORDER_PAGE;

    private final OrderingService orderingService;

    @Autowired
    public OrderControllerUser(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    // Shipping method part - 1st step
    @RequestMapping(value = "/user/shipping-method", method = RequestMethod.GET)
    public String showShippingMethodPage(Model model) {
        setShippingMethodValues(model);
        model.addAttribute("shippingMethodForm", orderingService.getShippingMethodForm());
        return USER__SHIPPING_METHOD_PAGE;
    }

    void setShippingMethodValues(Model model) {
        model.addAttribute("allShippingMethods", ShippingMethod.values());
    }

    @RequestMapping(value = "/user/shipping-address", method = RequestMethod.POST)
    public String validateShippingMethodAndShowShippingAddressPage(Model model,
            @Valid final ShippingMethodForm shippingMethodForm, final BindingResult result) {

        if (result.hasErrors()) {
            setShippingMethodValues(model);
            return USER__SHIPPING_METHOD_PAGE;
        }

        orderingService.setShippingMethod(shippingMethodForm);
        return USER__REDIRECT_SHIPPING_ADDRESS_PAGE;
    }

    // Shipping address part - 2nd step
    @RequestMapping(value = "/user/shipping-address", method = RequestMethod.GET)
    public String showShippingAddressPage(Model model) {
        model.addAttribute("shippingAddressForm", orderingService.getShippingAddressForm());
        return USER__SHIPPING_ADDRESS_PAGE;
    }

    @RequestMapping(value = "/user/payment-method", method = RequestMethod.POST)
    public String validateShippingAddressAndShowPaymentMethodPage(@Valid final ShippingAddressForm shippingAddressForm,
            final BindingResult result) {
        if (result.hasErrors()) {
            return USER__SHIPPING_ADDRESS_PAGE;
        }

        orderingService.setShippingAddressDetails(shippingAddressForm);
        return USER__REDIRECT_PAYMENT_METHOD_PAGE;
    }

    // Payment method part - 3rd step
    @RequestMapping(value = "/user/payment-method", method = RequestMethod.GET)
    public String showPaymentMethodPage(Model model) {
        setPaymentMethodValues(model);
        model.addAttribute("paymentMethodForm", orderingService.getPaymentMethodForm());
        return USER__PAYMENT_METHOD_PAGE;
    }

    void setPaymentMethodValues(Model model) {
        model.addAttribute("allPaymentMethods", PaymentMethod.values());
    }

    @RequestMapping(value = "/user/confirm-order", method = RequestMethod.POST)
    public String validatePaymentMethodAndShowConfirmOrderPage(Model model,
            @Valid final PaymentMethodForm paymentMethodForm, final BindingResult result) {

        if (result.hasErrors()) {
            setPaymentMethodValues(model);
            return USER__PAYMENT_METHOD_PAGE;
        }

        orderingService.setPaymentMethod(paymentMethodForm);
        return USER__REDIRECT_CONFIRM_ORDER_PAGE;
    }

    // Confirm order part - 4th step
    @RequestMapping(value = "/user/confirm-order", method = RequestMethod.GET)
    public String showConfirmOrderPage() {
        return USER__CONFIRM_ORDER_PAGE;
    }

    @RequestMapping(value = "/user/accept-order", method = RequestMethod.GET)
    public String showOrderProcessingResultPage(Model model) {
        model.addAttribute("orderProcessingResult", orderingService.acceptOrder());
        return "user/order-processing-result";
    }

    @RequestMapping("/user/order-item-details")
    public String showOrderItemDetailsPage(@RequestParam("orderId") int orderId, Model model) {
        TheOrder order = orderingService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "user/order-item-details";
    }
}
