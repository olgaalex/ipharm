package biol.ipharm.controller.admin;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.entity.Producer;
import biol.ipharm.service.ProducerService;
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
public class ProducerControllerAdminTest extends SampleBaseTestCase {

    private ProducerControllerAdmin producerControllerAdmin;
    private Model model;

    @Mock
    private ProducerService mockProducerService;

    @Before
    public void setUp() {
        producerControllerAdmin = new ProducerControllerAdmin(mockProducerService);
    }

    @Test
    public void showAllProducer() {
        List<Producer> producerList = new ArrayList<>();
        when(mockProducerService.getAllProducers()).thenReturn(producerList);
        model = new ExtendedModelMap();

        String actualViewName = producerControllerAdmin.showAllProducer(model);

        assertTrue(model.containsAttribute("producerList"));
        assertEquals(producerList, model.asMap().get("producerList"));
        assertEquals("admin/producer/producer-list", actualViewName);
    }

    @Test
    public void showAddProducerPage() {
        Producer producer = new Producer();
        model = new ExtendedModelMap();

        String actualViewName = producerControllerAdmin.showAddProducerPage(model);

        assertTrue(model.containsAttribute("producer"));
        assertEquals(producer, model.asMap().get("producer"));
        assertEquals("admin/producer/add-producer", actualViewName);
    }

    @Test
    public void addProducer_BindingResultHasError_AddProducerPageReturned() {
        Producer producer = new Producer();
        BindingResult resultWithError = new BeanPropertyBindingResult(producer, "producer");
        resultWithError.addError(new FieldError("producer", "producerName", "producerNameErrorMessage"));

        String actualViewName = producerControllerAdmin.addProducer(producer, resultWithError);

        assertEquals("admin/producer/add-producer", actualViewName);
    }

    @Test
    public void addProducer_BindingResultHasNoError_ProducerAddedPageReturned() {
        Producer producer = new Producer();
        BindingResult result = new BeanPropertyBindingResult(producer, "producer");

        String actualViewName = producerControllerAdmin.addProducer(producer, result);

        verify(mockProducerService).save(producer);
        assertEquals("redirect:/admin/producer/producer-added", actualViewName);
    }

    @Test
    public void addProducer_BindingResultHasNoErrorButProducerAlreadyExists_AddProducerPageReturned() {
        Producer producer = new Producer();
        BindingResult result = new BeanPropertyBindingResult(producer, "producer");
        String defaultErrorMessage = "Производитель с таким названием уже существует. Введите другое название.";
        when(mockProducerService.isProducerAlreadyExists(producer)).thenReturn(true);

        String actualViewName = producerControllerAdmin.addProducer(producer, result);

        verify(mockProducerService, never()).save(producer);
        assertEquals(defaultErrorMessage, result.getFieldError("producerName").getDefaultMessage());
        assertEquals("admin/producer/add-producer", actualViewName);
    }

    @Test
    public void showProducerAddedPage() {
        assertEquals("admin/producer/producer-added", producerControllerAdmin.showProducerAddedPage());
    }

    @Test
    public void showEditProducerPage() {
        Producer producer = new Producer();
        int producerIdToEdit = 3;
        when(mockProducerService.findOne(producerIdToEdit)).thenReturn(producer);
        model = new ExtendedModelMap();

        String actualViewName = producerControllerAdmin.showEditProducerPage(producerIdToEdit, model);

        assertTrue(model.containsAttribute("producer"));
        assertEquals(producer, model.asMap().get("producer"));
        assertEquals("admin/producer/edit-producer", actualViewName);
    }

    @Test
    public void updateProducer_BindingResultHasError_EditProduserPageReturned() {
        Producer producer = new Producer();
        BindingResult resultWithError = new BeanPropertyBindingResult(producer, "producer");
        resultWithError.addError(new FieldError("producer", "producerName", "producerNameErrorMessage"));

        String actualViewName = producerControllerAdmin.updateProducer(producer, resultWithError);

        assertEquals("admin/producer/edit-producer", actualViewName);
    }

    @Test
    public void updateProducer_BindingResultHasNoError_ProduserListPageReturned() {
        Producer producer = new Producer();
        BindingResult resultWithoutError = new BeanPropertyBindingResult(producer, "producer");

        String actualViewName = producerControllerAdmin.updateProducer(producer, resultWithoutError);

        verify(mockProducerService).save(producer);
        assertEquals("redirect:/admin/producer/producer-list", actualViewName);
    }

    @Test
    public void updateProducer_BindingResultHasNoErrorButProducerAlreadyExists_EditProduserPageReturned() {
        Producer producer = new Producer();
        BindingResult result = new BeanPropertyBindingResult(producer, "producer");
        String defaultErrorMessage = "Производитель с таким названием уже существует. Введите другое название.";
        when(mockProducerService.isProducerAlreadyExists(producer)).thenReturn(true);

        String actualViewName = producerControllerAdmin.updateProducer(producer, result);

        verify(mockProducerService, never()).save(producer);
        assertEquals(defaultErrorMessage, result.getFieldError("producerName").getDefaultMessage());
        assertEquals("admin/producer/edit-producer", actualViewName);
    }

    @Test
    public void removeProducer() {
        int orderIdToRemove = 5;

        String actualViewName = producerControllerAdmin.removeProducer(orderIdToRemove);

        verify(mockProducerService).remove(orderIdToRemove);
        assertEquals("redirect:/admin/producer/producer-list", actualViewName);
    }
}
