package biol.ipharm.controller;

import biol.ipharm.service.ErrorHandlerService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Olga
 */
@Controller
public class ErrorHandlerController {

    private final ErrorHandlerService errorHandlerService;

    @Autowired
    public ErrorHandlerController(ErrorHandlerService errorHandlerService) {
        this.errorHandlerService = errorHandlerService;
    }

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        final Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        model.addAttribute("errorCode", errorCode == null ? "" : errorCode);

        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        model.addAttribute("errorMessage", errorHandlerService.getErrorMessage(errorCode, throwable));

        return "error";
    }

    @RequestMapping("/simulateError")
    public void simulateError() {
        throw new RuntimeException("Это самодельный RuntimeException");
    }
}
