package biol.ipharm.service;

import biol.ipharm.entity.ProductGroup;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface ProductGroupService {

    List<ProductGroup> getAllProductGroups();

    void save(ProductGroup productGroup);

    void remove(int productGroupId);

    ProductGroup findOne(Integer productGroupId);

    boolean isProductGroupAlreadyExists(ProductGroup productGroup);
}
