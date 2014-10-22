package biol.ipharm.service.impl;

import biol.ipharm.dao.ProducerDao;
import biol.ipharm.entity.Producer;
import biol.ipharm.service.ProducerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private final ProducerDao producerDao;

    @Autowired
    public ProducerServiceImpl(ProducerDao producerDao) {
        this.producerDao = producerDao;
    }

    @Override
    public List<Producer> getAllProducers() {
        return (List<Producer>) producerDao.findAll();
    }

    @Override
    public void save(Producer producer) {
        producerDao.save(producer);
    }

    @Override
    public Producer findOne(Integer producerId) {
        return producerDao.findOne(producerId);
    }

    @Override
    public void remove(int producerIdToRemove) {
        producerDao.delete(producerIdToRemove);
    }

    @Override
    public boolean isProducerAlreadyExists(Producer producer) {
        Producer producerFromDatabase = producerDao.findByProducerName(producer.getProducerName());
        return producerFromDatabase != null;
    }
}
