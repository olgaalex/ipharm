package biol.ipharm.service.impl;

import biol.ipharm.service.ErrorHandlerService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olga
 */
@Service
public class ErrorHandlerServiceImpl implements ErrorHandlerService {

    @Override
    public String getErrorMessage(Integer errorCode, Throwable throwable) {
        if (throwable == null) {
            return httpStatusCodeDefinition(errorCode);
        }
        return errorMessageAsUnorderedHtmlList(throwable);
    }

    String httpStatusCodeDefinition(Integer errorCode) {
        if (errorCode != null) {
            switch (errorCode) {
                case 400:
                    return "Синтаксическая ошибка в запросе.";
                case 401:
                    return "Для доступа к запрашиваемому ресурсу требуется аутентификация.";
                case 403:
                    return "У вас недостаточно прав для просмотра запрашиваемого ресурса.";
                case 404:
                    return "Страница не найдена.";
                case 500:
                    return "Внутренняя ошибка сервера.";
                case 503:
                    return "Сервер временно не имеет возможности обрабатывать запрос.";
                default:
                    break;
            }
        }
        return "Видимо, что-то случилось.";
    }

    private String errorMessageAsUnorderedHtmlList(Throwable throwable) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append(getMessageFromThrowableAsHtmlListItem(throwable));
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        return errorMessage.toString();
    }

    private String getMessageFromThrowableAsHtmlListItem(Throwable throwable) {
        if (throwable.getMessage() != null) {
            return "<li>" + escapeTags(throwable.getMessage()) + "</li>";
        }
        return "";
    }

    String escapeTags(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
