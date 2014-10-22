package biol.ipharm.service;

import biol.ipharm.entity.Producer;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface ProducerService {

    List<Producer> getAllProducers();

    public void save(Producer producer);

    void remove(int productSubgroupIdToRemove);

    public Producer findOne(Integer producerId);

    boolean isProducerAlreadyExists(Producer producer);

}
