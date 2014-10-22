package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class CustomerTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Customer.class).verify();
    }
}
