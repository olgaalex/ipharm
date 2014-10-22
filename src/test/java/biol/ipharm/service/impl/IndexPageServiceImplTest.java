package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProductSubgroupDao;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

/**
 *
 * @author Olga
 */
public class IndexPageServiceImplTest extends SampleBaseTestCase {

    private IndexPageServiceImpl indexPageServiceImpl;

    @Mock
    private ProductSubgroupDao mockProductSubgroupDao;

    @Before
    public void setUp() {
        indexPageServiceImpl = spy(new IndexPageServiceImpl(mockProductSubgroupDao));
    }

    @Test
    public void getMapGroupedByProductGroupName() {
        List<ProductSubgroup> notEmptyProductSubgroups = new ArrayList<>();
        when(mockProductSubgroupDao.findNotEmptyProductSubgroups()).thenReturn(notEmptyProductSubgroups);
        Map<String, Set<ProductSubgroup>> expectedMap = new TreeMap<>();
        when(indexPageServiceImpl.populateMapGroupedByProductGroupName(notEmptyProductSubgroups)).thenReturn(expectedMap);

        Map<String, Set<ProductSubgroup>> actualMap = indexPageServiceImpl.getMapGroupedByProductGroupName();

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void populateMapGroupedByProductGroupName() {
        ProductGroup antibioticus = new ProductGroup("Антибиотики");
        ProductSubgroup tetraciclinum = new ProductSubgroup("Тетрациклины", antibioticus);
        ProductSubgroup penicilinum = new ProductSubgroup("Пенициллины", antibioticus);
        ProductGroup immunotropus = new ProductGroup("Иммунотропные");
        ProductSubgroup immunomodulum = new ProductSubgroup("Иммуномодуляторы", immunotropus);

        List<ProductSubgroup> productGroupList = new ArrayList<>();
        productGroupList.add(tetraciclinum);
        productGroupList.add(penicilinum);
        productGroupList.add(immunomodulum);

        Map<String, Set<ProductSubgroup>> actualMap = indexPageServiceImpl.populateMapGroupedByProductGroupName(productGroupList);

        assertEquals(2, actualMap.keySet().size());
        assertTrue(actualMap.containsKey("Антибиотики"));
        assertTrue(actualMap.get("Антибиотики").size() == 2);
        Iterator iterator = actualMap.get("Антибиотики").iterator();
        assertEquals("Пенициллины", ((ProductSubgroup) iterator.next()).getProductSubgroupName());
        assertEquals("Тетрациклины", ((ProductSubgroup) iterator.next()).getProductSubgroupName());
        assertTrue(actualMap.containsKey("Иммунотропные"));
        assertTrue(actualMap.get("Иммунотропные").size() == 1);
    }
}
