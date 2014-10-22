package biol.ipharm.form;

import biol.ipharm.entity.enums.ShippingMethod;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Olga
 */
public class ShippingMethodForm {

    @NotNull(message = "Выберите метод доставки")
    private ShippingMethod shippingMethod;

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
