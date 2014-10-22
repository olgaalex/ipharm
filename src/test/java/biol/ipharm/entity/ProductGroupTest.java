package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class ProductGroupTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ProductGroup.class).verify();
    }
}
