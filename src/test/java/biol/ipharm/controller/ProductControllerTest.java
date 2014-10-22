package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.Product;
import biol.ipharm.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class ProductControllerTest extends SampleBaseTestCase {

    private ProductController productController;
    private Model model;

    @Mock
    private ProductService mockProductService;
    @Mock
    private Map<Character, Set<Product>> mockProductAlphabeticSortedMap;

    @Before
    public void setUp() {
        productController = new ProductController(mockProductService);
        model = new ExtendedModelMap();
    }

    @Test
    public void showProductListPage() {
        int productSubgroupId = 2;
        when(mockProductService.getProductAlphabeticSortedMap(productSubgroupId)).thenReturn(mockProductAlphabeticSortedMap);

        String actualViewName = productController.showProductListPage(productSubgroupId, model);

        assertTrue(model.containsAttribute("productAlphabeticSortedMap"));
        assertEquals(mockProductAlphabeticSortedMap, model.asMap().get("productAlphabeticSortedMap"));
        assertEquals("product-list", actualViewName);
    }

    @Test
    public void showProductPage() {
        int productId = 1;
        Product product = new Product();
        List<Product> analogList = new ArrayList<>();
        when(mockProductService.getProduct(productId)).thenReturn(product);
        when(mockProductService.getAnalogList(product)).thenReturn(analogList);

        String actualViewName = productController.showProductPage(productId, model);

        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.asMap().get("product"));
        assertTrue(model.containsAttribute("analogList"));
        assertEquals(analogList, model.asMap().get("analogList"));
        assertEquals("product", actualViewName);
    }

    @Test
    public void getProductImage() {
        int productId = 2;
        byte[] expectedImage = {};
        Product product = new Product();
        product.setImage(expectedImage);
        when(mockProductService.getProduct(productId)).thenReturn(product);

        ResponseEntity<byte[]> responseActual = productController.getProductImage(productId);

        assertArrayEquals(expectedImage, responseActual.getBody());
        assertEquals(MediaType.IMAGE_JPEG, responseActual.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, responseActual.getStatusCode());
    }

    @Test
    public void findProduct_searchedProductNameIsEmpty_returnProductNotFoundPage() {
        String searchedProductName = "";

        String actualViewName = productController.findProduct(searchedProductName, model);

        assertTrue(model.containsAttribute("isSearchedProductNameEmpty"));
        assertTrue((boolean) model.asMap().get("isSearchedProductNameEmpty"));
        assertEquals("product-not-found", actualViewName);
    }

    @Test
    public void findProduct_mapWithFindProductsIsEmpty_returnProductNotFoundPage() {
        String searchedProductName = "notEmptyValue";
        Map<Character, Set<Product>> mapWithoutProducts = new TreeMap<>();
        when(mockProductService.getProductAlphabeticSortedMap(searchedProductName)).thenReturn(mapWithoutProducts);

        String actualViewName = productController.findProduct(searchedProductName, model);

        assertTrue(mapWithoutProducts.isEmpty());
        assertFalse(model.containsAttribute("productAlphabeticSortedMap"));
        assertEquals("product-not-found", actualViewName);
    }

    @Test
    public void findProduct_mapWithFindProductsIsNotEmpty_returnProductListPage() {
        String searchedProductName = "Анальгин";
        Set<Product> productSet = new TreeSet<>();
        Map<Character, Set<Product>> mapWithFoundProducts = new TreeMap<>();
        mapWithFoundProducts.put('А', productSet);
        when(mockProductService.getProductAlphabeticSortedMap(searchedProductName)).thenReturn(mapWithFoundProducts);

        String actualViewName = productController.findProduct(searchedProductName, model);

        assertFalse(mapWithFoundProducts.isEmpty());
        assertTrue(model.containsAttribute("productAlphabeticSortedMap"));
        assertEquals(mapWithFoundProducts, model.asMap().get("productAlphabeticSortedMap"));
        assertEquals("product-list", actualViewName);
    }
}
