package biol.ipharm.controller.admin;

import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.ProductGroupService;
import biol.ipharm.service.ProductSubgroupService;
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
public class ProductSubgroupControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__PRODUCT_SUBGROUP_LIST_PAGE = "admin/product-subgroup/product-subgroup-list";
    private static final String ADMIN__REDIRECT_PRODUCT_SUBGROUP_LIST_PAGE = REDIRECT + ADMIN__PRODUCT_SUBGROUP_LIST_PAGE;
    private static final String ADMIN__ADD_PRODUCT_SUBGROUP_PAGE = "admin/product-subgroup/add-product-subgroup";
    private static final String ADMIN__PRODUCT_SUBGROUP_ADDED_PAGE = "admin/product-subgroup/product-subgroup-added";
    private static final String ADMIN__REDIRECT_PRODUCT_SUBGROUP_ADDED_PAGE = REDIRECT + ADMIN__PRODUCT_SUBGROUP_ADDED_PAGE;
    private static final String ADMIN__EDIT_PRODUCT_SUBGROUP_PAGE = "admin/product-subgroup/edit-product-subgroup";

    private final ProductSubgroupService productSubgroupService;
    private final ProductGroupService productGroupService;

    @Autowired
    public ProductSubgroupControllerAdmin(ProductGroupService productGroupService, ProductSubgroupService productSubgroupService) {
        this.productGroupService = productGroupService;
        this.productSubgroupService = productSubgroupService;
    }

    @RequestMapping(value = "admin/product-subgroup/product-subgroup-list", method = RequestMethod.GET)
    public String showAllProductSubgroups(Model model) {
        model.addAttribute("productSubgroupList", productSubgroupService.getAllProductSubgroups());
        return ADMIN__PRODUCT_SUBGROUP_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/product-subgroup/add-product-subgroup", method = RequestMethod.GET)
    public String showAddProductSubgroupPage(Model model) {
        setProductGroupListInto(model);
        model.addAttribute("productSubgroup", new ProductSubgroup());
        return ADMIN__ADD_PRODUCT_SUBGROUP_PAGE;
    }

    private void setProductGroupListInto(Model model) {
        model.addAttribute("productGroupList", productGroupService.getAllProductGroups());
    }

    @RequestMapping(value = "/admin/product-subgroup/product-subgroup-added", method = RequestMethod.POST)
    public String addProductSubgroup(@Valid final ProductSubgroup productSubgroup,
            final BindingResult result, Model model) {

        if (result.hasErrors()) {
            setProductGroupListInto(model);
            return ADMIN__ADD_PRODUCT_SUBGROUP_PAGE;
        }
        if (productSubgroupService.isProductSubgroupAlreadyExists(productSubgroup)) {
            result.rejectValue("productSubgroupName", "theSameSubgroupAlreadyExists", "Подгруппа с таким названием уже существует. Введите другое название.");
            setProductGroupListInto(model);
            return ADMIN__ADD_PRODUCT_SUBGROUP_PAGE;
        }
        productSubgroupService.save(productSubgroup);
        return ADMIN__REDIRECT_PRODUCT_SUBGROUP_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/product-subgroup/product-subgroup-added", method = RequestMethod.GET)
    public String showProductSubgroupAddedPage() {
        return ADMIN__PRODUCT_SUBGROUP_ADDED_PAGE;
    }

    @RequestMapping(value = "admin/product-subgroup/edit-product-subgroup")
    public String showEditProductSubgroupPage(@RequestParam("productSubgroupIdToEdit") int productSubgroupIdToEdit, Model model) {
        setProductGroupListInto(model);
        model.addAttribute("productSubgroup", productSubgroupService.findOne(productSubgroupIdToEdit));
        return ADMIN__EDIT_PRODUCT_SUBGROUP_PAGE;
    }

    @RequestMapping(value = "/admin/product-subgroup/update-product-subgroup", method = RequestMethod.POST)
    public String updateProductSubgroup(@Valid final ProductSubgroup productSubgroup,
            final BindingResult result, Model model) {

        if (result.hasErrors()) {
            setProductGroupListInto(model);
            return ADMIN__EDIT_PRODUCT_SUBGROUP_PAGE;
        }
        if (productSubgroupService.isProductSubgroupAlreadyExists(productSubgroup)) {
            result.rejectValue("productSubgroupName", "theSameSubgroupAlreadyExists", "Подгруппа с таким названием уже существует. Введите другое название.");
            setProductGroupListInto(model);
            return ADMIN__EDIT_PRODUCT_SUBGROUP_PAGE;
        }
        productSubgroupService.save(productSubgroup);
        return ADMIN__REDIRECT_PRODUCT_SUBGROUP_LIST_PAGE;
    }

    @RequestMapping(value = "admin/product-subgroup/remove-product-subgroup")
    public String removeProductSubgroup(@RequestParam("productSubgroupIdToRemove") int productSubgroupIdToRemove) {
        productSubgroupService.remove(productSubgroupIdToRemove);
        return ADMIN__REDIRECT_PRODUCT_SUBGROUP_LIST_PAGE;
    }
}
