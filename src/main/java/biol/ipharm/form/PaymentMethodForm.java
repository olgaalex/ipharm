package biol.ipharm.form;

import biol.ipharm.entity.enums.PaymentMethod;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Olga
 */
public class PaymentMethodForm {

    @NotNull(message = "Выберите метод оплаты")
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
