package biol.ipharm.entity.formatter;

import biol.ipharm.entity.ProductGroup;
import biol.ipharm.service.ProductGroupService;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class ProductGroupFormatterTest {

    private final ProductGroupFormatter productGroupFormatter = new ProductGroupFormatter();

    @Test
    public void print() {
        String expectedProductGroupName = "Негодующие";
        ProductGroup ProductGroup = new ProductGroup();
        ProductGroup.setProductGroupName(expectedProductGroupName);

        String actual = productGroupFormatter.print(ProductGroup, Locale.getDefault());

        assertEquals(expectedProductGroupName, actual);
    }

    @Test
    public void parse() {
        ProductGroupService mockProductGroupService = mock(ProductGroupService.class);
        String productGroupId = "12";
        ProductGroup expectedProductGroup = new ProductGroup();
        when(mockProductGroupService.findOne(Integer.valueOf(productGroupId))).thenReturn(expectedProductGroup);
        productGroupFormatter.setProductGroupService(mockProductGroupService);

        ProductGroup actual = productGroupFormatter.parse(productGroupId, Locale.getDefault());

        assertEquals(expectedProductGroup, actual);
    }
}
