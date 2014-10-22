package biol.ipharm.service.impl;

import biol.ipharm.dao.ProductSubgroupDao;
import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.order.ProductSubgroupNameComparator;
import biol.ipharm.service.IndexPageService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class IndexPageServiceImpl implements IndexPageService {

    private final ProductSubgroupDao productSubgroupDao;

    @Autowired
    public IndexPageServiceImpl(ProductSubgroupDao productSubgroupDao) {
        this.productSubgroupDao = productSubgroupDao;
    }

    @Override
    public Map<String, Set<ProductSubgroup>> getMapGroupedByProductGroupName() {
        List<ProductSubgroup> notEmptyProductSubgroups = productSubgroupDao.findNotEmptyProductSubgroups();
        return populateMapGroupedByProductGroupName(notEmptyProductSubgroups);
    }

    Map<String, Set<ProductSubgroup>> populateMapGroupedByProductGroupName(List<ProductSubgroup> productGroupList) {
        Map<String, Set<ProductSubgroup>> productGroupAndSubgroupMap = new TreeMap<>();

        for (ProductSubgroup productSubgroup : productGroupList) {
            String productGroupName = productSubgroup.getProductGroup().getProductGroupName().trim();

            if (!productGroupAndSubgroupMap.containsKey(productGroupName)) {
                Set<ProductSubgroup> newProductSubgroupSet = new TreeSet<>(ProductSubgroupNameComparator.INSTANCE);
                newProductSubgroupSet.add(productSubgroup);
                productGroupAndSubgroupMap.put(productGroupName, newProductSubgroupSet);
            } else {
                Set<ProductSubgroup> existentProductSubgroupSet = productGroupAndSubgroupMap.get(productGroupName);
                existentProductSubgroupSet.add(productSubgroup);
            }
        }
        return productGroupAndSubgroupMap;
    }
}
