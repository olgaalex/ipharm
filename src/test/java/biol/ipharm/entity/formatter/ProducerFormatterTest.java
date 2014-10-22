package biol.ipharm.entity.formatter;

import biol.ipharm.entity.Producer;
import biol.ipharm.service.ProducerService;
import java.util.Locale;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class ProducerFormatterTest {

    private final ProducerFormatter producerFormatter = new ProducerFormatter();

    @Test
    public void print() {
        String expectedProduserName = "ООО ЛТД";
        Producer produser = new Producer();
        produser.setProducerName(expectedProduserName);

        String actual = producerFormatter.print(produser, Locale.getDefault());

        assertEquals(expectedProduserName, actual);
    }

    @Test
    public void parse() {
        ProducerService mockProducerService = mock(ProducerService.class);
        String produserId = "12";
        Producer expectedProducer = new Producer();
        when(mockProducerService.findOne(Integer.valueOf(produserId))).thenReturn(expectedProducer);
        producerFormatter.setProducerService(mockProducerService);

        Producer actual = producerFormatter.parse(produserId, Locale.getDefault());

        assertEquals(expectedProducer, actual);
    }
}
