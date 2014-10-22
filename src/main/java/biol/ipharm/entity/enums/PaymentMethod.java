package biol.ipharm.entity.enums;

/**
 *
 * @author Olga
 */
public enum PaymentMethod {

    CASH("Наличными"),
    CASHLESS("Банковский перевод"),
    PAY_PAL("Pay-pal");

    private final String name;

    private PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
