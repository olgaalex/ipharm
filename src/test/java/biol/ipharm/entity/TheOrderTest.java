package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class TheOrderTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(TheOrder.class).verify();
    }
}
