package biol.ipharm.order.result;

import java.text.MessageFormat;

/**
 *
 * @author Olga
 */
public class OrderUnacceptedMsg implements OrderProcessingResult {

    private String title = "{0}, не удалось принять Ваш заказ.";
    private final String message = "К сожалению, в самый последний момент что-то пошло не так "
            + "и нам не удалось принять Ваш заказ. "
            + "Возможно Вы его слабо кинули. Попробуйте "
            + "сделать заказ снова и нажимать на кнопки сильнее.";

    public OrderUnacceptedMsg(String name) {
        this.title = MessageFormat.format(title, name);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
