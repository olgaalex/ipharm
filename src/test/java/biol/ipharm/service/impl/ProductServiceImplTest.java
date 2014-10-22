package biol.ipharm.service.impl;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.dao.ProductDao;
import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.entity.Producer;
import biol.ipharm.entity.Product;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

/**
 *
 * @author Olga
 */
public class ProductServiceImplTest extends SampleBaseTestCase {

    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductDao mockProductDao;

    @Before
    public void setUp() {
        productServiceImpl = spy(new ProductServiceImpl(mockProductDao));
    }

    @Test
    public void getProduct() {
        int productId = 7;
        Product product = new Product();
        when(mockProductDao.findOne(productId)).thenReturn(product);

        Product actualProduct = productServiceImpl.getProduct(productId);

        assertEquals(product, actualProduct);
    }

    @Test
    public void getAnalogList() {
        Product product = new Product();
        final String interName = "InterName";
        product.setProductInternationalName(interName);
        final BigDecimal price = BigDecimal.TEN;
        product.setPrice(price);
        List<Product> productList = new ArrayList<>();
        when(mockProductDao.findByProductInternationalNameAndPriceLessThan(interName, price)).thenReturn(productList);

        List<Product> actualProductList = productServiceImpl.getAnalogList(product);

        assertEquals(productList, actualProductList);
    }

    @Test
    public void getProductAlphabeticSortedMap_ByProductName() {
        String productName = "Аспирин";
        List<Product> productList = new ArrayList<>();
        when(mockProductDao.findByProductNameContainingIgnoreCase(productName)).thenReturn(productList);
        Map<Character, Set<Product>> expectedMap = new TreeMap<>();
        when(productServiceImpl.populateMapInAlphabeticOrder(productList)).thenReturn(expectedMap);

        Map<Character, Set<Product>> actualMap = productServiceImpl.getProductAlphabeticSortedMap(productName);

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getProductAlphabeticSortedMap_ByProductSubgroupId() {
        int productSubgroupId = 7;
        List<Product> productList = new ArrayList<>();
        when(mockProductDao.findByProductSubgroupOrderByProductNameAsc(productSubgroupId)).thenReturn(productList);
        Map<Character, Set<Product>> expectedMap = new TreeMap<>();
        when(productServiceImpl.populateMapInAlphabeticOrder(productList)).thenReturn(expectedMap);

        Map<Character, Set<Product>> actualMap = productServiceImpl.getProductAlphabeticSortedMap(productSubgroupId);

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void populateMapInAlphabeticOrder() {
        Product aspirin = new Product();
        String aspirinName = "Жаспирин";
        aspirin.setProductName(aspirinName);
        Product analgin = new Product();
        String analginName = "жанальгин";
        analgin.setProductName(analginName);
        Product validol = new Product();
        String validolName = "Валидол";
        validol.setProductName(validolName);
        List<Product> productList = new ArrayList<>();
        productList.add(aspirin);
        productList.add(analgin);
        productList.add(validol);

        Map<Character, Set<Product>> map = productServiceImpl.populateMapInAlphabeticOrder(productList);

        assertEquals(2, map.keySet().size());
        assertTrue(map.containsKey('Ж'));
        assertFalse(map.containsKey('ж'));
        assertTrue(map.get('Ж').size() == 2);
        Iterator iterator = map.get('Ж').iterator();
        assertEquals("жанальгин", ((Product) iterator.next()).getProductName());
        assertEquals("Жаспирин", ((Product) iterator.next()).getProductName());
        assertTrue(map.containsKey('В'));
        assertTrue(map.get('В').size() == 1);
    }

    @Test
    public void getProductList() {
        List<Product> productList = new ArrayList<>();
        when(mockProductDao.findAll()).thenReturn(productList);

        List<Product> actualList = productServiceImpl.getProductList();

        assertEquals(productList, actualList);
    }

    @Test
    public void save() {
        Product product = new Product();

        productServiceImpl.save(product);

        verify(mockProductDao).save(product);
    }

    @Test
    public void remove() {
        int productIdToRemove = 2;

        productServiceImpl.remove(productIdToRemove);

        verify(mockProductDao).delete(productIdToRemove);
    }

    @Test
    public void isProductAlreadyExists_Exists_True() {
        Product product = getProductWithAllFields();
        when(mockProductDao.findByProductCompoundKey(
                product.getProductName(),
                product.getProductInternationalName(),
                product.getQuantitativeComposition(),
                product.getDosage(),
                product.getProducer(),
                product.getPharmaceuticalForm()))
                .thenReturn(product);

        assertTrue(productServiceImpl.isProductAlreadyExists(product));
    }

    private Product getProductWithAllFields() {
        Product product = new Product();
        product.setProductName("Флавамед");
        product.setProductInternationalName("Ambrocsolum hidrochloridum");
        product.setStorageOnHand(7);
        product.setAnnotation("Annotation");
        product.setQuantitativeComposition(20);
        product.setPrice(BigDecimal.TEN);
        product.setDosage("30 мг");
        byte[] image = {1, 0, 1};
        product.setImage(image);
        product.setPharmaceuticalForm(new PharmaceuticalForm("таблетки"));
        product.setProducer(new Producer("Berlin-Chemie Menarini", "Berlin"));
        product.setProductSubgroup(new ProductSubgroup("Антикоагулянты", new ProductGroup()));

        return product;
    }

    @Test
    public void isProductSubgroupAlreadyExists_NotExists_False() {
        Product product = getProductWithAllFields();
        when(mockProductDao.findByProductCompoundKey(
                product.getProductName(),
                product.getProductInternationalName(),
                product.getQuantitativeComposition(),
                product.getDosage(),
                product.getProducer(),
                product.getPharmaceuticalForm()))
                .thenReturn(null);

        assertFalse(productServiceImpl.isProductAlreadyExists(product));
    }

    @Test
    public void isProductChanged_ProductUniqueFieldsHaveBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        productFromUi.setProductName("Валидол");
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        productFromDatabase.setProductName("Афлубин");
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_StorageOnHandHasBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        productFromUi.setStorageOnHand(3);
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        productFromDatabase.setStorageOnHand(5);
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_AnnotationHasBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        productFromUi.setAnnotation("Old annotation");
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        productFromDatabase.setAnnotation("New annotation");
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_PriceHasBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        productFromUi.setPrice(BigDecimal.ZERO);
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        productFromDatabase.setPrice(BigDecimal.TEN);
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_ImageHasBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        byte[] imageFromUi = {1, 0, 1};
        productFromUi.setImage(imageFromUi);
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        byte[] imageFromDatabase = {0, 1, 0};
        productFromDatabase.setImage(imageFromDatabase);
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_ProductSubgroupHasBeenChanged_True() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        productFromUi.setProductSubgroup(new ProductSubgroup("Муколитики", new ProductGroup()));
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        productFromDatabase.setProductSubgroup(new ProductSubgroup("Жаропонижающие", new ProductGroup()));
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertTrue(productServiceImpl.isProductChanged(productFromUi));
    }

    @Test
    public void isProductChanged_AllFieldsAreTheSame_False() {
        int productId = 1;
        Product productFromUi = getProductWithAllFields();
        productFromUi.setProductId(productId);
        Product productFromDatabase = getProductWithAllFields();
        productFromDatabase.setProductId(productId);
        when(mockProductDao.findOne(productId)).thenReturn(productFromDatabase);

        assertFalse(productServiceImpl.isProductChanged(productFromUi));
    }
}
