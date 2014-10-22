package biol.ipharm.dao;

import biol.ipharm.entity.ProductGroup;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface ProductGroupDao extends CrudRepository<ProductGroup, Integer> {

    ProductGroup findByProductGroupName(String productGroupName);
}
