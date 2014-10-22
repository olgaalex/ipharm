package biol.ipharm.controller.admin;

import biol.ipharm.controller.validator.MultipartFileValidator;
import biol.ipharm.entity.Product;
import biol.ipharm.service.PharmaceuticalFormService;
import biol.ipharm.service.ProducerService;
import biol.ipharm.service.ProductService;
import biol.ipharm.service.ProductSubgroupService;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olga
 */
@Controller
public class ProductControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__PRODUCT_LIST_PAGE = "admin/product/product-list";
    private static final String ADMIN__REDIRECT_PRODUCT_LIST_PAGE = REDIRECT + ADMIN__PRODUCT_LIST_PAGE;
    private static final String ADMIN__ADD_PRODUCT_PAGE = "admin/product/add-product";
    private static final String ADMIN__PRODUCT_ADDED_PAGE = "admin/product/product-added";
    private static final String ADMIN__REDIRECT_PRODUCT_ADDED_PAGE = REDIRECT + ADMIN__PRODUCT_ADDED_PAGE;
    private static final String ADMIN__EDIT_PRODUCT_PAGE = "admin/product/edit-product";

    private final ProductService productService;
    private final PharmaceuticalFormService pharmaceuticalFormService;
    private final ProducerService producerService;
    private final ProductSubgroupService productSubgroupService;

    @Autowired
    public ProductControllerAdmin(
            ProductService productService,
            PharmaceuticalFormService pharmaceuticalFormService,
            ProducerService producerService,
            ProductSubgroupService productSubgroupService) {
        this.productService = productService;
        this.pharmaceuticalFormService = pharmaceuticalFormService;
        this.producerService = producerService;
        this.productSubgroupService = productSubgroupService;
    }

    @RequestMapping(value = "/admin/product/product-list", method = RequestMethod.GET)
    public String showProductListPage(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return ADMIN__PRODUCT_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/product/add-product", method = RequestMethod.GET)
    public String showAddProductPage(Model model) {
        model.addAttribute("product", new Product());
        setDropdownValuesInto(model);
        return ADMIN__ADD_PRODUCT_PAGE;
    }

    void setDropdownValuesInto(Model model) {
        model.addAttribute("pharmaceuticalFormList", pharmaceuticalFormService.findAll());
        model.addAttribute("productSubgroupList", productSubgroupService.getAllProductSubgroups());
        model.addAttribute("producerList", producerService.getAllProducers());
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("image");
    }

    @RequestMapping(value = "/admin/product/product-added", method = RequestMethod.POST)
    public String addProduct(Model model, @Valid final Product product, final BindingResult result,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        if (!image.isEmpty()) {
            getMultipartFileValidator().validate(image, result);
            product.setImage(image.getBytes());
        }

        if (result.hasErrors()) {
            setDropdownValuesInto(model);
            return ADMIN__ADD_PRODUCT_PAGE;
        }

        if (productService.isProductAlreadyExists(product)) {
            result.rejectValue("productName", "theSameProductAlreadyExists", "Товар с такими характеристиками уже существует. Введите другие данные.");
            setDropdownValuesInto(model);
            return ADMIN__ADD_PRODUCT_PAGE;
        }

        productService.save(product);
        return ADMIN__REDIRECT_PRODUCT_ADDED_PAGE;
    }

    MultipartFileValidator getMultipartFileValidator() {
        return new MultipartFileValidator();
    }

    @RequestMapping(value = "admin/product/product-added", method = RequestMethod.GET)
    public String showProductAddedPage() {
        return ADMIN__PRODUCT_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/product/edit-product", method = RequestMethod.GET)
    public String showEditProductPage(@RequestParam("productIdToEdit") int productIdToEdit, Model model) {
        model.addAttribute("product", productService.getProduct(productIdToEdit));
        setDropdownValuesInto(model);
        return ADMIN__EDIT_PRODUCT_PAGE;
    }

    @RequestMapping(value = "/admin/product/update-product", method = RequestMethod.POST)
    public String updateProduct(Model model, @Valid final Product product, final BindingResult result,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        if (!image.isEmpty()) {
            getMultipartFileValidator().validate(image, result);
            product.setImage(image.getBytes());
        } else {
            byte[] existingImage = productService.getProduct(product.getProductId()).getImage();
            product.setImage(existingImage);
        }

        if (result.hasErrors()) {
            setDropdownValuesInto(model);
            return ADMIN__EDIT_PRODUCT_PAGE;
        }

        if (!productService.isProductChanged(product)) {
            result.rejectValue("productName", "theSameProductAlreadyExists", "Товар с такими характеристиками уже существует. Введите другие данные.");
            setDropdownValuesInto(model);
            return ADMIN__EDIT_PRODUCT_PAGE;
        }

        productService.save(product);
        return ADMIN__REDIRECT_PRODUCT_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/product/remove-product", method = RequestMethod.GET)
    public String removeProduct(@RequestParam("productIdToRemove") int productIdToRemove) {
        productService.remove(productIdToRemove);
        return ADMIN__REDIRECT_PRODUCT_LIST_PAGE;
    }
}
