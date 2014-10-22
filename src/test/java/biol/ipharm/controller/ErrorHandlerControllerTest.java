package biol.ipharm.controller;

import biol.ipharm.SampleBaseTestCase;
import biol.ipharm.service.ErrorHandlerService;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 *
 * @author Olga
 */
public class ErrorHandlerControllerTest extends SampleBaseTestCase {

    private ErrorHandlerController errorHandlerController;

    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private ErrorHandlerService mockErrorHandlerService;

    @Before
    public void setUp() {
        errorHandlerController = new ErrorHandlerController(mockErrorHandlerService);
    }

    @Test
    public void error_EmptyStatusCodeInRequest_EmptyErrorCode() {
        Model model = new ExtendedModelMap();

        String actualViewName = errorHandlerController.error(mockHttpServletRequest, model);

        assertTrue(model.containsAttribute("errorCode"));
        assertEquals("", model.asMap().get("errorCode"));
        assertEquals("error", actualViewName);
    }

    @Test
    public void error_403StatusCodeInRequest_StatusCodeFromRequest() {
        when(mockHttpServletRequest.getAttribute("javax.servlet.error.status_code")).thenReturn(403);
        Model model = new ExtendedModelMap();

        String actualViewName = errorHandlerController.error(mockHttpServletRequest, model);

        assertTrue(model.containsAttribute("errorCode"));
        assertEquals(403, model.asMap().get("errorCode"));
        assertEquals("error", actualViewName);
    }

    @Test
    public void error_404StatusCodeInRequest_ErrorMessageFromThrowable() {
        Model model = new ExtendedModelMap();
        Throwable throwable = new Exception("Error message");
        Integer errorCode = 404;
        String expectedErrorMessage = "<ul><li>Error message</li></ul>";
        when(mockHttpServletRequest.getAttribute("javax.servlet.error.status_code")).thenReturn(errorCode);
        when(mockHttpServletRequest.getAttribute("javax.servlet.error.exception")).thenReturn(throwable);
        when(mockErrorHandlerService.getErrorMessage(errorCode, throwable)).thenReturn(expectedErrorMessage);

        String actualViewName = errorHandlerController.error(mockHttpServletRequest, model);

        assertTrue(model.containsAttribute("errorCode"));
        assertEquals(404, model.asMap().get("errorCode"));
        assertTrue(model.containsAttribute("errorMessage"));
        assertEquals(expectedErrorMessage, model.asMap().get("errorMessage"));
        assertEquals("error", actualViewName);
    }

    @Test(expected = Throwable.class)
    public void simulateError() {
        errorHandlerController.simulateError();
    }
}
