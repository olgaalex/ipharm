package biol.ipharm.service.impl;

import biol.ipharm.dao.CustomerDao;
import biol.ipharm.entity.Customer;
import biol.ipharm.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public Customer findCustomerByLogin(String login) { // user's login is his email
        return customerDao.findByLogin(login);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerDao.findAll();
    }

    @Override
    public void remove(int userIdToRemove) {
        customerDao.delete(userIdToRemove);
    }

    @Override
    public Customer findCustomerById(int userIdToGetOrderList) {
        return customerDao.findOne(userIdToGetOrderList);
    }

    @Override
    public Customer getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerName = ((UserDetails) authentication.getPrincipal()).getUsername();
        return customerDao.findByLogin(customerName);
    }
}
