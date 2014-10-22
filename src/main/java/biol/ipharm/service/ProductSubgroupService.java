package biol.ipharm.service;

import biol.ipharm.entity.ProductSubgroup;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface ProductSubgroupService {

    List<ProductSubgroup> getAllProductSubgroups();

    public void save(ProductSubgroup productSubgroup);

    public ProductSubgroup findOne(Integer productSubgroupId);

    void remove(int productSubgroupIdToRemove);

    boolean isProductSubgroupAlreadyExists(ProductSubgroup productSubgroup);
}
