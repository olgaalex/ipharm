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
@Table(name = "product_group")
public class ProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_group_id")
    private Integer productGroupId;

    @NotNull
    @Size(min = 3, max = 60, message = "Введите не менее 3 и не более 60 символов.")
    @Column(name = "product_group_name")
    private String productGroupName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productGroup")
    private List<ProductSubgroup> productSubgroupList;

    public ProductGroup() {
    }

    public ProductGroup(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public Integer getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Integer productGroupId) {
        this.productGroupId = productGroupId;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public List<ProductSubgroup> getProductSubgroupList() {
        return productSubgroupList;
    }

    public void setProductSubgroupList(List<ProductSubgroup> productSubgroupList) {
        this.productSubgroupList = productSubgroupList;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.productGroupName);
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
        if (!(obj instanceof ProductGroup)) {
            return false;
        }
        final ProductGroup other = (ProductGroup) obj;
        return Objects.equals(this.productGroupName, other.productGroupName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.ProductGroup[");
        sb.append("productGroupId=");
        sb.append(productGroupId);
        sb.append(", productGroupName=");
        sb.append(productGroupName);
        sb.append("]");
        return sb.toString();
    }
}
