package biol.ipharm.order;

import biol.ipharm.entity.Product;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Olga
 */
public enum ProductNameComparator implements Comparator<Product>, Serializable {

    INSTANCE;

    @Override
    public int compare(Product product_1, Product product_2) {
        String productName_1 = product_1.getProductName();
        String productName_2 = product_2.getProductName();
        return productName_1.compareToIgnoreCase(productName_2);
    }
}
