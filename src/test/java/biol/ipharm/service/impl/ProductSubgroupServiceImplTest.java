package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProductSubgroupDao;
import biol.ipharm.entity.ProductSubgroup;
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
public class ProductSubgroupServiceImplTest extends SampleBaseTestCase {

    private ProductSubgroupServiceImpl productSubgroupServiceImpl;

    @Mock
    ProductSubgroupDao mockProductSubgroupDao;

    @Before
    public void setUp() {
        productSubgroupServiceImpl = new ProductSubgroupServiceImpl(mockProductSubgroupDao);
    }

    @Test
    public void getAllProductSubgroups() {
        List<ProductSubgroup> productSubgroupList = new ArrayList<>();
        when(mockProductSubgroupDao.findAll()).thenReturn(productSubgroupList);

        List<ProductSubgroup> actualList = productSubgroupServiceImpl.getAllProductSubgroups();

        assertEquals(productSubgroupList, actualList);
    }

    @Test
    public void save() {
        ProductSubgroup productSubgroup = new ProductSubgroup();

        productSubgroupServiceImpl.save(productSubgroup);

        verify(mockProductSubgroupDao).save(productSubgroup);
    }

    @Test
    public void findOne() {
        Integer productSubgroupId = 3;
        ProductSubgroup productSubgroup = new ProductSubgroup();
        when(productSubgroupServiceImpl.findOne(productSubgroupId)).thenReturn(productSubgroup);

        ProductSubgroup actualProductSubgroup = productSubgroupServiceImpl.findOne(productSubgroupId);

        assertEquals(productSubgroup, actualProductSubgroup);
    }

    @Test
    public void remove() {
        int productSubgroupIdToRemove = 2;

        productSubgroupServiceImpl.remove(productSubgroupIdToRemove);

        verify(mockProductSubgroupDao).delete(productSubgroupIdToRemove);
    }

    @Test
    public void isProductSubgroupAlreadyExists_Exists_True() {
        String productSubgroupName = "productSubgroupName";
        ProductSubgroup productSubgroup = new ProductSubgroup();
        productSubgroup.setProductSubgroupName(productSubgroupName);
        when(mockProductSubgroupDao.findByProductSubgroupName(productSubgroupName)).thenReturn(productSubgroup);

        assertTrue(productSubgroupServiceImpl.isProductSubgroupAlreadyExists(productSubgroup));
    }

    @Test
    public void isProductSubgroupAlreadyExists_NotExists_False() {
        String productSubgroupName = "productSubgroupName";
        ProductSubgroup productSubgroup = new ProductSubgroup();
        productSubgroup.setProductSubgroupName(productSubgroupName);
        when(mockProductSubgroupDao.findByProductSubgroupName(productSubgroupName)).thenReturn(null);

        assertFalse(productSubgroupServiceImpl.isProductSubgroupAlreadyExists(productSubgroup));
    }
}
