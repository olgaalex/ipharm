package biol.ipharm.dao;

import biol.ipharm.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface CustomerDao extends CrudRepository<Customer, Integer> {

    Customer findByLogin(String login);
}
