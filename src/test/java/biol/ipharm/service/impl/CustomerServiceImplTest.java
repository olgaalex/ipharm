package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.CustomerDao;
import biol.ipharm.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class CustomerServiceImplTest extends SampleBaseTestCase {

    private CustomerServiceImpl customerServiceImp;

    @Mock
    private CustomerDao mockCustomerDao;

    @Before
    public void setUp() {
        customerServiceImp = new CustomerServiceImpl(mockCustomerDao);
    }

    @Test
    public void saveCustomer() {
        Customer customer = new Customer();

        customerServiceImp.saveCustomer(customer);

        verify(mockCustomerDao).save(customer);
    }

    @Test
    public void findCustomerByLogin() {
        String login = "myLogin";

        customerServiceImp.findCustomerByLogin(login);

        verify(mockCustomerDao).findByLogin(login);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customersList = new ArrayList<>();
        when(mockCustomerDao.findAll()).thenReturn(customersList);

        List<Customer> actualList = customerServiceImp.getAllCustomers();

        assertEquals(customersList, actualList);
    }

    @Test
    public void remove() {
        int userIdToRemove = 2;

        customerServiceImp.remove(userIdToRemove);

        verify(mockCustomerDao).delete(userIdToRemove);
    }

    @Test
    public void findCustomerById() {
        int userIdToGetOrderList = 2;

        customerServiceImp.findCustomerById(userIdToGetOrderList);

        verify(mockCustomerDao).findOne(userIdToGetOrderList);
    }

    @Ignore(value = "Нужен контекст, иначе - NullPointerException")
    @Test
    public void getAuthorizedUser() {
        Customer expectedCustomer = new Customer();
        when(mockCustomerDao.findByLogin(anyString())).thenReturn(expectedCustomer);

        Customer actualCustomer = customerServiceImp.getAuthorizedUser();

        assertEquals(expectedCustomer, actualCustomer);
    }
}
