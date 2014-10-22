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
public interface ProductListService {

    Map<Character, Set<Product>> getProductAlphabeticSortedMap(int productSubgroupId);
}
