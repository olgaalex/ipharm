package biol.ipharm.entity.enums.converter;

import biol.ipharm.entity.enums.PaymentMethod;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga
 */
public class PaymentMethodConverterTest {

    private final PaymentMethodConverter converter = new PaymentMethodConverter();

    @Test
    public void convertToDatabaseColumn_PaymentMethodCASH_CASH() {
        String actual = converter.convertToDatabaseColumn(PaymentMethod.CASH);

        assertEquals("CASH", actual);
    }

    @Test
    public void convertToDatabaseColumn_PaymentMethodCASHLESS_CASHLESS() {
        String actual = converter.convertToDatabaseColumn(PaymentMethod.CASHLESS);

        assertEquals("CASHLESS", actual);
    }

    @Test
    public void convertToDatabaseColumn_PaymentMethodPAYPAL_PAYPAL() {
        String actual = converter.convertToDatabaseColumn(PaymentMethod.PAY_PAL);

        assertEquals("PAY_PAL", actual);
    }

    @Test
    public void convertToEntityAttribute_CASH_PaymentMethodCASH() {
        PaymentMethod actual = converter.convertToEntityAttribute("CASH");

        assertEquals(PaymentMethod.CASH, actual);
    }

    @Test
    public void convertToEntityAttribute_CASHLESS_PaymentMethodCASHLESS() {
        PaymentMethod actual = converter.convertToEntityAttribute("CASHLESS");

        assertEquals(PaymentMethod.CASHLESS, actual);
    }

    @Test
    public void convertToEntityAttribute_PAYPAL_PaymentMethodPAYPAL() {
        PaymentMethod actual = converter.convertToEntityAttribute("PAY_PAL");

        assertEquals(PaymentMethod.PAY_PAL, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToEntityAttribute_NonExistentMethod_Exception() {
        converter.convertToEntityAttribute("trash");
    }
}
