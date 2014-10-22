package biol.ipharm.entity.enums.converter;

import biol.ipharm.entity.enums.PaymentMethod;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Olga
 */
@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethod paymentMethod) {
        switch (paymentMethod) {
            case CASH:
                return "CASH";
            case CASHLESS:
                return "CASHLESS";
            case PAY_PAL:
                return "PAY_PAL";
            default:
                throw new IllegalArgumentException("Unknown paymentMethod: " + paymentMethod);
        }
    }

    @Override
    public PaymentMethod convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "CASH":
                return PaymentMethod.CASH;
            case "CASHLESS":
                return PaymentMethod.CASHLESS;
            case "PAY_PAL":
                return PaymentMethod.PAY_PAL;
            default:
                throw new IllegalArgumentException("Unknown paymentMethod: " + dbData);
        }
    }
}
