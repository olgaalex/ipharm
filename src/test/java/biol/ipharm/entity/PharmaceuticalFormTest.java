package biol.ipharm.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class PharmaceuticalFormTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(PharmaceuticalForm.class).verify();
    }
}
