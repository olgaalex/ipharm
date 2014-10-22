package biol.ipharm.entity.formatter;

import biol.ipharm.entity.Producer;
import biol.ipharm.service.ProducerService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author Olga
 */
public class ProducerFormatter implements Formatter<Producer> {

    private ProducerService producerService;

    @Autowired
    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public String print(Producer producer, Locale locale) {
        return producer.getProducerName();
    }

    @Override
    public Producer parse(String text, Locale locale) {
        final Integer producerId = Integer.valueOf(text);
        return producerService.findOne(producerId);
    }
}
