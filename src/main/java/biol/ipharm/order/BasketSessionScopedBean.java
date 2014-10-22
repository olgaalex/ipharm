package biol.ipharm.order;

import biol.ipharm.entity.TheOrder;
import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.Product;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author Olga
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BasketSessionScopedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private TheOrder order;

    public BasketSessionScopedBean() {
    }

    public TheOrder getOrder() {
        return order;
    }

    public void setOrder(TheOrder order) {
        this.order = order;
    }

    public void addItemToOrder(Product product) {
        if (isItemAlreadyOrdered(product)) {
            increaseQuantity(product);
        } else {
            addItemToTheOrder(product);
        }
    }

    private boolean isItemAlreadyOrdered(Product product) {
        for (OrderItem orderItem : getNullSafetyOrderItemList()) {
            if (orderItem.getProduct().equals(product)) {
                return true;
            }
        }
        return false;
    }

    private List<OrderItem> getNullSafetyOrderItemList() {
        if (isOrderItemListExists()) {
            return order.getOrderItemList();
        } else {
            return Collections.<OrderItem>emptyList();
        }
    }

    private boolean isOrderItemListExists() {
        return isOrderExists() && (order.getOrderItemList() != null);
    }

    public boolean isOrderExists() {
        return order != null;
    }

    private void increaseQuantity(Product product) {
        for (OrderItem orderItem : getNullSafetyOrderItemList()) {
            if (orderItem.getProduct().equals(product)) {
                int oldProductQuantity = orderItem.getProductQuantity();
                orderItem.setProductQuantity(++oldProductQuantity);
            }
        }
    }

    private void addItemToTheOrder(Product product) {
        createNewOrderIfNotExists();
        createNewOrderItemListIfNotExists();

        order.getOrderItemList().add(createOrderItem(product));
    }

    private void createNewOrderIfNotExists() {
        if (!isOrderExists()) {
            order = new TheOrder();
        }
    }

    private void createNewOrderItemListIfNotExists() {
        if (!isOrderItemListExists()) {
            order.setOrderItemList(new ArrayList<OrderItem>());
        }
    }

    private OrderItem createOrderItem(Product product) {
        int defaultProductQuantity = 1;
        OrderItem orderItem = new OrderItem(defaultProductQuantity, product.getPrice(), this.order, product);

        return orderItem;
    }

    public BigDecimal getTotalSumOfOrder() {
        return OrderSumsCalculator.getTotalSumOfOrder(this.order);
    }

    public BigDecimal getTotalSumOfOrder(TheOrder order) {
        return OrderSumsCalculator.getTotalSumOfOrder(order);
    }

    public void removeItemFromOrder(int productIdToRemove) {
        List<OrderItem> orderItemList = getNullSafetyOrderItemList();

        for (Iterator<OrderItem> iterator = orderItemList.iterator(); iterator.hasNext();) {
            if (iterator.next().getProduct().getProductId() == productIdToRemove) {
                iterator.remove();
            }
        }
    }

    public void updateItemQuantity(int productIdToUpdate, int quantity) {
        if (quantity < 1) {
            return;
        }

        for (OrderItem orderItem : getNullSafetyOrderItemList()) {
            if (orderItem.getProduct().getProductId() == productIdToUpdate) {
                orderItem.setProductQuantity(quantity);
            }
        }
    }

    public BigDecimal getOrderItemSum(OrderItem orderItem) {
        return OrderSumsCalculator.getOrderItemSum(orderItem);
    }
}
