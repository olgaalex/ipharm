package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProductDao;
import biol.ipharm.entity.Product;
import biol.ipharm.order.BasketSessionScopedBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class BasketServiceImplTest extends SampleBaseTestCase {

    @Mock
    private BasketSessionScopedBean mockBasketSessionScopedBean;
    @Mock
    private ProductDao mockProductDao;

    private BasketServiceImpl basketServiceImpl;

    @Before
    public void setUp() {
        basketServiceImpl = new BasketServiceImpl(mockProductDao, mockBasketSessionScopedBean);
    }

    @Test
    public void remove() {
        int productIdToRemove = 1;

        basketServiceImpl.remove(productIdToRemove);

        verify(mockBasketSessionScopedBean).removeItemFromOrder(productIdToRemove);
    }

    @Test
    public void updateQuantity() {
        int productId = 1;
        int quantity = 9;

        basketServiceImpl.updateQuantity(productId, quantity);

        verify(mockBasketSessionScopedBean).updateItemQuantity(productId, quantity);
    }

    @Test
    public void addToBasket() {
        int productId = 1;
        Product product = new Product();
        when(mockProductDao.findOne(productId)).thenReturn(product);

        basketServiceImpl.addToBasket(productId);

        verify(mockBasketSessionScopedBean).addItemToOrder(product);
    }
}
