package biol.ipharm.order;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class BasketSessionScopedBeanTest extends SampleBaseTestCase {

    private BasketSessionScopedBean basketSessionScopedBean;

    @Before
    public void setUp() {
        basketSessionScopedBean = new BasketSessionScopedBean();
    }

    @Test
    public void setOrder() {
        TheOrder order = new TheOrder();
        basketSessionScopedBean.setOrder(order);

        assertEquals(order, basketSessionScopedBean.getOrder());
    }

    @Test
    public void addItemToOrder_ItemNotExistsInOrder_ItemHasAddedToOrder() {
        Product product = new Product();

        basketSessionScopedBean.addItemToOrder(product);

        boolean isOrderContainsProduct = false;
        int addedProductQuantity = 0;
        for (OrderItem orderItem : basketSessionScopedBean.getOrder().getOrderItemList()) {
            if (orderItem.getProduct().equals(product)) {
                isOrderContainsProduct = true;
                addedProductQuantity = orderItem.getProductQuantity();
            }
        }
        assertTrue(isOrderContainsProduct);
        assertEquals(1, addedProductQuantity);
    }

    @Test
    public void addItemToOrder_ItemAlreadyOrdered_ProductQuantityIncreased() {
        Product product = new Product();
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        int initialProductQuantity = 1;
        orderItem.setProductQuantity(initialProductQuantity);
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        TheOrder order = new TheOrder();
        order.setOrderItemList(orderItemList);
        basketSessionScopedBean.setOrder(order);

        basketSessionScopedBean.addItemToOrder(product);

        boolean isOrderContainsProduct = false;
        int addedProductQuantity = 0;
        for (OrderItem item : basketSessionScopedBean.getOrder().getOrderItemList()) {
            if (item.getProduct().equals(product)) {
                isOrderContainsProduct = true;
                addedProductQuantity = item.getProductQuantity();
            }
        }
        assertTrue(isOrderContainsProduct);
        assertEquals(2, addedProductQuantity);
    }

    @Test
    public void isOrderExists_OrderExists_True() {
        basketSessionScopedBean.setOrder(new TheOrder());

        assertTrue(basketSessionScopedBean.isOrderExists());
    }

    @Test
    public void isOrderExists_OrderNotExists_False() {
        assertFalse(basketSessionScopedBean.isOrderExists());
    }

    @Test
    public void removeItemFromOrder_ItemNotExists_ListSizeNotChanged() {
        Product product1 = new Product();
        product1.setProductId(1);
        Product product2 = new Product();
        product2.setProductId(2);
        basketSessionScopedBean.setOrder(getOrderPopulatedByProducts(product1, product2));

        int productIdToRemove = 3;
        basketSessionScopedBean.removeItemFromOrder(productIdToRemove);

        int expectedOrderItemListSize = 2;
        int actualOrderItemListSize = basketSessionScopedBean.getOrder().getOrderItemList().size();
        assertEquals(expectedOrderItemListSize, actualOrderItemListSize);
    }

    private TheOrder getOrderPopulatedByProducts(Product... products) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (Product product : products) {
            OrderItem item = new OrderItem(1, BigDecimal.ZERO, new TheOrder(), product);
            orderItemList.add(item);
        }

        TheOrder order = new TheOrder();
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Test
    public void removeItemFromOrder_ItemExists_ItemRemoved() {
        Product product1 = new Product();
        product1.setProductId(1);
        Product product2 = new Product();
        product2.setProductId(2);
        basketSessionScopedBean.setOrder(getOrderPopulatedByProducts(product1, product2));

        int productIdToRemove = 1;
        basketSessionScopedBean.removeItemFromOrder(productIdToRemove);

        int expectedOrderItemListSize = 1;
        int actualOrderItemListSize = basketSessionScopedBean.getOrder().getOrderItemList().size();
        assertEquals(expectedOrderItemListSize, actualOrderItemListSize);
    }

    @Test
    public void updateItemQuantity_ItemExists_ItemQuantityUpdated() {
        Product product1 = new Product();
        product1.setProductId(1);
        Product product2 = new Product();
        product2.setProductId(2);

        basketSessionScopedBean.setOrder(getOrderPopulatedByProducts(product1, product2));

        int productIdToUpdate = 1;
        int expectedQuantity = 8;
        basketSessionScopedBean.updateItemQuantity(productIdToUpdate, expectedQuantity);

        int actualQuantity = basketSessionScopedBean.getOrder().getOrderItemList().get(0).getProductQuantity();
        assertEquals(expectedQuantity, actualQuantity);

    }

    @Test
    public void updateItemQuantity_ItemNotExists_ItemQuantityNotChanged() {
        Product product1 = new Product();
        product1.setProductId(3);
        Product product2 = new Product();
        product2.setProductId(2);

        basketSessionScopedBean.setOrder(getOrderPopulatedByProducts(product1, product2));

        int productIdToUpdate = 1;
        int expectedQuantity = 8;
        basketSessionScopedBean.updateItemQuantity(productIdToUpdate, expectedQuantity);

        int productQuantityFromProduct1 = basketSessionScopedBean.getOrder().getOrderItemList().get(0).getProductQuantity();
        int productQuantityFromProduct2 = basketSessionScopedBean.getOrder().getOrderItemList().get(1).getProductQuantity();
        assertEquals(productQuantityFromProduct1, 1);
        assertEquals(productQuantityFromProduct2, 1);

    }

    @Test
    public void updateItemQuantity_PassedQuantityIsZero_QuantityDoesntChanged() {
        Product product = new Product();
        product.setProductId(3);
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        int expectedQuantity = 2;
        orderItem.setProductQuantity(expectedQuantity);
        basketSessionScopedBean.setOrder(createOrderWithEmptyOrderItemList());
        basketSessionScopedBean.getOrder().getOrderItemList().add(orderItem);

        basketSessionScopedBean.updateItemQuantity(3, 0);

        assertEquals(expectedQuantity, basketSessionScopedBean.getOrder().getOrderItemList().get(0).getProductQuantity());
    }

    private TheOrder createOrderWithEmptyOrderItemList() {
        TheOrder order = new TheOrder();
        order.setOrderItemList(new ArrayList<OrderItem>());
        return order;
    }

    @Test
    public void updateItemQuantity_PassedQuantityIsLessThanZero_QuantityDoesntChanged() {
        Product product = new Product();
        product.setProductId(3);
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        int expectedQuantity = 2;
        orderItem.setProductQuantity(expectedQuantity);
        basketSessionScopedBean.setOrder(createOrderWithEmptyOrderItemList());
        basketSessionScopedBean.getOrder().getOrderItemList().add(orderItem);

        basketSessionScopedBean.updateItemQuantity(3, -7);

        assertEquals(expectedQuantity, basketSessionScopedBean.getOrder().getOrderItemList().get(0).getProductQuantity());
    }
}
