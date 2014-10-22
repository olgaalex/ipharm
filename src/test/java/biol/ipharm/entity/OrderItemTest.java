package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class OrderItemTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(OrderItem.class).verify();
    }
}
