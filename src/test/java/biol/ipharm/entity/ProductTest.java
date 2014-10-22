package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class ProductTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Product.class).verify();
    }
}
