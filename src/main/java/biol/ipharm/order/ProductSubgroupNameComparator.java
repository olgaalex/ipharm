package biol.ipharm.order;

import biol.ipharm.entity.ProductSubgroup;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Olga
 */
public enum ProductSubgroupNameComparator implements Comparator<ProductSubgroup>, Serializable {

    INSTANCE;

    @Override
    public int compare(ProductSubgroup subgroup_1, ProductSubgroup subgroup_2) {
        String subgroupName_1 = subgroup_1.getProductSubgroupName();
        String subgroupName_2 = subgroup_2.getProductSubgroupName();
        return subgroupName_1.compareToIgnoreCase(subgroupName_2);
    }
}
