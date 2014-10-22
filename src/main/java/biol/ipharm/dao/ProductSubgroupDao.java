package biol.ipharm.dao;

import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface ProductSubgroupDao extends CrudRepository<ProductSubgroup, Integer> {

    List<ProductSubgroup> findByProductGroup(ProductGroup productGroup);

    @Query("select ps from ProductSubgroup ps, Product p where p.productSubgroup = ps.productSubgroupId GROUP BY ps.productSubgroupName")
    List<ProductSubgroup> findNotEmptyProductSubgroups();

    ProductSubgroup findByProductSubgroupName(String productSubgroupName);
}
