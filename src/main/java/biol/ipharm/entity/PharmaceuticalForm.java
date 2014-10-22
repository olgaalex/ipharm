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
@Table(name = "pharmaceutical_form")
public class PharmaceuticalForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmaceutical_form_id")
    private Integer pharmaceuticalFormId;

    @NotNull
    @Size(min = 3, max = 45, message = "Введите не менее 3 и не более 45 символов.")
    @Column(name = "pharmaceutical_form_name")
    private String pharmaceuticalFormName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pharmaceuticalForm")
    private List<Product> productList;

    public PharmaceuticalForm() {
    }

    public PharmaceuticalForm(String pharmaceuticalFormName) {
        this.pharmaceuticalFormName = pharmaceuticalFormName;
    }

    public Integer getPharmaceuticalFormId() {
        return pharmaceuticalFormId;
    }

    public void setPharmaceuticalFormId(Integer pharmaceuticalFormId) {
        this.pharmaceuticalFormId = pharmaceuticalFormId;
    }

    public String getPharmaceuticalFormName() {
        return pharmaceuticalFormName;
    }

    public void setPharmaceuticalFormName(String pharmaceuticalFormName) {
        this.pharmaceuticalFormName = pharmaceuticalFormName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.pharmaceuticalFormName);
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
        if (!(obj instanceof PharmaceuticalForm)) {
            return false;
        }
        final PharmaceuticalForm other = (PharmaceuticalForm) obj;
        return Objects.equals(this.pharmaceuticalFormName, other.pharmaceuticalFormName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.PharmaceuticalForm[");
        sb.append("pharmaceuticalFormId=");
        sb.append(pharmaceuticalFormId);
        sb.append(", pharmaceuticalFormName=");
        sb.append(pharmaceuticalFormName);
        sb.append("]");
        return sb.toString();
    }
}
