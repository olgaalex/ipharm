package biol.ipharm.dao;

import biol.ipharm.entity.Producer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface ProducerDao extends CrudRepository<Producer, Integer> {

    Producer findByProducerName(String producerName);

}
