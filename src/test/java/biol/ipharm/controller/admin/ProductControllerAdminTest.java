package biol.ipharm.controller.admin;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.controller.validator.MultipartFileValidator;
import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.entity.Producer;
import biol.ipharm.entity.Product;
import biol.ipharm.entity.ProductSubgroup;
import biol.ipharm.service.PharmaceuticalFormService;
import biol.ipharm.service.ProducerService;
import biol.ipharm.service.ProductService;
import biol.ipharm.service.ProductSubgroupService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olga
 */
public class ProductControllerAdminTest extends SampleBaseTestCase {

    private ProductControllerAdmin productControllerAdmin;

    @Mock
    private ProductService mockProductService;
    @Mock
    private PharmaceuticalFormService mockPharmaceuticalFormService;
    @Mock
    private ProducerService mockProducerService;
    @Mock
    private ProductSubgroupService mockProductSubgroupService;

    @Before
    public void setUp() {
        productControllerAdmin = spy(new ProductControllerAdmin(
                mockProductService, mockPharmaceuticalFormService,
                mockProducerService, mockProductSubgroupService));
    }

    @Test
    public void showProductListPage() {
        List<Product> productList = new ArrayList<>();
        when(mockProductService.getProductList()).thenReturn(productList);
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.showProductListPage(model);

        assertTrue(model.containsAttribute("productList"));
        assertEquals(productList, model.asMap().get("productList"));
        assertEquals("admin/product/product-list", actualViewName);
    }

