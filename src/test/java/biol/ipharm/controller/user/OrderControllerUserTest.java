package biol.ipharm.controller.user;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.entity.enums.PaymentMethod;
import biol.ipharm.entity.enums.ShippingMethod;
import biol.ipharm.form.PaymentMethodForm;
import biol.ipharm.form.ShippingAddressForm;
import biol.ipharm.form.ShippingMethodForm;
import biol.ipharm.order.result.OrderAcceptedMsg;
import biol.ipharm.order.result.OrderProcessingResult;
import biol.ipharm.service.OrderingService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author Olga
 */
public class OrderControllerUserTest extends SampleBaseTestCase {

    private OrderControllerUser orderControllerUser;
    private ShippingMethodForm shippingMethodForm;
    private ShippingAddressForm shippingAddressForm;
    private PaymentMethodForm paymentMethodForm;

    @Mock
    private OrderingService mockOrderingService;

    @Before
    public void setUp() {
        orderControllerUser = spy(new OrderControllerUser(mockOrderingService));
    }

    @Test
    public void showShippingMethodPage() {
        shippingMethodForm = new ShippingMethodForm();
        when(mockOrderingService.getShippingMethodForm()).thenReturn(shippingMethodForm);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.showShippingMethodPage(model);

        verify(orderControllerUser).setShippingMethodValues(model);
        assertTrue(model.containsAttribute("shippingMethodForm"));
        assertEquals(shippingMethodForm, model.asMap().get("shippingMethodForm"));
        assertEquals("user/shipping-method", actualViewName);
    }

    @Test
    public void setShippingMethodValues() {
        Model model = new ExtendedModelMap();
        
        orderControllerUser.setShippingMethodValues(model);

        assertTrue(model.containsAttribute("allShippingMethods"));
        assertArrayEquals(ShippingMethod.values(), (Object[]) model.asMap().get("allShippingMethods"));
    }

    @Test
    public void validateShippingMethodAndShowShippingAddressPage_BindingResultHasError_ReturnShippingMethodPage() {
        shippingMethodForm = new ShippingMethodForm();
        BindingResult resultWithError = new BeanPropertyBindingResult(shippingMethodForm, "shippingMethodForm");
        resultWithError.addError(new FieldError("shippingMethodForm", "shippingMethod", "shippingMethodErrorMessage"));
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.validateShippingMethodAndShowShippingAddressPage(model, shippingMethodForm, resultWithError);

        verify(orderControllerUser).setShippingMethodValues(model);
        assertEquals("user/shipping-method", actualViewName);
    }

    @Test
    public void validateShippingMethodAndShowShippingAddressPage_BindingResultHasNoError_ReturnShippingAddressPage() {
        shippingMethodForm = new ShippingMethodForm();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(shippingMethodForm, "shippingMethodForm");
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.validateShippingMethodAndShowShippingAddressPage(model, shippingMethodForm, resultWithoutError);

        verify(mockOrderingService).setShippingMethod(shippingMethodForm);
        assertEquals("redirect:/user/shipping-address", actualViewName);
    }

    @Test
    public void showShippingAddressPage() {
        shippingAddressForm = new ShippingAddressForm();
        when(mockOrderingService.getShippingAddressForm()).thenReturn(shippingAddressForm);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.showShippingAddressPage(model);

        assertTrue(model.containsAttribute("shippingAddressForm"));
        assertEquals(shippingAddressForm, model.asMap().get("shippingAddressForm"));
        assertEquals("user/shipping-address", actualViewName);
    }

    @Test
    public void validateShippingAddressAndShowPaymentMethodPage_BindingResultHasError_ReturnShippingAddressPage() {
        shippingAddressForm = new ShippingAddressForm();
        BindingResult resultWithError = new BeanPropertyBindingResult(shippingAddressForm, "shippingAddressForm");
        resultWithError.addError(new FieldError("shippingAddressForm", "shippingAddress", "shippingAddressErrorMessage"));

        String actualViewName = orderControllerUser.validateShippingAddressAndShowPaymentMethodPage(shippingAddressForm, resultWithError);

        assertEquals("user/shipping-address", actualViewName);
    }

