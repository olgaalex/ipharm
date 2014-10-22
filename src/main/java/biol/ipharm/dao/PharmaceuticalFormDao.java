package biol.ipharm.dao;

import biol.ipharm.entity.PharmaceuticalForm;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olga
 */
public interface PharmaceuticalFormDao extends CrudRepository<PharmaceuticalForm, Integer> {

}
