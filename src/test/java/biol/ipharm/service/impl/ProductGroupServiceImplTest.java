package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProductGroupDao;
import biol.ipharm.entity.ProductGroup;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class ProductGroupServiceImplTest extends SampleBaseTestCase {

    private ProductGroupServiceImpl productGroupServiceImpl;
    @Mock
    private ProductGroupDao mockProductGroupDao;

    @Before
    public void setUp() {
        productGroupServiceImpl = new ProductGroupServiceImpl(mockProductGroupDao);
    }

    @Test
    public void getAllProductGroups() {
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupDao.findAll()).thenReturn(productGroupList);

        List<ProductGroup> actualList = productGroupServiceImpl.getAllProductGroups();

        assertEquals(productGroupList, actualList);
    }

    @Test
    public void save() {
        ProductGroup productGroup = new ProductGroup();

        productGroupServiceImpl.save(productGroup);

        verify(mockProductGroupDao).save(productGroup);
    }

    @Test
    public void remove() {
        int productGroupIdToRemove = 2;

        productGroupServiceImpl.remove(productGroupIdToRemove);

        verify(mockProductGroupDao).delete(productGroupIdToRemove);
    }

    @Test
    public void findOne() {
        Integer productGroupId = 3;
        ProductGroup productGroup = new ProductGroup();
        when(productGroupServiceImpl.findOne(productGroupId)).thenReturn(productGroup);

        ProductGroup actualProductGroup = productGroupServiceImpl.findOne(productGroupId);

        assertEquals(productGroup, actualProductGroup);
    }

    @Test
    public void isProductGroupAlreadyExists_Exists_True() {
        String productGroupName = "productGroupName";
        ProductGroup productGroup = new ProductGroup(productGroupName);
        when(mockProductGroupDao.findByProductGroupName(productGroupName)).thenReturn(productGroup);

        assertTrue(productGroupServiceImpl.isProductGroupAlreadyExists(productGroup));
    }

    @Test
    public void isProductGroupAlreadyExists_NotExists_False() {
        String productGroupName = "productGroupName";
        ProductGroup productGroup = new ProductGroup(productGroupName);
        when(mockProductGroupDao.findByProductGroupName(productGroupName)).thenReturn(null);

        assertFalse(productGroupServiceImpl.isProductGroupAlreadyExists(productGroup));
    }
}