    @Test
    public void validateShippingAddressAndShowPaymentMethodPage_BindingResultHasNoError_ReturnPaymentMethodPage() {
        shippingAddressForm = new ShippingAddressForm();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(shippingAddressForm, "shippingAddressForm");

        String actualViewName = orderControllerUser.validateShippingAddressAndShowPaymentMethodPage(shippingAddressForm, resultWithoutError);

        verify(mockOrderingService).setShippingAddressDetails(shippingAddressForm);
        assertEquals("redirect:/user/payment-method", actualViewName);
    }

    @Test
    public void showPaymentMethodPage() {
        paymentMethodForm = new PaymentMethodForm();
        when(mockOrderingService.getPaymentMethodForm()).thenReturn(paymentMethodForm);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.showPaymentMethodPage(model);

        verify(orderControllerUser).setPaymentMethodValues(model);
        assertTrue(model.containsAttribute("paymentMethodForm"));
        assertEquals(paymentMethodForm, model.asMap().get("paymentMethodForm"));
        assertEquals("user/payment-method", actualViewName);
    }

    @Test
    public void setPaymentMethodValues() {
        Model model = new ExtendedModelMap();
        
        orderControllerUser.setPaymentMethodValues(model);

        assertTrue(model.containsAttribute("allPaymentMethods"));
        assertArrayEquals(PaymentMethod.values(), (Object[]) model.asMap().get("allPaymentMethods"));
    }

    @Test
    public void validatePaymentMethodAndShowConfirmOrderPage_BindingResultHasError_ReturnPaymentMethodPage() {
        paymentMethodForm = new PaymentMethodForm();
        BindingResult resultWithError = new BeanPropertyBindingResult(paymentMethodForm, "paymentMethodForm");
        resultWithError.addError(new FieldError("paymentMethodForm", "paymentMethod", "paymentMethodErrorMessage"));
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.validatePaymentMethodAndShowConfirmOrderPage(model, paymentMethodForm, resultWithError);

        verify(orderControllerUser).setPaymentMethodValues(model);
        assertEquals("user/payment-method", actualViewName);
    }

    @Test
    public void validatePaymentMethodAndShowConfirmOrderPage_BindingResultHasNoError_ReturnPaymentAddressPage() {
        paymentMethodForm = new PaymentMethodForm();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(paymentMethodForm, "paymentMethodForm");
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.validatePaymentMethodAndShowConfirmOrderPage(model, paymentMethodForm, resultWithoutError);

        verify(mockOrderingService).setPaymentMethod(paymentMethodForm);
        assertEquals("redirect:/user/confirm-order", actualViewName);
    }

    @Test
    public void showConfirmOrderPage() {
        String actualViewName = orderControllerUser.showConfirmOrderPage();

        assertEquals("user/confirm-order", actualViewName);
    }

    @Test
    public void showOrderProcessingResultPage() {
        OrderProcessingResult orderProcessingResult = new OrderAcceptedMsg("Ivan", "mne@pisem.net");
        when(mockOrderingService.acceptOrder()).thenReturn(orderProcessingResult);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.showOrderProcessingResultPage(model);

        assertTrue(model.containsAttribute("orderProcessingResult"));
        assertEquals(orderProcessingResult, model.asMap().get("orderProcessingResult"));
        assertEquals("user/order-processing-result", actualViewName);
    }

    @Test
    public void showOrderItemDetailsPage() {
        int orderId = 5;
        TheOrder order = new TheOrder();
        when(mockOrderingService.getOrderById(orderId)).thenReturn(order);
        Model model = new ExtendedModelMap();

        String actualViewName = orderControllerUser.showOrderItemDetailsPage(orderId, model);

        assertTrue(model.containsAttribute("order"));
        assertEquals(order, model.asMap().get("order"));
        assertEquals("user/order-item-details", actualViewName);
    }
}
