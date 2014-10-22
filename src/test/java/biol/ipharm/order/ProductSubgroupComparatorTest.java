package biol.ipharm.order;

import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class ProductSubgroupComparatorTest {

    private final ProductGroup productGroup = new ProductGroup("ProdGroupName");

    private final ProductSubgroupNameComparator productSubgroupNameComparator = ProductSubgroupNameComparator.INSTANCE;
    private ProductSubgroup productSubgroup_1;
    private ProductSubgroup productSubgroup_2;

    @Test
    public void testCompare_FirstStringPrecedesSecond_NegativeInteger() {
        productSubgroup_1 = new ProductSubgroup("Аспирин", productGroup);
        productSubgroup_2 = new ProductSubgroup("Бспирин", productGroup);

        int result = productSubgroupNameComparator.compare(productSubgroup_1, productSubgroup_2);

        assertTrue(result < 0);
    }

    @Test
    public void testCompare_FirstStringFollowsSecond_PositiveInteger() {
        productSubgroup_1 = new ProductSubgroup("Бспирин", productGroup);
        productSubgroup_2 = new ProductSubgroup("Аспирин", productGroup);

        int result = productSubgroupNameComparator.compare(productSubgroup_1, productSubgroup_2);

        assertTrue(result > 0);
    }

    @Test
    public void testCompare_FirstStringEqualsSecond_Zero() {
        productSubgroup_1 = new ProductSubgroup("Аспирин", productGroup);
        productSubgroup_2 = new ProductSubgroup("аспирин", productGroup);

        int result = productSubgroupNameComparator.compare(productSubgroup_1, productSubgroup_2);

        assertTrue(result == 0);
    }
}
