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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "product_subgroup")
public class ProductSubgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_subgroup_id")
    private Integer productSubgroupId;

    @NotNull
    @Size(min = 3, max = 120, message = "Введите не менее 3 и не более 120 символов.")
    @Column(name = "product_subgroup_name")
    private String productSubgroupName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSubgroup")
    private List<Product> productList;

    @JoinColumn(name = "product_group_id", referencedColumnName = "product_group_id")
    @ManyToOne
    private ProductGroup productGroup;

    public ProductSubgroup() {
    }

    public ProductSubgroup(String productSubgroupName, ProductGroup productGroup) {
        this.productSubgroupName = productSubgroupName;
        this.productGroup = productGroup;
    }

    public Integer getProductSubgroupId() {
        return productSubgroupId;
    }

    public void setProductSubgroupId(Integer productSubgroupId) {
        this.productSubgroupId = productSubgroupId;
    }

    public String getProductSubgroupName() {
        return productSubgroupName;
    }

    public void setProductSubgroupName(String productSubgroupName) {
        this.productSubgroupName = productSubgroupName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.productSubgroupName);
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
        if (!(obj instanceof ProductSubgroup)) {
            return false;
        }
        final ProductSubgroup other = (ProductSubgroup) obj;
        return Objects.equals(this.productSubgroupName, other.productSubgroupName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.ProductSubgroup[");
        sb.append("productSubgroupId=");
        sb.append(productSubgroupId);
        sb.append(", productSubgroupName=");
        sb.append(productSubgroupName);
        sb.append(", productGroup=");
        sb.append(productGroup.getProductGroupName());
        sb.append("]");
        return sb.toString();
    }
}
