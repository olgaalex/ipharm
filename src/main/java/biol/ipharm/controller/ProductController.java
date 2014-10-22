package biol.ipharm.controller;

import biol.ipharm.entity.Product;
import biol.ipharm.service.ProductService;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class ProductController {

    private static final String PRODUCT_LIST_PAGE = "product-list";
    private static final String PRODUCT_NOT_FOUND_PAGE = "product-not-found";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/product-list", method = RequestMethod.GET)
    public String showProductListPage(@RequestParam("productSubgroupId") int productSubgroupId, Model model) {
        model.addAttribute("productAlphabeticSortedMap", productService.getProductAlphabeticSortedMap(productSubgroupId));
        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public String showProductPage(@RequestParam("productId") int productId, Model model) {
        final Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("analogList", productService.getAnalogList(product));
        return "product";
    }

    @RequestMapping(value = "/images/product/{productId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProductImage(@PathVariable("productId") int productId) {
        byte[] image = productService.getProduct(productId).getImage();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/find-product", method = RequestMethod.GET)
    public String findProduct(@RequestParam("productName") String searchedProductName, Model model) {
        if (searchedProductName.isEmpty()) {
            model.addAttribute("isSearchedProductNameEmpty", searchedProductName.isEmpty());
            return PRODUCT_NOT_FOUND_PAGE;
        }
        Map<Character, Set<Product>> mapWithFindProducts = productService.getProductAlphabeticSortedMap(searchedProductName.trim());
        if (mapWithFindProducts.isEmpty()) {
            return PRODUCT_NOT_FOUND_PAGE;
        }
        model.addAttribute("productAlphabeticSortedMap", mapWithFindProducts);
        return PRODUCT_LIST_PAGE;
    }
}
