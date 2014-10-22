package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class ProducerTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Producer.class).verify();
    }
}
