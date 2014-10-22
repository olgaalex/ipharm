package biol.ipharm.entity.enums;

/**
 *
 * @author Olga
 */
public enum ShippingMethod {

    SELF_DELIVERY("Самовывоз"),
    COURIER("Курьером"),
    POST("Почтой");

    private final String name;

    private ShippingMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
