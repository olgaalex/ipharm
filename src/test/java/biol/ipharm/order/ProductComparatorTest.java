package biol.ipharm.order;

import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.entity.Producer;
import biol.ipharm.entity.Product;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga
 */
public class ProductComparatorTest {

    private final PharmaceuticalForm pharmaceuticalForm = new PharmaceuticalForm("PharmForm");
    private final Producer producer = new Producer("Name", "Address");
    private final ProductSubgroup productSubgroup = new ProductSubgroup("SubgroupName", new ProductGroup("ProdGroupName"));

    private final ProductNameComparator productNameComparator = ProductNameComparator.INSTANCE;
    private Product product_1;
    private Product product_2;

    @Test
    public void testCompare_FirstStringPrecedesSecond_NegativeInteger() {
        product_1 = new Product("Аспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);
        product_2 = new Product("Бспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);

        int result = productNameComparator.compare(product_1, product_2);

        assertTrue(result < 0);
    }

    @Test
    public void testCompare_FirstStringFollowsSecond_PositiveInteger() {
        product_1 = new Product("Бспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);
        product_2 = new Product("Аспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);

        int result = productNameComparator.compare(product_1, product_2);

        assertTrue(result > 0);
    }

    @Test
    public void testCompare_FirstStringEqualsSecond_Zero() {
        product_1 = new Product("Аспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);
        product_2 = new Product("аспирин", "InterName", 1, 1, BigDecimal.ONE, "dosage", pharmaceuticalForm, producer, productSubgroup);

        int result = productNameComparator.compare(product_1, product_2);

        assertTrue(result == 0);
    }
}
