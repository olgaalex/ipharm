package biol.ipharm.entity.formatter;

import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.ProductSubgroupService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author Olga
 */
public class ProductSubgroupFormatter implements Formatter<ProductSubgroup> {

    private ProductSubgroupService productSubgroupService;

    @Autowired
    public void setProductSubgroupService(ProductSubgroupService productSubgroupService) {
        this.productSubgroupService = productSubgroupService;
    }

    @Override
    public String print(ProductSubgroup productSubgroup, Locale locale) {
        return productSubgroup.getProductSubgroupName();
    }

    @Override
    public ProductSubgroup parse(String text, Locale locale) {
        final Integer productSubgroupId = Integer.valueOf(text);
        return productSubgroupService.findOne(productSubgroupId);
    }

}
