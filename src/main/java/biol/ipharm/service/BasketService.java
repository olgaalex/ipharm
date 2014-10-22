package biol.ipharm.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface BasketService {

    void remove(int productIdToRemove);

    void updateQuantity(int productId, int quantity);

    void addToBasket(int productId);
}
