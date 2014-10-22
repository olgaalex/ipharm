package biol.ipharm.order.result;

/**
 *
 * @author Olga
 */
public class UpdateOrderProcessingResultPageMsg implements OrderProcessingResult {

    private final String title = "Запрашиваемая Вами страница еще или уже не существует. Почему так произошло?";
    private final String message = "Возможно Вы перешли на страницу по сохраненной ссылке "
            + "или просто обновили страницу. "
            + "Поскольку Ваша корзина еще или уже не содержит данных о заказе, то это 'ничего' невозможно обработать. "
            + "Именно поэтому вы сейчас видите то, что видите.";

    public UpdateOrderProcessingResultPageMsg() {
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
