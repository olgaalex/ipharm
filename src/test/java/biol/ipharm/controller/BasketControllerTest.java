package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.service.impl.BasketServiceImpl;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class BasketControllerTest extends SampleBaseTestCase {

    private BasketController basketController;

    @Mock
    private BasketServiceImpl mockBasketService;

    @Before
    public void setUp() {
        basketController = new BasketController(mockBasketService);
    }

    @Test
    public void showBasketPage() {
        assertEquals("basket", basketController.showBasketPage());
    }

    @Test
    public void addToBasket() {
        int productId = 3;
        Model model = new ExtendedModelMap();

        String actualViewName = basketController.addToBasket(productId, model);

        verify(mockBasketService).addToBasket(productId);
        assertTrue(model.containsAttribute("productId"));
        assertEquals(productId, model.asMap().get("productId"));
        assertEquals("redirect:/product", actualViewName);
    }

    @Test
    public void addToBasketGet() {
        int productId = 3;
        Model model = new ExtendedModelMap();

        String actualViewName = basketController.addToBasketGet(productId, model);

        assertTrue(model.containsAttribute("productId"));
        assertEquals(productId, model.asMap().get("productId"));
        assertEquals("product", actualViewName);
    }

    @Test
    public void removeFromBasket() {
        int productIdToRemove = 2;

        String actualViewName = basketController.removeFromBasket(productIdToRemove);

        verify(mockBasketService).remove(productIdToRemove);
        assertEquals("basket", actualViewName);
    }

    @Test
    public void updateQuantity() {
        int productId = 1;
        int quantity = 2;

        String actualViewName = basketController.updateQuantity(productId, quantity);

        verify(mockBasketService).updateQuantity(productId, quantity);
        assertEquals("basket", actualViewName);
    }
}
