package biol.ipharm.order;

import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.TheOrder;
import biol.ipharm.order.OrderSumsCalculator.BigDecimalConverter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 *
 * @author Olga
 */
@RunWith(Theories.class)
public class OrderSumsCalculatorTest {

    @Test
    public void getOrderItemSum() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductPrice(new BigDecimal("3.50"));
        orderItem.setProductQuantity(2);
        BigDecimal expectedSum = new BigDecimal("7");

        BigDecimal actualSum = OrderSumsCalculator.getOrderItemSum(orderItem);

        assertEquals(0, expectedSum.compareTo(actualSum));
    }

    @Test
    public void getTotalSumOfOrder_OrderIsNull_Zero() {
        BigDecimal expectedPrice = BigDecimal.ZERO;

        BigDecimal actualPrice = OrderSumsCalculator.getTotalSumOfOrder(null);

        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }

    @Test
    public void getTotalSumOfOrder_OrderItemListIsNull_Zero() {
        BigDecimal expectedPrice = BigDecimal.ZERO;

        BigDecimal actualPrice = OrderSumsCalculator.getTotalSumOfOrder(new TheOrder());

        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }

    @Test
    public void getTotalSumOfOrder_OrderItemListHasItems_TotalSumOfOrder() {
        OrderItem orderItem_1 = new OrderItem();
        orderItem_1.setProductPrice(new BigDecimal("1.5"));
        orderItem_1.setProductQuantity(2);
        OrderItem orderItem_2 = new OrderItem();
        orderItem_2.setProductPrice(new BigDecimal("2"));
        orderItem_2.setProductQuantity(8);
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem_1);
        orderItemList.add(orderItem_2);
        TheOrder order = new TheOrder();
        order.setOrderItemList(orderItemList);
        BigDecimal expectedPrice = new BigDecimal("19.00");

        BigDecimal actualPrice = OrderSumsCalculator.getTotalSumOfOrder(order);

        assertEquals(0, expectedPrice.compareTo(actualPrice));
    }

    @DataPoints
    public static Object[][] getDataForBigDecimalConverterTest() {
        return new Object[][]{
            {BigDecimal.ZERO, 0L},
            {new BigDecimal("1"), 100L},
            {new BigDecimal("3.50"), 350L}
        };
    }

    @Theory
    public void fromHryvniasToKopecks(Object... testData) {
        long expected = (long) testData[1];

        long actual = BigDecimalConverter.fromHryvniasToKopecks((BigDecimal) testData[0]);

        assertEquals(expected, actual);
    }

    @Theory
    public void fromKopecksToHryvnias(Object... testData) {
        BigDecimal expected = (BigDecimal) testData[0];

        BigDecimal actual = BigDecimalConverter.fromKopecksToHryvnias((long) testData[1]);

        assertEquals(0, expected.compareTo(actual));
    }
}
