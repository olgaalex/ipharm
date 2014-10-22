package biol.ipharm.controller.validator;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.Product;
import static biol.ipharm.util.Utils.MAX_UPLOAD_FILE_SIZE;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olga
 */
public class MultipartFileValidatorTest extends SampleBaseTestCase {

    private final MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
    private final Product product = new Product();

    @Mock
    MultipartFile mockMultipartFile;

    @Test
    public void supports_MultipartFileClass_True() {
        assertTrue(multipartFileValidator.supports(MultipartFile.class));
    }

    @Test
    public void supports_NotMultipartFileClass_False() {
        assertFalse(multipartFileValidator.supports(String.class));
    }

    @Test
    public void validate_FileIsNotImage_SelectOnlyAllowedFileTypesError() {
        byte[] content = {1, 0, 1};
        MultipartFile image = new MockMultipartFile("image", "Фуфломицин.jpg", "text/html", content);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(image, result);

        assertTrue(result.hasFieldErrors("image"));
        String expectedErrorCode = "selectOnlyAllowedFileTypes";
        String expectedDefaultErrorMsg = "Изображение должно быть типа jpeg, gif или png.";
        assertEquals(expectedErrorCode, result.getFieldErrors("image").get(0).getCode());
        assertEquals(expectedDefaultErrorMsg, result.getFieldErrors("image").get(0).getDefaultMessage());
    }

    @Test
    public void validate_FileContentTypeIsGif_NoErrors() {
        byte[] content = {1, 0, 1};
        MultipartFile image = new MockMultipartFile("image", "Фуфломицин.gif", "image/gif", content);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(image, result);

        assertFalse(result.hasFieldErrors("image"));
    }

    @Test
    public void validate_FileContentTypeIsJpeg_NoErrors() {
        byte[] content = {1, 0, 1};
        MultipartFile image = new MockMultipartFile("image", "Фуфломицин.jpg", "image/jpeg", content);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(image, result);

        assertFalse(result.hasFieldErrors("image"));
    }

    @Test
    public void validate_FileContentTypeIsPjpeg_NoErrors() {
        byte[] content = {1, 0, 1};
        MultipartFile image = new MockMultipartFile("image", "Фуфломицин.pjpg", "image/pjpeg", content);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(image, result);

        assertFalse(result.hasFieldErrors("image"));
    }

    @Test
    public void validate_FileContentTypeIsPng_NoErrors() {
        byte[] content = {1, 0, 1};
        MultipartFile image = new MockMultipartFile("image", "Фуфломицин.png", "image/png", content);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(image, result);

        assertFalse(result.hasFieldErrors("image"));
    }

    @Test
    public void validate_FileSizeIsGreaterThanShouldBe_FileIsTooLargeError() {
        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("image/jpeg");
        when(mockMultipartFile.getSize()).thenReturn(MAX_UPLOAD_FILE_SIZE + 1L);
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(mockMultipartFile, result);

        assertTrue(result.hasFieldErrors("image"));
        String expectedErrorCode = "fileIsTooLarge";
        String expectedDefaultErrorMsg = "Файл изображения слишком большой.";
        assertEquals(expectedErrorCode, result.getFieldErrors("image").get(0).getCode());
        assertEquals(expectedDefaultErrorMsg, result.getFieldErrors("image").get(0).getDefaultMessage());
    }

    @Test
    public void imageBytesAccessibilityCheck() throws IOException {
        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("image/jpeg");
        when(mockMultipartFile.getSize()).thenReturn(1L);
        when(mockMultipartFile.getBytes()).thenThrow(new IOException());
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        multipartFileValidator.validate(mockMultipartFile, result);

        assertTrue(result.hasFieldErrors("image"));
        String expectedErrorCode = "imageBytesAccessError";
        String expectedDefaultErrorMsg = "Ошибка доступа к изображению. Попробуйте ещё раз.";
        assertEquals(expectedErrorCode, result.getFieldErrors("image").get(0).getCode());
        assertEquals(expectedDefaultErrorMsg, result.getFieldErrors("image").get(0).getDefaultMessage());
    }
}
