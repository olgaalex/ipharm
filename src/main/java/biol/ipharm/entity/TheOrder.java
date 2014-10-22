package biol.ipharm.entity;

import biol.ipharm.entity.enums.PaymentMethod;
import biol.ipharm.entity.enums.ShippingMethod;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "the_order")
public class TheOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customer;

    @Column(name = "payment_method")
    @NotNull
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_method")
    @NotNull
    private ShippingMethod shippingMethod;

    @Column(name = "comment")
    @Size(max = 300, message = "Введите не более 300 символов.")
    private String comment;

    public TheOrder() {
    }

    public TheOrder(Customer customer, PaymentMethod paymentMethod, ShippingMethod shippingMethod) {
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    // In accordance with 'Effective Java' by Joshua Bloch, 2nd edition, pages 184—188.
    // http://www.informit.com/articles/article.aspx?p=31551&seqNum=2
    public Date getOrderDate() {
        return (Date) orderDate.clone();
    }

    // In accordance with 'Effective Java' by Joshua Bloch, 2nd edition, pages 184—188.
    // http://www.informit.com/articles/article.aspx?p=31551&seqNum=2
    public void setOrderDate(Date orderDate) {
        this.orderDate = new Date(orderDate.getTime());
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.orderDate, this.customer, this.paymentMethod, this.shippingMethod);
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
        if (!(obj instanceof TheOrder)) {
            return false;
        }
        final TheOrder other = (TheOrder) obj;
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.paymentMethod, other.paymentMethod)) {
            return false;
        }
        return Objects.equals(this.shippingMethod, other.shippingMethod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.TheOrder[");
        sb.append("orderId=");
        sb.append(orderId);
        sb.append(", customer=");
        sb.append(customer);
        sb.append(", paymentMethod=");
        sb.append(paymentMethod);
        sb.append(", shippingMethod=");
        sb.append(shippingMethod);
        sb.append("]");
        return sb.toString();
    }
}
