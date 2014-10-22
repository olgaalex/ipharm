package biol.ipharm.entity.enums.converter;

import biol.ipharm.entity.enums.ShippingMethod;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga
 */
public class ShippingMethodConverterTest {

    private final ShippingMethodConverter converter = new ShippingMethodConverter();

    @Test
    public void convertToDatabaseColumn_ShippingMethodSELFDELIVERY_SELFDELIVERY() {
        String actual = converter.convertToDatabaseColumn(ShippingMethod.SELF_DELIVERY);

        assertEquals("SELF_DELIVERY", actual);
    }

    @Test
    public void convertToDatabaseColumn_ShippingMethodCOURIER_COURIER() {
        String actual = converter.convertToDatabaseColumn(ShippingMethod.COURIER);

        assertEquals("COURIER", actual);
    }

    @Test
    public void convertToDatabaseColumn_ShippingMethodPOST_POST() {
        String actual = converter.convertToDatabaseColumn(ShippingMethod.POST);

        assertEquals("POST", actual);
    }

    @Test
    public void convertToEntityAttribute_SELF_DELIVERY_PaymentMethodSELF_DELIVERY() {
        ShippingMethod actual = converter.convertToEntityAttribute("SELF_DELIVERY");

        assertEquals(ShippingMethod.SELF_DELIVERY, actual);
    }

    @Test
    public void convertToEntityAttribute_COURIER_PaymentMethodCOURIER() {
        ShippingMethod actual = converter.convertToEntityAttribute("COURIER");

        assertEquals(ShippingMethod.COURIER, actual);
    }

    @Test
    public void convertToEntityAttribute_POST_PaymentMethodPOST() {
        ShippingMethod actual = converter.convertToEntityAttribute("POST");

        assertEquals(ShippingMethod.POST, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToEntityAttribute_NonExistentMethod_Exception() {
        converter.convertToEntityAttribute("trash");
    }
}
