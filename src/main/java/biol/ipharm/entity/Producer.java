package biol.ipharm.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "producer")
public class Producer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producer_id")
    private Integer producerId;

    @NotNull
    @Size(min = 3, max = 60, message = "Введите не менее 3 и не более 60 символов.")
    @Column(name = "producer_name")
    private String producerName;

    @NotNull
    @Size(min = 3, max = 120, message = "Введите не менее 3 и не более 120 символов.")
    @Column(name = "producer_address")
    private String producerAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producer")
    private List<Product> productList;

    public Producer() {
    }

    public Producer(String producerName, String producerAddress) {
        this.producerName = producerName;
        this.producerAddress = producerAddress;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerAddress() {
        return producerAddress;
    }

    public void setProducerAddress(String producerAddress) {
        this.producerAddress = producerAddress;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.producerName);
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
        if (!(obj instanceof Producer)) {
            return false;
        }
        final Producer other = (Producer) obj;
        return Objects.equals(this.producerName, other.producerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.Producer[");
        sb.append("producerId=");
        sb.append(producerId);
        sb.append(", producerName=");
        sb.append(producerName);
        sb.append("]");
        return sb.toString();
    }
}
