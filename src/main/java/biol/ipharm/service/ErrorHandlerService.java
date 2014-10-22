package biol.ipharm.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public interface ErrorHandlerService {

    String getErrorMessage(Integer errorCode, Throwable throwable);

}
