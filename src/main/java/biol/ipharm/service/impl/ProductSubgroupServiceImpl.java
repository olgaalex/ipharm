package biol.ipharm.service.impl;

import biol.ipharm.dao.ProductSubgroupDao;
import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.ProductSubgroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class ProductSubgroupServiceImpl implements ProductSubgroupService {

    private final ProductSubgroupDao productSubgroupDao;

    @Autowired
    public ProductSubgroupServiceImpl(ProductSubgroupDao productSubgroupDao) {
        this.productSubgroupDao = productSubgroupDao;
    }

    @Override
    public List<ProductSubgroup> getAllProductSubgroups() {
        return (List<ProductSubgroup>) productSubgroupDao.findAll();
    }

    @Override
    public void save(ProductSubgroup productSubgroup) {
        productSubgroupDao.save(productSubgroup);
    }

    @Override
    public ProductSubgroup findOne(Integer productSubgroupId) {
        return productSubgroupDao.findOne(productSubgroupId);
    }

    @Override
    public void remove(int productSubgroupIdToRemove) {
        productSubgroupDao.delete(productSubgroupIdToRemove);
    }

    @Override
    public boolean isProductSubgroupAlreadyExists(ProductSubgroup productSubgroup) {
        ProductSubgroup productSubgroupFromDatabase = productSubgroupDao.findByProductSubgroupName(productSubgroup.getProductSubgroupName());
        return productSubgroupFromDatabase != null;
    }
}
