package biol.ipharm.service.impl;

import biol.ipharm.dao.OrderItemDao;
import biol.ipharm.dao.TheOrderDao;
import biol.ipharm.entity.Customer;
import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.form.PaymentMethodForm;
import biol.ipharm.form.ShippingAddressForm;
import biol.ipharm.form.ShippingMethodForm;
import biol.ipharm.order.BasketSessionScopedBean;
import biol.ipharm.order.OrderSumsCalculator;
import biol.ipharm.order.result.OrderAcceptedButEmailNotSentMsg;
import biol.ipharm.order.result.OrderAcceptedMsg;
import biol.ipharm.order.result.OrderProcessingResult;
import biol.ipharm.order.result.OrderUnacceptedMsg;
import biol.ipharm.order.result.UpdateOrderProcessingResultPageMsg;
import biol.ipharm.service.CustomerService;
import biol.ipharm.service.EmailService;
import biol.ipharm.service.OrderingService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olga
 */
@Service
public class OrderingServiceImpl implements OrderingService {

    private final TheOrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final EmailService emailService;
    private final BasketSessionScopedBean basketSessionScopedBean;
    private final CustomerService customerService;

    @Autowired
    public OrderingServiceImpl(TheOrderDao orderDao, OrderItemDao orderItemDao,
            EmailService emailService, BasketSessionScopedBean basketSessionScopedBean,
            CustomerService customerService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.emailService = emailService;
        this.basketSessionScopedBean = basketSessionScopedBean;
        this.customerService = customerService;
    }

    @Override
    public List<TheOrder> getAllOrders() {
        return (List<TheOrder>) orderDao.findAll();
    }

    @Override
    public List<TheOrder> getOrdersForCustomer(Customer customer) {
        return orderDao.findByCustomer(customer);
    }

    @Override
    public void removeOrder(int orderIdToRemove) {
        orderDao.delete(orderIdToRemove);
    }

    @Override
    public void removeOrderItem(int orderItemIdToRemove) {
        orderItemDao.delete(orderItemIdToRemove);
    }

    @Override
    public OrderItem updateQuantity(int orderItemId, int quantity) {
        OrderItem orderItem = orderItemDao.findOne(orderItemId);
        if (quantity > 0) {
            orderItem.setProductQuantity(quantity);
            orderItemDao.save(orderItem);
        }
        return orderItem;
    }

    @Override
    @Transactional
    public TheOrder getOrderById(int orderId) {
        TheOrder order = orderDao.findOne(orderId);
        order.getOrderItemList().size();
        return order;
    }

    @Override
    public OrderItem findOrderItem(int orderItemId) {
        return orderItemDao.findOne(orderItemId);
    }

    @Override
    public ShippingMethodForm getShippingMethodForm() {
        ShippingMethodForm shippingMethodForm = new ShippingMethodForm();
        if (isShippingMethodInOrderSpecified()) {
            shippingMethodForm.setShippingMethod(basketSessionScopedBean.getOrder().getShippingMethod());
        }

        return shippingMethodForm;
    }

    boolean isShippingMethodInOrderSpecified() {
        return isOrderExists() && (basketSessionScopedBean.getOrder().getShippingMethod() != null);
    }

    boolean isOrderExists() {
        return basketSessionScopedBean.isOrderExists();
    }

    @Override
    public void setShippingMethod(ShippingMethodForm shippingMethodForm) {
        basketSessionScopedBean.getOrder().setShippingMethod(shippingMethodForm.getShippingMethod());
    }

    @Override
    public ShippingAddressForm getShippingAddressForm() {
        if (!isCustomerInOrderExists()) {
            basketSessionScopedBean.getOrder().setCustomer(customerService.getAuthorizedUser());
        }

        ShippingAddressForm shippingAddressForm = new ShippingAddressForm();
        Customer customer = basketSessionScopedBean.getOrder().getCustomer();

        shippingAddressForm.setFirstName(customer.getFirstName());
        shippingAddressForm.setMiddleName(customer.getMiddleName());
        shippingAddressForm.setLastName(customer.getLastName());
        shippingAddressForm.setPhoneNumber(customer.getPhoneNumber());
        shippingAddressForm.setEmail(customer.getEmail());
        shippingAddressForm.setAddress(customer.getAddress());
        shippingAddressForm.setComment(basketSessionScopedBean.getOrder().getComment());

        return shippingAddressForm;
    }

    boolean isCustomerInOrderExists() {
        return isOrderExists() && (basketSessionScopedBean.getOrder().getCustomer() != null);
    }

    @Override
    public void setShippingAddressDetails(ShippingAddressForm shippingAddressForm) {
        Customer customer = basketSessionScopedBean.getOrder().getCustomer();

        customer.setFirstName(shippingAddressForm.getFirstName());
        customer.setMiddleName(shippingAddressForm.getMiddleName());
        customer.setLastName(shippingAddressForm.getLastName());
        customer.setPhoneNumber(shippingAddressForm.getPhoneNumber());
        customer.setEmail(shippingAddressForm.getEmail());
        customer.setAddress(shippingAddressForm.getAddress());

        basketSessionScopedBean.getOrder().setComment(shippingAddressForm.getComment());
    }

    @Override
    public PaymentMethodForm getPaymentMethodForm() {
        PaymentMethodForm paymentMethodForm = new PaymentMethodForm();
        if (isPaymentMethodInOrderExists()) {
            paymentMethodForm.setPaymentMethod(basketSessionScopedBean.getOrder().getPaymentMethod());
        }

        return paymentMethodForm;
    }

    boolean isPaymentMethodInOrderExists() {
        return isOrderExists() && (basketSessionScopedBean.getOrder().getPaymentMethod() != null);
    }

    @Override
    public void setPaymentMethod(PaymentMethodForm paymentMethodForm) {
        basketSessionScopedBean.getOrder().setPaymentMethod(paymentMethodForm.getPaymentMethod());
    }

    @Override
    public OrderProcessingResult acceptOrder() {
        if (isOrderExists()) {
            String customerName = basketSessionScopedBean.getOrder().getCustomer().getFirstName();
            String customerEmail = basketSessionScopedBean.getOrder().getCustomer().getEmail();

            TheOrder order = orderDao.save(basketSessionScopedBean.getOrder());
            if (order == null) {
                return new OrderUnacceptedMsg(customerName);
            }

            boolean isSuccessfullySent = emailService.sendOrderConfirmationEmail(basketSessionScopedBean);
            if (!isSuccessfullySent) {
                return new OrderAcceptedButEmailNotSentMsg(customerName, customerEmail);
            }

            basketSessionScopedBean.setOrder(null);
            return new OrderAcceptedMsg(customerName, customerEmail);
        }
        return new UpdateOrderProcessingResultPageMsg();
    }
}
