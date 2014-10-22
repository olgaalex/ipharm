package biol.ipharm.service.impl;

import biol.ipharm.dao.ProductDao;
import biol.ipharm.entity.Product;
import biol.ipharm.order.ProductNameComparator;
import biol.ipharm.service.ProductService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product getProduct(int productId) {
        return productDao.findOne(productId);
    }

    @Override
    public List<Product> getAnalogList(Product product) {
        return productDao.findByProductInternationalNameAndPriceLessThan(product.getProductInternationalName(), product.getPrice());
    }

    @Override
    public Map<Character, Set<Product>> getProductAlphabeticSortedMap(String productName) {
        List<Product> productList = productDao.findByProductNameContainingIgnoreCase(productName);
        return populateMapInAlphabeticOrder(productList);
    }

    @Override
    public Map<Character, Set<Product>> getProductAlphabeticSortedMap(int productSubgroupId) {
        List<Product> productList = productDao.findByProductSubgroupOrderByProductNameAsc(productSubgroupId);
        return populateMapInAlphabeticOrder(productList);
    }

    Map<Character, Set<Product>> populateMapInAlphabeticOrder(List<Product> productList) {
        Map<Character, Set<Product>> productAlphabeticSortedMap = new TreeMap<>();

        for (Product product : productList) {
            Character firstCharOfProductName = product.getProductName().trim().toUpperCase().charAt(0);

            if (!productAlphabeticSortedMap.containsKey(firstCharOfProductName)) {
                Set<Product> newProductSet = new TreeSet<>(ProductNameComparator.INSTANCE);
                newProductSet.add(product);
                productAlphabeticSortedMap.put(firstCharOfProductName, newProductSet);
            } else {
                Set<Product> existingProductSet = productAlphabeticSortedMap.get(firstCharOfProductName);
                existingProductSet.add(product);
            }
        }
        return productAlphabeticSortedMap;
    }

    @Override
    public List<Product> getProductList() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void remove(int productIdToRemove) {
        productDao.delete(productIdToRemove);
    }

    @Override
    public boolean isProductAlreadyExists(Product product) {
        Product productFromDatabase = productDao.findByProductCompoundKey(
                product.getProductName(),
                product.getProductInternationalName(),
                product.getQuantitativeComposition(),
                product.getDosage(),
                product.getProducer(),
                product.getPharmaceuticalForm());

        return productFromDatabase != null;
    }

    @Override
    public boolean isProductChanged(Product productFromUi) {
        Product productFromDatabase = productDao.findOne(productFromUi.getProductId());

        if (!Objects.equals(productFromUi, productFromDatabase)) {
            return true;
        }
        if (!Objects.equals(productFromUi.getStorageOnHand(), productFromDatabase.getStorageOnHand())) {
            return true;
        }
        if (!Objects.equals(productFromUi.getAnnotation(), productFromDatabase.getAnnotation())) {
            return true;
        }
        if (!Objects.equals(productFromUi.getPrice(), productFromDatabase.getPrice())) {
            return true;
        }
        if (!Objects.deepEquals(productFromUi.getImage(), productFromDatabase.getImage())) {
            return true;
        }
        return !Objects.equals(productFromUi.getProductSubgroup(), productFromDatabase.getProductSubgroup());
    }
}
