package biol.ipharm.entity.formatter;

import biol.ipharm.entity.ProductGroup;
import biol.ipharm.service.ProductGroupService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author Olga
 */
public class ProductGroupFormatter implements Formatter<ProductGroup> {

    private ProductGroupService productGroupService;

    @Autowired
    public void setProductGroupService(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @Override
    public String print(ProductGroup productGroup, Locale locale) {
        return productGroup.getProductGroupName();
    }

    @Override
    public ProductGroup parse(String text, Locale locale) {
        final Integer productGroupId = Integer.valueOf(text);
        return productGroupService.findOne(productGroupId);
    }
}
