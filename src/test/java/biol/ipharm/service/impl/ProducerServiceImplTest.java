package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProducerDao;
import biol.ipharm.entity.Producer;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class ProducerServiceImplTest extends SampleBaseTestCase {

    private ProducerServiceImpl producerServiceImpl;

    @Mock
    private ProducerDao mockProducerDao;

    @Before
    public void setUp() {
        producerServiceImpl = new ProducerServiceImpl(mockProducerDao);
    }

    @Test
    public void getAllProducers() {
        List<Producer> producerList = new ArrayList<>();
        when(mockProducerDao.findAll()).thenReturn(producerList);

        List<Producer> actualList = producerServiceImpl.getAllProducers();

        assertEquals(producerList, actualList);
    }

    @Test
    public void save() {
        Producer producer = new Producer();

        producerServiceImpl.save(producer);

        verify(mockProducerDao).save(producer);
    }

    @Test
    public void findOne() {
        Integer producerId = 3;
        Producer producer = new Producer();
        when(producerServiceImpl.findOne(producerId)).thenReturn(producer);

        Producer actualProducer = producerServiceImpl.findOne(producerId);

        assertEquals(producer, actualProducer);
    }

    @Test
    public void remove() {
        int producerIdToRemove = 2;

        producerServiceImpl.remove(producerIdToRemove);

        verify(mockProducerDao).delete(producerIdToRemove);
    }

    @Test
    public void isProducerAlreadyExists_Exists_True() {
        String producerName = "UPSA";
        Producer producer = new Producer();
        producer.setProducerName(producerName);
        when(mockProducerDao.findByProducerName(producerName)).thenReturn(producer);

        assertTrue(producerServiceImpl.isProducerAlreadyExists(producer));
    }

    @Test
    public void isProducerAlreadyExists_NotExists_False() {
        String producerName = "UPSA";
        Producer producer = new Producer();
        producer.setProducerName(producerName);
        when(mockProducerDao.findByProducerName(producerName)).thenReturn(null);

        assertFalse(producerServiceImpl.isProducerAlreadyExists(producer));
    }
}
