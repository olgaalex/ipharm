package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class ProductSubgroupTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ProductSubgroup.class).verify();
    }
}
