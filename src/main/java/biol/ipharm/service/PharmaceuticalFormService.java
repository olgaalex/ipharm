package biol.ipharm.service;

import biol.ipharm.entity.PharmaceuticalForm;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface PharmaceuticalFormService {

    List<PharmaceuticalForm> findAll();

    PharmaceuticalForm findOne(int id);
}
