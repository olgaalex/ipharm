package biol.ipharm.entity.formatter;

import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.service.PharmaceuticalFormService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author Olga
 */
public class PharmaceuticalFormFormatter implements Formatter<PharmaceuticalForm> {

    private PharmaceuticalFormService pharmaceuticalFormService;

    @Autowired
    public void setPharmaceuticalFormService(PharmaceuticalFormService pharmaceuticalFormService) {
        this.pharmaceuticalFormService = pharmaceuticalFormService;
    }

    @Override
    public String print(PharmaceuticalForm pharmaceuticalForm, Locale locale) {
        return pharmaceuticalForm.getPharmaceuticalFormName();
    }

    @Override
    public PharmaceuticalForm parse(String text, Locale locale) {
        final Integer pharmaceuticalFormId = Integer.valueOf(text);
        return pharmaceuticalFormService.findOne(pharmaceuticalFormId);
    }
}
