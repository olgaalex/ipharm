package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.PharmaceuticalFormDao;
import biol.ipharm.entity.PharmaceuticalForm;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class PharmaceuticalFormServiceImplTest extends SampleBaseTestCase {

    private PharmaceuticalFormServiceImpl pharmaceuticalFormServiceImpl;

    @Mock
    private PharmaceuticalFormDao mockPharmaceuticalFormDao;

    @Before
    public void setUp() {
        pharmaceuticalFormServiceImpl = new PharmaceuticalFormServiceImpl(mockPharmaceuticalFormDao);
    }

    @Test
    public void findAll() {
        List<PharmaceuticalForm> pharmaceuticalFormList = new ArrayList<>();
        when(mockPharmaceuticalFormDao.findAll()).thenReturn(pharmaceuticalFormList);

        List<PharmaceuticalForm> actualList = pharmaceuticalFormServiceImpl.findAll();

        assertEquals(pharmaceuticalFormList, actualList);
    }

    @Test
    public void findOne() {
        Integer pharmaceuticalFormId = 3;
        PharmaceuticalForm pharmaceuticalForm = new PharmaceuticalForm();
        when(pharmaceuticalFormServiceImpl.findOne(pharmaceuticalFormId)).thenReturn(pharmaceuticalForm);

        PharmaceuticalForm actualPharmaceuticalForm = pharmaceuticalFormServiceImpl.findOne(pharmaceuticalFormId);

        assertEquals(pharmaceuticalForm, actualPharmaceuticalForm);
    }
}
