package biol.ipharm.controller.admin;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.ProductGroup;
import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.ProductGroupService;
import biol.ipharm.service.ProductSubgroupService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class ProductSubgroupControllerAdminTest extends SampleBaseTestCase {

    private ProductSubgroupControllerAdmin productSubgroupControllerAdmin;

    @Mock
    private ProductSubgroupService mockProductSubgroupService;
    @Mock
    private ProductGroupService mockProductGroupService;

    @Before
    public void setUp() {
        productSubgroupControllerAdmin = new ProductSubgroupControllerAdmin(mockProductGroupService, mockProductSubgroupService);
    }

    @Test
    public void showAllProductSubgroups() {
        List<ProductSubgroup> productSubgroupList = new ArrayList<>();
        when(mockProductSubgroupService.getAllProductSubgroups()).thenReturn(productSubgroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.showAllProductSubgroups(model);

        assertTrue(model.containsAttribute("productSubgroupList"));
        assertEquals(productSubgroupList, model.asMap().get("productSubgroupList"));
        assertEquals("admin/product-subgroup/product-subgroup-list", actualViewName);
    }

    @Test
    public void showAddProductSubgroupPage() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.showAddProductSubgroupPage(model);

        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertTrue(model.containsAttribute("productSubgroup"));
        assertEquals(productSubgroup, model.asMap().get("productSubgroup"));
        assertEquals("admin/product-subgroup/add-product-subgroup", actualViewName);
    }

    @Test
    public void addProductSubgroup_BindingResultHasError_AddProductSubgroupPageReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult resultWithError = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        resultWithError.addError(new FieldError("productSubgroup", "productSubgroupName", "productSubgroupNameErrorMessage"));
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.addProductSubgroup(productSubgroup, resultWithError, model);

        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertEquals("admin/product-subgroup/add-product-subgroup", actualViewName);
    }

    @Test
    public void addProductSubgroup_BindingResultHasNoError_ProductSubgroupAddedPageReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.addProductSubgroup(productSubgroup, resultWithoutError, model);

        verify(mockProductSubgroupService).save(productSubgroup);
        assertEquals("redirect:/admin/product-subgroup/product-subgroup-added", actualViewName);
    }

    @Test
    public void addProductSubgroup_BindingResultHasNoErrorButProductSubgroupAlreadyExists_ProductSubgroupAddedPageReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult result = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        String defaultErrorMessage = "Подгруппа с таким названием уже существует. Введите другое название.";
        when(mockProductSubgroupService.isProductSubgroupAlreadyExists(productSubgroup)).thenReturn(true);
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.addProductSubgroup(productSubgroup, result, model);

        verify(mockProductSubgroupService, never()).save(productSubgroup);
        assertEquals(defaultErrorMessage, result.getFieldError("productSubgroupName").getDefaultMessage());
        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertEquals("admin/product-subgroup/add-product-subgroup", actualViewName);
    }

    @Test
    public void showProductSubgroupAddedPage() {
        assertEquals("admin/product-subgroup/product-subgroup-added", productSubgroupControllerAdmin.showProductSubgroupAddedPage());
    }

    @Test
    public void showEditProductSubgroupPage() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        int productSubgroupIdToEdit = 3;
        when(mockProductSubgroupService.findOne(productSubgroupIdToEdit)).thenReturn(productSubgroup);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.showEditProductSubgroupPage(productSubgroupIdToEdit, model);

        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertTrue(model.containsAttribute("productSubgroup"));
        assertEquals(productSubgroup, model.asMap().get("productSubgroup"));
        assertEquals("admin/product-subgroup/edit-product-subgroup", actualViewName);
    }

    @Test
    public void updateProductSubgroup_BindingResultHasError_EditProductSubgroupReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult resultWithError = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        resultWithError.addError(new FieldError("productSubgroup", "productSubgroupName", "productSubgroupNameErrorMessage"));
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.updateProductSubgroup(productSubgroup, resultWithError, model);

        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertEquals("admin/product-subgroup/edit-product-subgroup", actualViewName);
    }

    @Test
    public void updateProductSubgroup_BindingResultHasNoError_ProductSubgroupListPageReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.updateProductSubgroup(productSubgroup, resultWithoutError, model);

        verify(mockProductSubgroupService).save(productSubgroup);
        assertEquals("redirect:/admin/product-subgroup/product-subgroup-list", actualViewName);
    }

    @Test
    public void updateProductSubgroup_BindingResultHasNoErrorButProductSubgroupAlreadyExists_EditProductSubgroupReturned() {
        ProductSubgroup productSubgroup = new ProductSubgroup();
        BindingResult result = new BeanPropertyBindingResult(productSubgroup, "productSubgroup");
        String defaultErrorMessage = "Подгруппа с таким названием уже существует. Введите другое название.";
        when(mockProductSubgroupService.isProductSubgroupAlreadyExists(productSubgroup)).thenReturn(true);
        List<ProductGroup> productGroupList = new ArrayList<>();
        when(mockProductGroupService.getAllProductGroups()).thenReturn(productGroupList);
        Model model = new ExtendedModelMap();

        String actualViewName = productSubgroupControllerAdmin.updateProductSubgroup(productSubgroup, result, model);

        verify(mockProductSubgroupService, never()).save(productSubgroup);
        assertEquals(defaultErrorMessage, result.getFieldError("productSubgroupName").getDefaultMessage());
        assertTrue(model.containsAttribute("productGroupList"));
        assertEquals(productGroupList, model.asMap().get("productGroupList"));
        assertEquals("admin/product-subgroup/edit-product-subgroup", actualViewName);
    }

    @Test
    public void removeProductSubgroup() {
        int productSubgroupIdToRemove = 5;

        String actualViewName = productSubgroupControllerAdmin.removeProductSubgroup(productSubgroupIdToRemove);

        verify(mockProductSubgroupService).remove(productSubgroupIdToRemove);
        assertEquals("redirect:/admin/product-subgroup/product-subgroup-list", actualViewName);
    }
}
