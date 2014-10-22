package biol.ipharm.order.result;

import java.text.MessageFormat;

/**
 *
 * @author Olga
 */
public class OrderAcceptedButEmailNotSentMsg implements OrderProcessingResult {

    private String title = "{0}, Ваш заказ принят. Не получилось отправить Вам e-mail об этом.";
    private String message = "Мы знаем, что Вы сделали заказ, но по каким-то причинам "
            + "не получилось отправить Вам письмо с информацией о нём "
            + "на электронный адрес {0}. "
            + "Наши менеджены обязательно позвонят Вам и подтвердят заказ.";

    public OrderAcceptedButEmailNotSentMsg(String name, String email) {
        this.title = MessageFormat.format(title, name);
        this.message = MessageFormat.format(message, email);
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
