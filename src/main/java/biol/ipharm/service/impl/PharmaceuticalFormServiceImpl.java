package biol.ipharm.service.impl;

import biol.ipharm.dao.PharmaceuticalFormDao;
import biol.ipharm.entity.PharmaceuticalForm;
import biol.ipharm.service.PharmaceuticalFormService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class PharmaceuticalFormServiceImpl implements PharmaceuticalFormService {

    private final PharmaceuticalFormDao pharmaceuticalFormDao;

    @Autowired
    public PharmaceuticalFormServiceImpl(PharmaceuticalFormDao pharmaceuticalFormDao) {
        this.pharmaceuticalFormDao = pharmaceuticalFormDao;
    }

    @Override
    public List<PharmaceuticalForm> findAll() {
        return (List<PharmaceuticalForm>) pharmaceuticalFormDao.findAll();
    }

    @Override
    public PharmaceuticalForm findOne(int id) {
        return pharmaceuticalFormDao.findOne(id);
    }
}