    @Test
    public void showAddProductPage() {
        Product product = new Product();
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.showAddProductPage(model);

        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.asMap().get("product"));
        assertEquals("admin/product/add-product", actualViewName);
    }

    @Test
    public void setDropdownValuesInto() {
        List<PharmaceuticalForm> pharmaceuticalFormList = new ArrayList<>();
        when(mockPharmaceuticalFormService.findAll()).thenReturn(pharmaceuticalFormList);
        List<ProductSubgroup> productSubgroupList = new ArrayList<>();
        when(mockProductSubgroupService.getAllProductSubgroups()).thenReturn(productSubgroupList);
        List<Producer> producerList = new ArrayList<>();
        when(mockProducerService.getAllProducers()).thenReturn(producerList);
        Model model = new ExtendedModelMap();

        productControllerAdmin.setDropdownValuesInto(model);

        assertTrue(model.containsAttribute("pharmaceuticalFormList"));
        assertEquals(pharmaceuticalFormList, model.asMap().get("pharmaceuticalFormList"));
        assertTrue(model.containsAttribute("productSubgroupList"));
        assertEquals(productSubgroupList, model.asMap().get("productSubgroupList"));
        assertTrue(model.containsAttribute("producerList"));
        assertEquals(producerList, model.asMap().get("producerList"));
    }

    @Test
    public void addProduct_ImageIsEmptyResultHasNotErrors_RedirectProductAddedPageReturned() throws IOException {
        Product product = new Product();
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.addProduct(model, product, result, image);

        assertNull(product.getImage());
        verify(mockProductService).save(product);
        assertEquals("redirect:/admin/product/product-added", actualViewName);
    }

    @Test
    public void addProduct_ImageIsEmptyResultHasErrors_AddProductPageReturned() throws IOException {
        Product product = new Product();
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        result.addError(new FieldError("product", "productName", "ErrorMessage"));
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.addProduct(model, product, result, image);

        assertNull(product.getImage());
        verify(productControllerAdmin).setDropdownValuesInto(model);
        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals("admin/product/add-product", actualViewName);
    }

    @Test
    public void addProduct_ImageIsNotEmptyResultHasNotErrors_RedirectProductAddedPageReturned() throws IOException {
        Product product = new Product();
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        byte[] imageContent = {0, 1, 1, 0, 1, 0, 0};
        when(image.getBytes()).thenReturn(imageContent);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        Model model = new ExtendedModelMap();
        MultipartFileValidator mockImageValidator = mock(MultipartFileValidator.class);
        doReturn(mockImageValidator).when(productControllerAdmin).getMultipartFileValidator();

        String actualViewName = productControllerAdmin.addProduct(model, product, result, image);

        verify(mockImageValidator).validate(image, result);
        assertArrayEquals(imageContent, product.getImage());
        verify(mockProductService).save(product);
        assertEquals("redirect:/admin/product/product-added", actualViewName);
    }

    @Test
    public void addProduct_ImageIsNotEmptyResultHasErrors_AddProductPageReturned() throws IOException {
        Product product = new Product();
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        byte[] imageContent = {0, 1, 1, 0, 1, 0, 0};
        when(image.getBytes()).thenReturn(imageContent);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        result.addError(new FieldError("product", "image", "ErrorMessage"));
        Model model = new ExtendedModelMap();
        MultipartFileValidator mockImageValidator = mock(MultipartFileValidator.class);
        doReturn(mockImageValidator).when(productControllerAdmin).getMultipartFileValidator();

        String actualViewName = productControllerAdmin.addProduct(model, product, result, image);

        verify(mockImageValidator).validate(image, result);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals("admin/product/add-product", actualViewName);
    }

    @Test
    public void addProduct_ImageIsEmptyBindingResultHasNoErrorButProductAlreadyExists_AddProductPageReturned() throws IOException {
        Product product = new Product();
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        Model model = new ExtendedModelMap();
        String defaultErrorMessage = "Товар с такими характеристиками уже существует. Введите другие данные.";
        when(mockProductService.isProductAlreadyExists(product)).thenReturn(true);

        String actualViewName = productControllerAdmin.addProduct(model, product, result, image);

        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals(defaultErrorMessage, result.getFieldError("productName").getDefaultMessage());
        assertEquals("admin/product/add-product", actualViewName);
    }

    @Test
    public void showProductAddedPage() {
        assertEquals("admin/product/product-added", productControllerAdmin.showProductAddedPage());
    }

    @Test
    public void showEditProductPage() {
        Product product = new Product();
        int productIdToEdit = 3;
        when(mockProductService.getProduct(productIdToEdit)).thenReturn(product);
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.showEditProductPage(productIdToEdit, model);

        assertTrue(model.containsAttribute("product"));
        assertEquals(product, model.asMap().get("product"));
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals("admin/product/edit-product", actualViewName);
    }

    @Test
    public void updateProduct_ImageIsEmptyResultHasNotErrors_RedirectProductListPageReturned() throws IOException {
        Product product = new Product();
        int productId = 3;
        product.setProductId(productId);
        byte[] existingImageContent = {0, 1, 1, 0, 1, 0, 0};
        product.setImage(existingImageContent);
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        when(mockProductService.getProduct(productId)).thenReturn(product);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        when(mockProductService.isProductChanged(product)).thenReturn(true);
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.updateProduct(model, product, result, image);

        assertArrayEquals(existingImageContent, product.getImage());
        verify(mockProductService).save(product);
        assertEquals("redirect:/admin/product/product-list", actualViewName);
    }

    @Test
    public void updateProduct_ImageIsEmptyResultHasErrors_EditProductPageReturned() throws IOException {
        Product product = new Product();
        int productId = 3;
        product.setProductId(productId);
        byte[] existingImageContent = {0, 1, 1, 0, 1, 0, 0};
        product.setImage(existingImageContent);
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        when(mockProductService.getProduct(productId)).thenReturn(product);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        result.addError(new FieldError("product", "productName", "ErrorMessage"));
        Model model = new ExtendedModelMap();

        String actualViewName = productControllerAdmin.updateProduct(model, product, result, image);

        assertArrayEquals(existingImageContent, product.getImage());
        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals("admin/product/edit-product", actualViewName);
    }

    @Test
    public void updateProduct_ImageIsNotEmptyResultHasNotErrors_RedirectProductListPageReturned() throws IOException {
        Product product = new Product();
        int productId = 3;
        product.setProductId(productId);
        byte[] existingImageContent = {0, 1, 1, 0, 1, 0, 0};
        product.setImage(existingImageContent);
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        byte[] newImageContent = {1, 0, 0, 1, 0, 1, 1};
        when(image.getBytes()).thenReturn(newImageContent);
        when(mockProductService.getProduct(productId)).thenReturn(product);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        Model model = new ExtendedModelMap();
        MultipartFileValidator mockImageValidator = mock(MultipartFileValidator.class);
        doReturn(mockImageValidator).when(productControllerAdmin).getMultipartFileValidator();
        when(mockProductService.isProductChanged(product)).thenReturn(true);

        String actualViewName = productControllerAdmin.updateProduct(model, product, result, image);

        verify(mockImageValidator).validate(image, result);
        assertArrayEquals(newImageContent, product.getImage());
        verify(mockProductService).save(product);
        assertEquals("redirect:/admin/product/product-list", actualViewName);
    }

    @Test
    public void updateProduct_ImageIsNotEmptyResultHasErrors_EditProductPageReturned() throws IOException {
        Product product = new Product();
        int productId = 3;
        product.setProductId(productId);
        byte[] existingImageContent = {0, 1, 1, 0, 1, 0, 0};
        product.setImage(existingImageContent);
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        byte[] newImageContent = {1, 0, 0, 1, 0, 1, 1};
        when(image.getBytes()).thenReturn(newImageContent);
        when(mockProductService.getProduct(productId)).thenReturn(product);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        result.addError(new FieldError("product", "image", "ErrorMessage"));
        Model model = new ExtendedModelMap();
        MultipartFileValidator mockImageValidator = mock(MultipartFileValidator.class);
        doReturn(mockImageValidator).when(productControllerAdmin).getMultipartFileValidator();

        String actualViewName = productControllerAdmin.updateProduct(model, product, result, image);

        verify(mockImageValidator).validate(image, result);
        assertArrayEquals(newImageContent, product.getImage());
        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals("admin/product/edit-product", actualViewName);
    }

    @Test
    public void updateProduct_ImageIsEmptyBindingResultHasNoErrorAndProductNotChanged_EditProductPageReturned() throws IOException {
        Product product = new Product();
        int productId = 3;
        product.setProductId(productId);
        byte[] existingImageContent = {0, 1, 1, 0, 1, 0, 0};
        product.setImage(existingImageContent);
        MultipartFile image = mock(MockMultipartFile.class);
        when(image.isEmpty()).thenReturn(true);
        when(mockProductService.getProduct(productId)).thenReturn(product);
        BindingResult result = new BeanPropertyBindingResult(product, "product");
        Model model = new ExtendedModelMap();
        String defaultErrorMessage = "Товар с такими характеристиками уже существует. Введите другие данные.";
        when(mockProductService.isProductChanged(product)).thenReturn(false);

        String actualViewName = productControllerAdmin.updateProduct(model, product, result, image);

        verify(mockProductService, never()).save(product);
        verify(productControllerAdmin).setDropdownValuesInto(model);
        assertEquals(defaultErrorMessage, result.getFieldError("productName").getDefaultMessage());
        assertEquals("admin/product/edit-product", actualViewName);
    }

    @Test
    public void removeProduct() {
        int productIdToRemove = 5;

        String actualViewName = productControllerAdmin.removeProduct(productIdToRemove);

        verify(mockProductService).remove(productIdToRemove);
        assertEquals("redirect:/admin/product/product-list", actualViewName);
    }
}
