package biol.ipharm.order;

import biol.ipharm.entity.OrderItem;
import biol.ipharm.entity.TheOrder;
import java.math.BigDecimal;

/**
 *
 * @author Olga
 */
public class OrderSumsCalculator {

    public static BigDecimal getOrderItemSum(OrderItem orderItem) {
        long itemSumInKopecks = getProductPriceInKopecks(orderItem) * orderItem.getProductQuantity();
        return getSumInHryvniasFromSumInKopecks(itemSumInKopecks);
    }

    public static BigDecimal getTotalSumOfOrder(TheOrder order) {
        if (order == null || order.getOrderItemList() == null) {
            return BigDecimal.ZERO;
        }

        long sum = 0L;

        for (OrderItem orderItem : order.getOrderItemList()) {
            long itemSumInKopecks = getProductPriceInKopecks(orderItem) * orderItem.getProductQuantity();
            sum += itemSumInKopecks;
        }

        return getSumInHryvniasFromSumInKopecks(sum);
    }

    private static long getProductPriceInKopecks(OrderItem orderItem) {
        return BigDecimalConverter.fromHryvniasToKopecks(orderItem.getProductPrice());
    }

    private static BigDecimal getSumInHryvniasFromSumInKopecks(long sum) {
        return BigDecimalConverter.fromKopecksToHryvnias(sum);
    }

    static class BigDecimalConverter {

        private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

        static long fromHryvniasToKopecks(BigDecimal valueInHryvnias) {
            return valueInHryvnias.multiply(ONE_HUNDRED).longValue();
        }

        static BigDecimal fromKopecksToHryvnias(long valueInKopecks) {
            return BigDecimal.valueOf(valueInKopecks).divide(ONE_HUNDRED);
        }
    }
}
