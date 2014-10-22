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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.SafeHtml;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotNull
    @Size(min = 3, max = 45, message = "Введите не менее 3 и не более 45 символов.")
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Size(min = 3, max = 70, message = "Введите не менее 3 и не более 70 символов.")
    @Column(name = "product_international_name")
    private String productInternationalName;

    @NotNull
    @Min(value = 0, message = "Количество не должно быть отрицательным числом.")
    @Column(name = "storage_on_hand")
    private int storageOnHand;

    @Lob
    @Size(max = 65535)
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.BASIC, message = "Описание содержит потенциально опасные теги и/или атрибуты. Нажмите \"Инструменты ⟶ Исходный код\" и удалите их из текста.")
    @Column(name = "annotation")
    private String annotation;

    @NotNull
    @Min(value = 1, message = "В упаковке должна быть хотя бы одна штучка.")
    @Column(name = "quantitative_composition")
    private int quantitativeComposition;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.01", message = "Товар не может быть дешевле 0,01 грн.")
    @DecimalMax(value = "999999.99", message = "Самое дорогое, что мы можем себе позволить, стоит 999999,99 грн.")
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 45, message = "Введите не менее 3 и не более 45 символов.")
    @Column(name = "dosage")
    private String dosage;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @JoinColumn(name = "pharmaceutical_form_id", referencedColumnName = "pharmaceutical_form_id")
    @ManyToOne
    private PharmaceuticalForm pharmaceuticalForm;

    @JoinColumn(name = "producer_id", referencedColumnName = "producer_id")
    @ManyToOne
    private Producer producer;

    @JoinColumn(name = "product_subgroup_id", referencedColumnName = "product_subgroup_id")
    @ManyToOne
    private ProductSubgroup productSubgroup;

    public Product() {
    }

    public Product(
            String productName,
            String productInternationalName,
            int storageOnHand,
            int quantitativeComposition,
            BigDecimal price,
            String dosage,
            PharmaceuticalForm pharmaceuticalForm,
            Producer producer,
            ProductSubgroup productSubgroup) {
        this.productName = productName;
        this.productInternationalName = productInternationalName;
        this.storageOnHand = storageOnHand;
        this.quantitativeComposition = quantitativeComposition;
        this.price = price;
        this.dosage = dosage;
        this.pharmaceuticalForm = pharmaceuticalForm;
        this.producer = producer;
        this.productSubgroup = productSubgroup;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductInternationalName() {
        return productInternationalName;
    }

    public void setProductInternationalName(String productInternationalName) {
        this.productInternationalName = productInternationalName;
    }

    public int getStorageOnHand() {
        return storageOnHand;
    }

    public void setStorageOnHand(int storageOnHand) {
        this.storageOnHand = storageOnHand;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public int getQuantitativeComposition() {
        return quantitativeComposition;
    }

    public void setQuantitativeComposition(int quantitativeComposition) {
        this.quantitativeComposition = quantitativeComposition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public PharmaceuticalForm getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(PharmaceuticalForm pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public ProductSubgroup getProductSubgroup() {
        return productSubgroup;
    }

    public void setProductSubgroup(ProductSubgroup productSubgroup) {
        this.productSubgroup = productSubgroup;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(
                this.productName,
                this.productInternationalName,
                this.quantitativeComposition,
                this.dosage,
                this.pharmaceuticalForm,
                this.producer
        );
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
        if (!(obj instanceof Product)) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.productInternationalName, other.productInternationalName)) {
            return false;
        }
        if (!Objects.equals(this.quantitativeComposition, other.quantitativeComposition)) {
            return false;
        }
        if (!Objects.equals(this.dosage, other.dosage)) {
            return false;
        }
        if (!Objects.equals(this.pharmaceuticalForm, other.pharmaceuticalForm)) {
            return false;
        }
        return Objects.equals(this.producer, other.producer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.Product[");
        sb.append("productId=");
        sb.append(productId);
        sb.append(", productName=");
        sb.append(productName);
        sb.append(", price=");
        sb.append(price);
        sb.append("]");
        return sb.toString();
    }
}
