package biol.ipharm.entity.formatter;

import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.ProductSubgroupService;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class ProductSubgroupFormatterTest {

    private final ProductSubgroupFormatter productSubgroupFormatter = new ProductSubgroupFormatter();

    @Test
    public void print() {
        String expectedProductSubgroupName = "Препараты, повышающие понимание";
        ProductSubgroup productSubgroup = new ProductSubgroup();
        productSubgroup.setProductSubgroupName(expectedProductSubgroupName);

        String actual = productSubgroupFormatter.print(productSubgroup, Locale.getDefault());

        assertEquals(expectedProductSubgroupName, actual);
    }

    @Test
    public void parse() {
        ProductSubgroupService mockProductSubgroupService = mock(ProductSubgroupService.class);
        String productSubgroupId = "12";
        ProductSubgroup expectedProductSubgroup = new ProductSubgroup();
        when(mockProductSubgroupService.findOne(Integer.valueOf(productSubgroupId))).thenReturn(expectedProductSubgroup);
        productSubgroupFormatter.setProductSubgroupService(mockProductSubgroupService);

        ProductSubgroup actual = productSubgroupFormatter.parse(productSubgroupId, Locale.getDefault());

        assertEquals(expectedProductSubgroup, actual);
    }
}
