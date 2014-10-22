package biol.ipharm.controller.admin;

import biol.ipharm.entity.ProductGroup;
import biol.ipharm.service.ProductGroupService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class ProductGroupControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__PRODUCT_GROUP_LIST_PAGE = "admin/product-group/product-group-list";
    private static final String ADMIN__REDIRECT_PRODUCT_GROUP_LIST_PAGE = REDIRECT + ADMIN__PRODUCT_GROUP_LIST_PAGE;
    private static final String ADMIN__ADD_PRODUCT_GROUP_PAGE = "admin/product-group/add-product-group";
    private static final String ADMIN__PRODUCT_GROUP_ADDED_PAGE = "admin/product-group/product-group-added";
    private static final String ADMIN__REDIRECT_PRODUCT_GROUP_ADDED_PAGE = REDIRECT + ADMIN__PRODUCT_GROUP_ADDED_PAGE;
    private static final String ADMIN__EDIT_PRODUCT_GROUP_PAGE = "admin/product-group/edit-product-group";

    private final ProductGroupService productGroupService;

    @Autowired
    public ProductGroupControllerAdmin(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @RequestMapping(value = "/admin/product-group/product-group-list", method = RequestMethod.GET)
    public String showAllProductGroups(Model model) {
        model.addAttribute("productGroupList", productGroupService.getAllProductGroups());
        return ADMIN__PRODUCT_GROUP_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/add-product-group", method = RequestMethod.GET)
    public String showAddProductGroupPage(Model model) {
        model.addAttribute("productGroup", new ProductGroup());
        return ADMIN__ADD_PRODUCT_GROUP_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/product-group-added", method = RequestMethod.POST)
    public String addProductGroup(@Valid final ProductGroup productGroup, final BindingResult result) {
        if (result.hasErrors()) {
            return ADMIN__ADD_PRODUCT_GROUP_PAGE;
        }
        if (productGroupService.isProductGroupAlreadyExists(productGroup)) {
            result.rejectValue("productGroupName", "theSameGroupAlreadyExists", "Группа с таким названием уже существует. Введите другое название.");
            return ADMIN__ADD_PRODUCT_GROUP_PAGE;
        }
        productGroupService.save(productGroup);
        return ADMIN__REDIRECT_PRODUCT_GROUP_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/product-group-added", method = RequestMethod.GET)
    public String showProductGroupAddedPage() {
        return ADMIN__PRODUCT_GROUP_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/remove-product-group", method = RequestMethod.GET)
    public String removeProductGroup(@RequestParam("productGroupIdToRemove") int productGroupIdToRemove) {
        productGroupService.remove(productGroupIdToRemove);
        return ADMIN__REDIRECT_PRODUCT_GROUP_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/edit-product-group", method = RequestMethod.GET)
    public String showEditProductGroupPage(@RequestParam("productGroupIdToEdit") int productGroupIdToEdit, Model model) {
        model.addAttribute("productGroup", productGroupService.findOne(productGroupIdToEdit));
        return ADMIN__EDIT_PRODUCT_GROUP_PAGE;
    }

    @RequestMapping(value = "/admin/product-group/update-product-group", method = RequestMethod.POST)
    public String editProductGroup(@Valid final ProductGroup productGroup, final BindingResult result) {
        if (result.hasErrors()) {
            return ADMIN__EDIT_PRODUCT_GROUP_PAGE;
        }
        if (productGroupService.isProductGroupAlreadyExists(productGroup)) {
            result.rejectValue("productGroupName", "theSameGroupAlreadyExists", "Группа с таким названием уже существует. Введите другое название.");
            return ADMIN__EDIT_PRODUCT_GROUP_PAGE;
        }
        productGroupService.save(productGroup);
        return ADMIN__REDIRECT_PRODUCT_GROUP_LIST_PAGE;
    }
}
