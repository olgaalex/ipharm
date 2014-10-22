package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.IndexPageService;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class CommonControllerTest extends SampleBaseTestCase {

    private CommonController commonController;

    @Mock
    private IndexPageService mockIndexPageService;
    @Mock
    private Map<String, Set<ProductSubgroup>> mockProductGroupAndSubgroupMap;

    @Before
    public void setUp() {
        commonController = new CommonController(mockIndexPageService);
    }

    @Test
    public void showIndexPage() {
        when(mockIndexPageService.getMapGroupedByProductGroupName()).thenReturn(mockProductGroupAndSubgroupMap);
        Model model = new ExtendedModelMap();

        String actualViewName = commonController.showIndexPage(model);

        assertTrue(model.containsAttribute("productGroupAndSubgroupMap"));
        assertEquals(mockProductGroupAndSubgroupMap, model.asMap().get("productGroupAndSubgroupMap"));
        assertEquals("index", actualViewName);
    }

    @Test
    public void showAboutProjectPage() {
        assertEquals("about", commonController.showAboutProjectPage());
    }

    @Test
    public void showShippingAndPaymentPage() {
        assertEquals("shipping-and-payment", commonController.showShippingAndPaymentPage());
    }
}
