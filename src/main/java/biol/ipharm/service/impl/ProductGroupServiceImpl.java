package biol.ipharm.service.impl;

import biol.ipharm.dao.ProductGroupDao;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.service.ProductGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class ProductGroupServiceImpl implements ProductGroupService {

    private final ProductGroupDao productGroupDao;

    @Autowired
    public ProductGroupServiceImpl(ProductGroupDao productGroupDao) {
        this.productGroupDao = productGroupDao;
    }

    @Override
    public List<ProductGroup> getAllProductGroups() {
        return (List<ProductGroup>) productGroupDao.findAll();
    }

    @Override
    public void save(ProductGroup productGroup) {
        productGroupDao.save(productGroup);
    }

    @Override
    public void remove(int productGroupId) {
        productGroupDao.delete(productGroupId);
    }

    @Override
    public ProductGroup findOne(Integer productGroupId) {
        return productGroupDao.findOne(productGroupId);
    }

    @Override
    public boolean isProductGroupAlreadyExists(ProductGroup productGroup) {
        ProductGroup productGroupFromDatabase = productGroupDao.findByProductGroupName(productGroup.getProductGroupName());
        return productGroupFromDatabase != null;
    }
}
