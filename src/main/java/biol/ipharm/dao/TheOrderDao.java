package biol.ipharm.dao;

import biol.ipharm.entity.Customer;
import biol.ipharm.entity.TheOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface TheOrderDao extends CrudRepository<TheOrder, Integer> {

    List<TheOrder> findByCustomer(Customer customer);
}
