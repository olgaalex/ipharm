package biol.ipharm.dao;

import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.entity.Producer;
import biol.ipharm.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Olga
 */
public interface ProductDao extends CrudRepository<Product, Integer> {

    @Query("select p from Product p where p.productSubgroup.productSubgroupId = ?1 order by p.productName")
    List<Product> findByProductSubgroupOrderByProductNameAsc(int productSubgroupId);

    List<Product> findByProductInternationalNameAndPriceLessThan(String productInternationalName, BigDecimal price);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    @Query("select p from Product p where "
            + "p.productName = :productName and "
            + "p.productInternationalName = :productInternationalName and "
            + "p.quantitativeComposition = :quantitativeComposition and "
            + "p.dosage = :dosage and "
            + "p.producer = :producer and "
            + "p.pharmaceuticalForm = :pharmaceuticalForm")
    Product findByProductCompoundKey(
            @Param("productName") String productName,
            @Param("productInternationalName") String productInternationalName,
            @Param("quantitativeComposition") int quantitativeComposition,
            @Param("dosage") String dosage,
            @Param("producer") Producer producer,
            @Param("pharmaceuticalForm") PharmaceuticalForm pharmaceuticalForm);
}
