package biol.ipharm.service.impl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olga
 */
public class ErrorHandlerServiceImplTest {

    private final ErrorHandlerServiceImpl errorHandlerService = new ErrorHandlerServiceImpl();

    @Test
    public void getErrorMessage_HttpCodeIsNullAndThrowableIsNull_DefaultErrorMessage() {
        Integer errorCode = null;
        Throwable throwable = null;

        assertEquals("Видимо, что-то случилось.", errorHandlerService.getErrorMessage(errorCode, throwable));
    }

    @Test
    public void getErrorMessage_HttpCodeIs503StringAndThrowableHasNoMessage_EmptyUnorderedHtmlList() {
        Integer errorCode = 503;
        Throwable throwable = new Throwable();

        assertEquals("<ul></ul>", errorHandlerService.getErrorMessage(errorCode, throwable));
    }

    @Test
    public void getErrorMessage_HttpCodeIs404AndThrowableHasMessage_UnorderedHtmlListWithMessage() {
        Integer errorCode = 404;
        Throwable throwable = new Throwable("Error message");

        assertEquals("<ul><li>Error message</li></ul>", errorHandlerService.getErrorMessage(errorCode, throwable));
    }

    @Test
    public void getErrorMessage_HttpCodeIs403EmptyStringAndThrowableHasInnerThrowable_UnorderedHtmlListWithMessages() {
        Integer errorCode = 403;
        Throwable innerThrowable = new Throwable("Inner error message");
        Throwable outerThrowable = new Throwable("Outer error message", innerThrowable);
        String expectedMessage = "<ul><li>Outer error message</li><li>Inner error message</li></ul>";

        assertEquals(expectedMessage, errorHandlerService.getErrorMessage(errorCode, outerThrowable));
    }

    @Test
    public void httpStatusCodeDefinition() {
        assertEquals("Синтаксическая ошибка в запросе.", errorHandlerService.httpStatusCodeDefinition(400));
        assertEquals("Для доступа к запрашиваемому ресурсу требуется аутентификация.", errorHandlerService.httpStatusCodeDefinition(401));
        assertEquals("У вас недостаточно прав для просмотра запрашиваемого ресурса.", errorHandlerService.httpStatusCodeDefinition(403));
        assertEquals("Страница не найдена.", errorHandlerService.httpStatusCodeDefinition(404));
        assertEquals("Внутренняя ошибка сервера.", errorHandlerService.httpStatusCodeDefinition(500));
        assertEquals("Сервер временно не имеет возможности обрабатывать запрос.", errorHandlerService.httpStatusCodeDefinition(503));
        assertEquals("Видимо, что-то случилось.", errorHandlerService.httpStatusCodeDefinition(111));
    }

    @Test
    public void escapeTags_NullPassed_NullReturned() {
        assertNull(errorHandlerService.escapeTags(null));
    }

    @Test
    public void escapeTags_EmptyStringPassed_EmptyStringReturned() {
        assertEquals("", errorHandlerService.escapeTags(""));
    }

    @Test
    public void escapeTags_StringWithoutAngleBracketsPassed_UnmodifiedStringReturned() {
        String expected = "String without angle brackets";

        String actual = errorHandlerService.escapeTags(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void escapeTags_StringWithAngleBracketsPassed_StringWithEscapedAngleBracketsReturned() {
        String expected = "&lt;b&gt;String with angle brackets&lt;b&gt;";

        String actual = errorHandlerService.escapeTags(expected);

        assertEquals(expected, actual);
    }
}
