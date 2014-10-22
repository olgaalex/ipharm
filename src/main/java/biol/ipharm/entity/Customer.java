package biol.ipharm.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Olga
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @NotNull
    @Size(min = 6, max = 45, message = "Логин должен быть не короче 6 и не длиннее 45 символов.")
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(min = 6, max = 45, message = "Пароль должен быть не короче 6 и не длиннее 45 символов.")
    @Column(name = "password")
    private String password;

    @NotNull
    @Email(message = "Похоже, вы ввели не email. Попробуйте ещё раз.")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 2, max = 70, message = "Введите не менее 2 и не более 70 символов.")
    @Pattern(regexp = "\\D*")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 70, message = "Введите не более 70 символов.")
    @Pattern(regexp = "\\D*")
    @Column(name = "middle_name")
    private String middleName;

    @Size(max = 70, message = "Введите не более 70 символов.")
    @Pattern(regexp = "\\D*")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Size(min = 5, max = 20, message = "Введите не менее 5 и не более 20 символов.")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Size(min = 5, max = 300, message = "Введите не менее 5 и не более 300 символов.")
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<TheOrder> orderList;

    public Customer() {
    }

    public Customer(String login, String password, String email, String firstName, String phoneNumber, String address) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<TheOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<TheOrder> orderList) {
        this.orderList = orderList;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.login);
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
        if (!(obj instanceof Customer)) {
            return false;
        }
        final Customer other = (Customer) obj;
        return Objects.equals(this.login, other.login);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("biol.ipharm.entity.Customer[");
        sb.append("customerId=");
        sb.append(customerId);
        sb.append(", login=");
        sb.append(login);
        sb.append(", email=");
        sb.append(email);
        sb.append("]");
        return sb.toString();
    }
}
