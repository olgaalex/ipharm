package biol.ipharm.dao;

import biol.ipharm.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface OrderItemDao extends CrudRepository<OrderItem, Integer> {

}
