package biol.ipharm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000, message = "Морда не треснет?")
    @Column(name = "product_quantity")
    private int productQuantity;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.01", message = "Товар не может быть дешевле 0,01 грн.")
    @DecimalMax(value = "999999.99", message = "Самое дорогое, что мы можем себе позволить, стоит 999999,99 грн.")
    @Column(name = "product_price")
    private BigDecimal productPrice;

    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne
    private TheOrder order;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ManyToOne
    private Product product;

    public OrderItem() {
    }

    public OrderItem(int productQuantity, BigDecimal productPrice, TheOrder order, Product product) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.order = order;
        this.product = product;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public TheOrder getOrder() {
        return order;
    }

    public void setOrder(TheOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.productQuantity, this.productPrice, this.order, this.product);
    }

    // In accordance with 'Effective Java' by Joshua Bloch, 2nd edition, pages 42—44.
    @Override
    public final boolean equals(Object obj) {
        // Performance optimization
        if (this == obj) {
            return true;
        }
        // Null check is unnecessary, because the instanceof operator is specified 
        // to return false if its first operand is null, regardless of what type 
        // appears in the second operand [JLS, 15.20.2].
        if (!(obj instanceof OrderItem)) {
            return false;
        }
        final OrderItem other = (OrderItem) obj;
        if (!Objects.equals(this.productQuantity, other.productQuantity)) {
            return false;
        }
        if (!Objects.equals(this.productPrice, other.productPrice)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        return Objects.equals(this.product, other.product);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.OrderItem[");
        sb.append("orderItemId=");
        sb.append(orderItemId);
        sb.append(", orderId=");
        sb.append(order.getOrderId());
        sb.append(", product=");
        sb.append(product.getProductName());
        sb.append(", productPrice=");
        sb.append(productPrice);
        sb.append("]");
        return sb.toString();
    }
}
