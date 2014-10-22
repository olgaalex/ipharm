package biol.ipharm.service.impl;

import biol.ipharm.dao.ProductDao;
import biol.ipharm.entity.Product;
import biol.ipharm.order.BasketSessionScopedBean;
import biol.ipharm.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class BasketServiceImpl implements BasketService {

    private final ProductDao productDao;
    private final BasketSessionScopedBean basketSessionScopedBean;

    @Autowired
    public BasketServiceImpl(ProductDao productDao, BasketSessionScopedBean basketSessionScopedBean) {
        this.productDao = productDao;
        this.basketSessionScopedBean = basketSessionScopedBean;
    }

    @Override
    public void remove(int productIdToRemove) {
        basketSessionScopedBean.removeItemFromOrder(productIdToRemove);
    }

    @Override
    public void updateQuantity(int productId, int quantity) {
        basketSessionScopedBean.updateItemQuantity(productId, quantity);
    }

    @Override
    public void addToBasket(int productId) {
        Product product = productDao.findOne(productId);
        basketSessionScopedBean.addItemToOrder(product);
    }
}
