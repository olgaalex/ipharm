package biol.ipharm.controller.admin;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.service.ProductGroupService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author Olga
 */
public class ProductGroupControllerAdminTest extends SampleBaseTestCase {

    private ProductGroupControllerAdmin productGroupControllerAdmin;
    private Model model;

    @Mock
    private ProductGroupService mockProductGroupService;

    @Before
    public void setUp() {
        productGroupControllerAdmin = new ProductGroupControllerAdmin(mockProductGroupService);
    }

    @Test
    public void showAllProductGroups() {
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        model = new ExtendedModelMap();

        String actualViewName = productGroupControllerAdmin.showAllProductGroups(model);

        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertEquals("admin/product-group/product-group-list", actualViewName);
    }

    @Test
    public void showAddProductGroupPage() {
        ProductGroup productGroup = new ProductGroup();
        model = new ExtendedModelMap();

        String actualViewName = productGroupControllerAdmin.showAddProductGroupPage(model);

        assertTrue(model.containsAttribute("productGroup"));
        assertEquals(productGroup, model.asMap().get("productGroup"));
        assertEquals("admin/product-group/add-product-group", actualViewName);
    }

    @Test
    public void addProductGroup_BindingResultHasError_AddProductGroupPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult resultWithError = new BeanPropertyBindingResult(productGroup, "productGroup");
        resultWithError.addError(new FieldError("productGroup", "productGroupName", "productGroupNameErrorMessage"));

        String actualViewName = productGroupControllerAdmin.addProductGroup(productGroup, resultWithError);

        assertEquals("admin/product-group/add-product-group", actualViewName);
    }

    @Test
    public void addProductGroup_BindingResultHasNoError_ProductGroupAddedPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(productGroup, "productGroup");

        String actualViewName = productGroupControllerAdmin.addProductGroup(productGroup, resultWithoutError);

        verify(mockProductGroupService).save(productGroup);
        assertEquals("redirect:/admin/product-group/product-group-added", actualViewName);
    }

    @Test
    public void addProductGroup_BindingResultHasNoErrorButProductGroupAlreadyExists_AddProductGroupPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult result = new BeanPropertyBindingResult(productGroup, "productGroup");
        String defaultErrorMessage = "Группа с таким названием уже существует. Введите другое название.";
        when(mockProductGroupService.isProductGroupAlreadyExists(productGroup)).thenReturn(true);

        String actualViewName = productGroupControllerAdmin.addProductGroup(productGroup, result);

        verify(mockProductGroupService, never()).save(productGroup);
        assertEquals(defaultErrorMessage, result.getFieldError("productGroupName").getDefaultMessage());
        assertEquals("admin/product-group/add-product-group", actualViewName);
    }

    @Test
    public void showProductGroupAddedPage() {
        assertEquals("admin/product-group/product-group-added", productGroupControllerAdmin.showProductGroupAddedPage());
    }

    @Test
    public void removeProductGroup() {
        int productGroupIdToRemove = 5;

        String actualViewName = productGroupControllerAdmin.removeProductGroup(productGroupIdToRemove);

        verify(mockProductGroupService).remove(productGroupIdToRemove);
        assertEquals("redirect:/admin/product-group/product-group-list", actualViewName);
    }

    @Test
    public void showEditProductGroupPage() {
        ProductGroup productGroup = new ProductGroup();
        int productGroupIdToEdit = 3;
        when(mockProductGroupService.findOne(productGroupIdToEdit)).thenReturn(productGroup);
        model = new ExtendedModelMap();

        String actualViewName = productGroupControllerAdmin.showEditProductGroupPage(productGroupIdToEdit, model);

        assertTrue(model.containsAttribute("productGroup"));
        assertEquals(productGroup, model.asMap().get("productGroup"));
        assertEquals("admin/product-group/edit-product-group", actualViewName);
    }

    @Test
    public void editProductGroup_BindingResultHasError_EditProductGroupPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult resultWithError = new BeanPropertyBindingResult(productGroup, "productGroup");
        resultWithError.addError(new FieldError("productGroup", "productGroupName", "productGroupNameErrorMessage"));

        String actualViewName = productGroupControllerAdmin.editProductGroup(productGroup, resultWithError);

        assertEquals("admin/product-group/edit-product-group", actualViewName);
    }

    @Test
    public void editProductGroup_BindingResultHasNoError_ProductGroupListPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(productGroup, "productGroup");

        String actualViewName = productGroupControllerAdmin.editProductGroup(productGroup, resultWithoutError);

        verify(mockProductGroupService).save(productGroup);
        assertEquals("redirect:/admin/product-group/product-group-list", actualViewName);
    }

    @Test
    public void editProductGroup_BindingResultHasNoErrorButProductGroupAlreadyExists_EditProductGroupPageReturned() {
        ProductGroup productGroup = new ProductGroup();
        BindingResult result = new BeanPropertyBindingResult(productGroup, "productGroup");
        String defaultErrorMessage = "Группа с таким названием уже существует. Введите другое название.";
        when(mockProductGroupService.isProductGroupAlreadyExists(productGroup)).thenReturn(true);

        String actualViewName = productGroupControllerAdmin.editProductGroup(productGroup, result);

        verify(mockProductGroupService, never()).save(productGroup);
        assertEquals(defaultErrorMessage, result.getFieldError("productGroupName").getDefaultMessage());
        assertEquals("admin/product-group/edit-product-group", actualViewName);
    }
}
