package biol.ipharm.order.result;

import java.text.MessageFormat;

/**
 *
 * @author Olga
 */
public class OrderAcceptedMsg implements OrderProcessingResult {

    private String title = "{0}, Ваш заказ принят.";
    private String message = "Письмо с информацией о Вашем заказе отправлено на электронный адрес {0}. "
            + "В скором времени наши менеджены перезвонят Вам и подтвердят заказ.";

    public OrderAcceptedMsg(String name, String email) {
        // http://docs.oracle.com/javase/tutorial/i18n/format/messageFormat.html
        // http://docs.oracle.com/javase/7/docs/api/index.html?java/text/MessageFormat.html
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
