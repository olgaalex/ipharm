package biol.ipharm.service;

import biol.ipharm.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface ProductService {

    Product getProduct(int productId);

    List<Product> getAnalogList(Product product);

    Map<Character, Set<Product>> getProductAlphabeticSortedMap(String productName);

    Map<Character, Set<Product>> getProductAlphabeticSortedMap(int productSubgroupId);

    List<Product> getProductList();

    public void save(Product product);

    public void remove(int productIdToRemove);

    boolean isProductAlreadyExists(Product product);

    boolean isProductChanged(Product product);
}
