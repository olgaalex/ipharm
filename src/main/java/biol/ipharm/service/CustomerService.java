package biol.ipharm.service;

import biol.ipharm.entity.Customer;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface CustomerService {

    void saveCustomer(Customer customer);

    Customer findCustomerByLogin(String login);

    List<Customer> getAllCustomers();

    void remove(int userIdToRemove);

    public Customer findCustomerById(int userIdToGetOrderList);

    public Customer getAuthorizedUser();
}
