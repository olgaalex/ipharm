package biol.ipharm.service;

import biol.ipharm.entity.ProductSubgroup;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface IndexPageService {

    Map<String, Set<ProductSubgroup>> getMapGroupedByProductGroupName();
}
