package biol.ipharm.entity.formatter;

import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.service.PharmaceuticalFormService;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Olga
 */
public class PharmaceuticalFormFormatterTest {

    private final PharmaceuticalFormFormatter pharmaceuticalFormFormatter = new PharmaceuticalFormFormatter();

    @Test
    public void print() {
        String pharmaceuticalFormName = "Таблетки";
        PharmaceuticalForm pharmaceuticalForm = new PharmaceuticalForm();
        pharmaceuticalForm.setPharmaceuticalFormName(pharmaceuticalFormName);

        String actual = pharmaceuticalFormFormatter.print(pharmaceuticalForm, Locale.getDefault());

        assertEquals(pharmaceuticalFormName, actual);
    }

    @Test
    public void parse() {
        PharmaceuticalFormService mockPharmaceuticalFormService = mock(PharmaceuticalFormService.class);
        String pharmaceuticalFormId = "12";
        PharmaceuticalForm expectedPharmaceuticalForm = new PharmaceuticalForm();
        when(mockPharmaceuticalFormService.findOne(Integer.valueOf(pharmaceuticalFormId))).thenReturn(expectedPharmaceuticalForm);
        pharmaceuticalFormFormatter.setPharmaceuticalFormService(mockPharmaceuticalFormService);

        PharmaceuticalForm actual = pharmaceuticalFormFormatter.parse(pharmaceuticalFormId, Locale.getDefault());

        assertEquals(expectedPharmaceuticalForm, actual);
    }
}
