package biol.ipharm.controller.admin;

import biol.ipharm.entity.Producer;
import biol.ipharm.service.ProducerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Olga
 */
@Controller
public class ProducerControllerAdmin {

    private static final String REDIRECT = "redirect:/";
    private static final String ADMIN__PRODUCER_LIST_PAGE = "admin/producer/producer-list";
    private static final String ADMIN__REDIRECT_PRODUCER_LIST_PAGE = REDIRECT + ADMIN__PRODUCER_LIST_PAGE;
    private static final String ADMIN__ADD_PRODUCER_PAGE = "admin/producer/add-producer";
    private static final String ADMIN__PRODUCER_ADDED_PAGE = "admin/producer/producer-added";
    private static final String ADMIN__REDIRECT_PRODUCER_ADDED_PAGE = REDIRECT + ADMIN__PRODUCER_ADDED_PAGE;
    private static final String ADMIN__EDIT_PRODUCER_PAGE = "admin/producer/edit-producer";

    private final ProducerService producerService;

    @Autowired
    public ProducerControllerAdmin(ProducerService producerService) {
        this.producerService = producerService;
    }

    @RequestMapping(value = "/admin/producer/producer-list", method = RequestMethod.GET)
    public String showAllProducer(Model model) {
        model.addAttribute("producerList", producerService.getAllProducers());
        return ADMIN__PRODUCER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/producer/add-producer", method = RequestMethod.GET)
    public String showAddProducerPage(Model model) {
        model.addAttribute("producer", new Producer());
        return ADMIN__ADD_PRODUCER_PAGE;
    }

    @RequestMapping(value = "/admin/producer/producer-added", method = RequestMethod.POST)
    public String addProducer(@Valid final Producer producer, final BindingResult result) {
        if (result.hasErrors()) {
            return ADMIN__ADD_PRODUCER_PAGE;
        }
        if (producerService.isProducerAlreadyExists(producer)) {
            result.rejectValue("producerName", "theSameProducerAlreadyExists", "Производитель с таким названием уже существует. Введите другое название.");
            return ADMIN__ADD_PRODUCER_PAGE;
        }
        producerService.save(producer);
        return ADMIN__REDIRECT_PRODUCER_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/producer/producer-added", method = RequestMethod.GET)
    public String showProducerAddedPage() {
        return ADMIN__PRODUCER_ADDED_PAGE;
    }

    @RequestMapping(value = "/admin/producer/edit-producer", method = RequestMethod.GET)
    public String showEditProducerPage(@RequestParam("producerIdToEdit") int producerIdToEdit, Model model) {
        model.addAttribute("producer", producerService.findOne(producerIdToEdit));
        return ADMIN__EDIT_PRODUCER_PAGE;
    }

    @RequestMapping(value = "/admin/producer/update-producer", method = RequestMethod.POST)
    public String updateProducer(@Valid final Producer producer, final BindingResult result) {
        if (result.hasErrors()) {
            return ADMIN__EDIT_PRODUCER_PAGE;
        }
        if (producerService.isProducerAlreadyExists(producer)) {
            result.rejectValue("producerName", "theSameProducerAlreadyExists", "Производитель с таким названием уже существует. Введите другое название.");
            return ADMIN__EDIT_PRODUCER_PAGE;
        }
        producerService.save(producer);
        return ADMIN__REDIRECT_PRODUCER_LIST_PAGE;
    }

    @RequestMapping(value = "/admin/producer/remove-producer", method = RequestMethod.GET)
    public String removeProducer(@RequestParam("producerIdToRemove") int producerIdToRemove) {
        producerService.remove(producerIdToRemove);
        return ADMIN__REDIRECT_PRODUCER_LIST_PAGE;
    }
}
