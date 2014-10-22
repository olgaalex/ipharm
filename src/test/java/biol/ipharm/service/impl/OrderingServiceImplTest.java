package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.OrderItemDao;
import biol.ipharm.dao.TheOrderDao;
import biol.ipharm.entity.Customer;
import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.entity.enums.PaymentMethod;
import biol.ipharm.entity.enums.ShippingMethod;
import biol.ipharm.form.PaymentMethodForm;
import biol.ipharm.form.ShippingAddressForm;
import biol.ipharm.form.ShippingMethodForm;
import biol.ipharm.order.BasketSessionScopedBean;
import biol.ipharm.order.result.OrderAcceptedButEmailNotSentMsg;
import biol.ipharm.order.result.OrderAcceptedMsg;
import biol.ipharm.order.result.OrderProcessingResult;
import biol.ipharm.order.result.OrderUnacceptedMsg;
import biol.ipharm.order.result.UpdateOrderProcessingResultPageMsg;
import biol.ipharm.service.CustomerService;
import biol.ipharm.service.EmailService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class OrderingServiceImplTest extends SampleBaseTestCase {

    private OrderingServiceImpl orderingServiceImpl;

    @Mock
    private TheOrderDao mockOrderDao;
    @Mock
    private OrderItemDao mockOrderItemDao;
    @Mock
    private EmailService mockEmailService;
    @Mock
    private BasketSessionScopedBean mockBasketSessionScopedBean;

    @Mock
    private CustomerService mockCustomerService;

    @Before
    public void setUp() {
        orderingServiceImpl = spy(new OrderingServiceImpl(mockOrderDao, mockOrderItemDao, mockEmailService, mockBasketSessionScopedBean, mockCustomerService));
    }

    @Test
    public void getAllOrders() {
        List<TheOrder> expectedOrderList = new ArrayList<>();
        when(mockOrderDao.findAll()).thenReturn(expectedOrderList);

        List<TheOrder> actualOrderList = orderingServiceImpl.getAllOrders();

        assertEquals(expectedOrderList, actualOrderList);
    }

    @Test
    public void getOrdersForCustomer() {
        Customer customer = new Customer();
        List<TheOrder> expectedOrderList = new ArrayList<>();
        when(mockOrderDao.findByCustomer(customer)).thenReturn(expectedOrderList);

        List<TheOrder> actualOrderList = orderingServiceImpl.getOrdersForCustomer(customer);

        assertEquals(expectedOrderList, actualOrderList);
    }

    @Test
    public void removeOrder() {
        Integer orderIdToRemove = 3;

        orderingServiceImpl.removeOrder(orderIdToRemove);

        verify(mockOrderDao).delete(orderIdToRemove);
    }

    @Test
    public void removeOrderItem() {
        Integer orderItemIdToRemove = 4;

        orderingServiceImpl.removeOrderItem(orderItemIdToRemove);

        verify(mockOrderItemDao).delete(orderItemIdToRemove);
    }

    @Test
    public void updateQuantity_PassedQuantityIsZero_QuantityDoesntChanged() {
        int orderItemId = 4;
        int oldQuantity = 1;
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setProductQuantity(oldQuantity);
        when(mockOrderItemDao.findOne(orderItemId)).thenReturn(expectedOrderItem);
        int newQuantity = 0;

        OrderItem actualOrderItem = orderingServiceImpl.updateQuantity(orderItemId, newQuantity);

        verify(mockOrderItemDao, never()).save(expectedOrderItem);
        assertEquals(oldQuantity, actualOrderItem.getProductQuantity());
    }

    @Test
    public void updateItemQuantity_PassedQuantityIsLessThanZero_QuantityDoesntChanged() {
        int orderItemId = 4;
        int oldQuantity = 1;
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setProductQuantity(oldQuantity);
        when(mockOrderItemDao.findOne(orderItemId)).thenReturn(expectedOrderItem);
        int newQuantity = -3;

        OrderItem actualOrderItem = orderingServiceImpl.updateQuantity(orderItemId, newQuantity);

        verify(mockOrderItemDao, never()).save(expectedOrderItem);
        assertEquals(oldQuantity, actualOrderItem.getProductQuantity());
    }

    @Test
    public void updateItemQuantity_PassedQuantityIsGreaterThanZero_ItemQuantityUpdated() {
        int orderItemId = 4;
        int oldQuantity = 1;
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setProductQuantity(oldQuantity);
        when(mockOrderItemDao.findOne(orderItemId)).thenReturn(expectedOrderItem);
        int newQuantity = 3;

        OrderItem actualOrderItem = orderingServiceImpl.updateQuantity(orderItemId, newQuantity);

        verify(mockOrderItemDao).save(expectedOrderItem);
        assertEquals(newQuantity, actualOrderItem.getProductQuantity());
    }

    @Test
    public void getOrderById() {
        int orderId = 3;
        TheOrder orderExpected = new TheOrder();
        orderExpected.setOrderItemList(new ArrayList<OrderItem>());
        when(mockOrderDao.findOne(orderId)).thenReturn(orderExpected);

        TheOrder orderActual = orderingServiceImpl.getOrderById(orderId);

        assertEquals(orderExpected, orderActual);
    }

    @Test
    public void findOrderItem() {
        Integer orderItemId = 3;
        OrderItem expectedOrderItem = new OrderItem();
        when(mockOrderItemDao.findOne(orderItemId)).thenReturn(expectedOrderItem);

        OrderItem actualOrderItem = orderingServiceImpl.findOrderItem(orderItemId);

        assertEquals(expectedOrderItem, actualOrderItem);
    }

    @Test
    public void getShippingMethodForm_ShippingMethodInOrderNotSpecified_NewShippingMethodFormReturned() {
        when(orderingServiceImpl.isShippingMethodInOrderSpecified()).thenReturn(false);

        ShippingMethodForm actualForm = orderingServiceImpl.getShippingMethodForm();

        assertNull(actualForm.getShippingMethod());
    }

    @Test
    public void getShippingMethodForm_ShippingMethodInOrderSpecified_ShippingMethodFormWithSpecifiedShippingMethodReturned() {
        when(orderingServiceImpl.isShippingMethodInOrderSpecified()).thenReturn(true);
        TheOrder order = new TheOrder();
        final ShippingMethod specifiedShippingMethod = ShippingMethod.SELF_DELIVERY;
        order.setShippingMethod(specifiedShippingMethod);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        ShippingMethodForm shippingMethodForm = orderingServiceImpl.getShippingMethodForm();

        assertEquals(specifiedShippingMethod, shippingMethodForm.getShippingMethod());
    }

    @Test
    public void isShippingMethodInOrderExists_OrderNotExists_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(false);

        assertFalse(orderingServiceImpl.isShippingMethodInOrderSpecified());
    }

    @Test
    public void isShippingMethodInOrderExists_OrderExistsButShippingMethodNotSpecified_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertFalse(orderingServiceImpl.isShippingMethodInOrderSpecified());
    }

    @Test
    public void isShippingMethodInOrderExists_OrderExistsAndShippingMethodIsSpecified_True() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        final ShippingMethod specifiedShippingMethod = ShippingMethod.SELF_DELIVERY;
        order.setShippingMethod(specifiedShippingMethod);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertTrue(orderingServiceImpl.isShippingMethodInOrderSpecified());
    }

    @Test
    public void isOrderExists() {
        when(mockBasketSessionScopedBean.isOrderExists()).thenReturn(true);

        assertTrue(mockBasketSessionScopedBean.isOrderExists());
    }

    @Test
    public void setShippingMethod() {
        ShippingMethodForm shippingMethodForm = new ShippingMethodForm();
        ShippingMethod expectedShippingMethod = ShippingMethod.COURIER;
        shippingMethodForm.setShippingMethod(expectedShippingMethod);
        TheOrder order = new TheOrder();
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        orderingServiceImpl.setShippingMethod(shippingMethodForm);

        assertEquals(expectedShippingMethod, mockBasketSessionScopedBean.getOrder().getShippingMethod());
    }

    @Test
    public void getShippingAddressForm_CustomerInOrderNotExists_ShippingAddressFormContainsCustomerDetails() {
        when(orderingServiceImpl.isCustomerInOrderExists()).thenReturn(false);
        String firstName = "firstName";
        String middleName = "middleName";
        String lastName = "lastName";
        String phoneNumber = "123-45-67";
        String email = "customer@gmail.com";
        String address = "address";
        Customer customer = createCustomerWithDetails(firstName, middleName, lastName, phoneNumber, email, address);
        when(mockCustomerService.getAuthorizedUser()).thenReturn(customer);
        TheOrder order = new TheOrder();
        String comment = "Comment";
        order.setComment(comment);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        ShippingAddressForm actualForm = orderingServiceImpl.getShippingAddressForm();

        assertEquals(customer, mockBasketSessionScopedBean.getOrder().getCustomer());
        assertEquals(firstName, actualForm.getFirstName());
        assertEquals(middleName, actualForm.getMiddleName());
        assertEquals(lastName, actualForm.getLastName());
        assertEquals(phoneNumber, actualForm.getPhoneNumber());
        assertEquals(email, actualForm.getEmail());
        assertEquals(address, actualForm.getAddress());
        assertEquals(comment, actualForm.getComment());
    }

    private Customer createCustomerWithDetails(String firstName, String middleName, String lastName, String phoneNumber, String email, String address) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setMiddleName(middleName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customer.setAddress(address);

        return customer;
    }

    @Test
    public void getShippingAddressForm_CustomerInOrderExists_ShippingAddressFormContainsCustomerDetails() {
        when(orderingServiceImpl.isCustomerInOrderExists()).thenReturn(true);
        String firstName = "firstName";
        String middleName = "middleName";
        String lastName = "lastName";
        String phoneNumber = "123-45-67";
        String email = "customer@gmail.com";
        String address = "address";
        Customer customer = createCustomerWithDetails(firstName, middleName, lastName, phoneNumber, email, address);
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        String comment = "Comment";
        order.setComment(comment);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        ShippingAddressForm actualForm = orderingServiceImpl.getShippingAddressForm();

        verify(mockCustomerService, never()).getAuthorizedUser();
        assertEquals(customer, mockBasketSessionScopedBean.getOrder().getCustomer());
        assertEquals(firstName, actualForm.getFirstName());
        assertEquals(middleName, actualForm.getMiddleName());
        assertEquals(lastName, actualForm.getLastName());
        assertEquals(phoneNumber, actualForm.getPhoneNumber());
        assertEquals(email, actualForm.getEmail());
        assertEquals(address, actualForm.getAddress());
        assertEquals(comment, actualForm.getComment());
    }

    @Test
    public void isCustomerInOrderExists_OrderNotExists_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(false);

        assertFalse(orderingServiceImpl.isCustomerInOrderExists());
    }

    @Test
    public void isCustomerInOrderExists_OrderExistsButCustomerNotExists_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertFalse(orderingServiceImpl.isCustomerInOrderExists());
    }

    @Test
    public void isCustomerInOrderExists_OrderExistsAndCustomerExists_True() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        Customer customer = new Customer();
        order.setCustomer(customer);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertTrue(orderingServiceImpl.isCustomerInOrderExists());
    }

    @Test
    public void setShippingAddressDetails() {
        String firstName = "firstName";
        String middleName = "middleName";
        String lastName = "lastName";
        String phoneNumber = "123-45-67";
        String email = "customer@gmail.com";
        String address = "address";
        String comment = "Comment";
        ShippingAddressForm addressForm = new ShippingAddressForm();
        addressForm.setFirstName(firstName);
        addressForm.setMiddleName(middleName);
        addressForm.setLastName(lastName);
        addressForm.setPhoneNumber(phoneNumber);
        addressForm.setEmail(email);
        addressForm.setAddress(address);
        addressForm.setComment(comment);

        Customer customer = new Customer();
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        orderingServiceImpl.setShippingAddressDetails(addressForm);

        assertEquals(firstName, customer.getFirstName());
        assertEquals(middleName, customer.getMiddleName());
        assertEquals(lastName, customer.getLastName());
        assertEquals(phoneNumber, customer.getPhoneNumber());
        assertEquals(email, customer.getEmail());
        assertEquals(address, customer.getAddress());
        assertEquals(comment, order.getComment());
    }

    @Test
    public void getPaymentMethodForm_PaymentMethodInOrderNotExists_NewPaymentMethodFormReturned() {
        when(orderingServiceImpl.isPaymentMethodInOrderExists()).thenReturn(false);

        PaymentMethodForm actualForm = orderingServiceImpl.getPaymentMethodForm();

        assertNull(actualForm.getPaymentMethod());
    }

    @Test
    public void getPaymentMethodForm_PaymentMethodInOrderExists_PaymentMethodFormWithPaymentMethodFromOrderReturned() {
        when(orderingServiceImpl.isPaymentMethodInOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        order.setPaymentMethod(PaymentMethod.CASHLESS);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        PaymentMethodForm actualForm = orderingServiceImpl.getPaymentMethodForm();

        assertEquals(PaymentMethod.CASHLESS, actualForm.getPaymentMethod());
    }

    @Test
    public void isPaymentMethodInOrderExists_OrderNotExists_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(false);

        assertFalse(orderingServiceImpl.isPaymentMethodInOrderExists());
    }

    @Test
    public void isPaymentMethodInOrderExists_OrderExistsButPaymentMethodIsNull_False() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertFalse(orderingServiceImpl.isPaymentMethodInOrderExists());
    }

    @Test
    public void isPaymentMethodInOrderExists_OrderExistsAndPaymentMethodIsSpecified_True() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        TheOrder order = new TheOrder();
        order.setPaymentMethod(PaymentMethod.CASHLESS);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        assertTrue(orderingServiceImpl.isPaymentMethodInOrderExists());
    }

    @Test
    public void setPaymentMethodForm() {
        PaymentMethodForm paymentMethodForm = new PaymentMethodForm();
        PaymentMethod expectedPaymentMethod = PaymentMethod.PAY_PAL;
        paymentMethodForm.setPaymentMethod(expectedPaymentMethod);
        TheOrder order = new TheOrder();
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);

        orderingServiceImpl.setPaymentMethod(paymentMethodForm);

        assertEquals(expectedPaymentMethod, mockBasketSessionScopedBean.getOrder().getPaymentMethod());
    }

    @Test
    public void acceptOrder_OrderNotExists_UpdateOrderProcessingResultPageMsgReturned() {
        OrderProcessingResult orderProcessingResult = orderingServiceImpl.acceptOrder();

        assertTrue(orderProcessingResult.getClass() == UpdateOrderProcessingResultPageMsg.class);
    }

    @Test
    public void acceptOrder_OrderExistsButCouldNotBeSaved_OrderUnacceptedMsgReturned() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setEmail("Email");
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);
        when(mockOrderDao.save(order)).thenReturn(null);

        OrderProcessingResult orderProcessingResult = orderingServiceImpl.acceptOrder();

        assertTrue(orderProcessingResult.getClass() == OrderUnacceptedMsg.class);
    }

    @Test
    public void acceptOrder_OrderExistsAndSavedButEmailNotSent_OrderAcceptedButEmailNotSentMsgReturned() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setEmail("Email");
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);
        when(mockOrderDao.save(order)).thenReturn(order);
        when(mockEmailService.sendOrderConfirmationEmail(mockBasketSessionScopedBean)).thenReturn(false);

        OrderProcessingResult orderProcessingResult = orderingServiceImpl.acceptOrder();

        assertTrue(orderProcessingResult.getClass() == OrderAcceptedButEmailNotSentMsg.class);
    }

    @Test
    public void acceptOrder_OrderExistsAndSuccessfullySaved_OrderAcceptedMsgReturned() {
        when(orderingServiceImpl.isOrderExists()).thenReturn(true);
        Customer customer = new Customer();
        customer.setFirstName("FirstName");
        customer.setEmail("Email");
        TheOrder order = new TheOrder();
        order.setCustomer(customer);
        when(mockBasketSessionScopedBean.getOrder()).thenReturn(order);
        when(mockOrderDao.save(order)).thenReturn(order);
        when(mockEmailService.sendOrderConfirmationEmail(mockBasketSessionScopedBean)).thenReturn(true);

        OrderProcessingResult orderProcessingResult = orderingServiceImpl.acceptOrder();

        verify(mockBasketSessionScopedBean).setOrder(null);
        assertTrue(orderProcessingResult.getClass() == OrderAcceptedMsg.class);
    }
}
