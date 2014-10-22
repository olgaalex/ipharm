package biol.ipharm.controller.validator;

import static biol.ipharm.util.Utils.MAX_UPLOAD_FILE_SIZE;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olga
 */
public class MultipartFileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile image = (MultipartFile) target;

        List<String> imageMimeTypes = Arrays.asList("image/gif", "image/jpeg", "image/pjpeg", "image/png");
        if (!imageMimeTypes.contains(image.getContentType())) {
            errors.rejectValue("image", "selectOnlyAllowedFileTypes", "Изображение должно быть типа jpeg, gif или png.");
            return;
        }

        if (image.getSize() > MAX_UPLOAD_FILE_SIZE) {
            errors.rejectValue("image", "fileIsTooLarge", "Файл изображения слишком большой.");
            return;
        }

        imageBytesAccessibilityCheck(image, errors);
    }

    void imageBytesAccessibilityCheck(MultipartFile image, Errors errors) {
        try {
            image.getBytes();
        } catch (IOException ex) {
            errors.rejectValue("image", "imageBytesAccessError", "Ошибка доступа к изображению. Попробуйте ещё раз.");
        }
    }
}
