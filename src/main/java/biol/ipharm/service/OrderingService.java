package biol.ipharm.service;

import biol.ipharm.entity.Customer;
import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.form.PaymentMethodForm;
import biol.ipharm.form.ShippingAddressForm;
import biol.ipharm.form.ShippingMethodForm;
import biol.ipharm.order.result.OrderProcessingResult;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface OrderingService {

    List<TheOrder> getAllOrders();

    List<TheOrder> getOrdersForCustomer(Customer customer);

    void removeOrder(int orderIdToRemove);

    void removeOrderItem(int orderItemIdToRemove);

    OrderItem updateQuantity(int productId, int quantity);

    TheOrder getOrderById(int orderId);

    OrderItem findOrderItem(int orderItemId);

    ShippingMethodForm getShippingMethodForm();

    void setShippingMethod(ShippingMethodForm shippingMethodForm);

    ShippingAddressForm getShippingAddressForm();

    void setShippingAddressDetails(ShippingAddressForm shippingAddressForm);

    PaymentMethodForm getPaymentMethodForm();

    void setPaymentMethod(PaymentMethodForm paymentMethodForm);

    OrderProcessingResult acceptOrder();
}
