package biol.ipharm.entity.enums.converter;

import biol.ipharm.entity.enums.ShippingMethod;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Olga
 */
@Converter(autoApply = true)
public class ShippingMethodConverter implements AttributeConverter<ShippingMethod, String> {

    @Override
    public String convertToDatabaseColumn(ShippingMethod shippingMethod) {
        switch (shippingMethod) {
            case SELF_DELIVERY:
                return "SELF_DELIVERY";
            case COURIER:
                return "COURIER";
            case POST:
                return "POST";
            default:
                throw new IllegalArgumentException("Unknown shippingMethod: " + shippingMethod);
        }
    }

    @Override
    public ShippingMethod convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "SELF_DELIVERY":
                return ShippingMethod.SELF_DELIVERY;
            case "COURIER":
                return ShippingMethod.COURIER;
            case "POST":
                return ShippingMethod.POST;
            default:
                throw new IllegalArgumentException("Unknown shippingMethod: " + dbData);
        }
    }
}
