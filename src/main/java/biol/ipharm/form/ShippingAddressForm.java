package biol.ipharm.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Olga
 */
public class ShippingAddressForm {

    @Size(min = 2, max = 70, message = "Введите не менее 2 и не более 70 символов.")
    @Pattern(regexp = "\\D*")
    private String firstName;

    @Size(min = 2, max = 70, message = "Введите не менее 2 и не более 70 символов.")
    @Pattern(regexp = "\\D*")
    private String middleName;

    @Size(min = 2, max = 70, message = "Введите не менее 2 и не более 70 символов.")
    @Pattern(regexp = "\\D*")
    private String lastName;

    @NotNull
    @Size(min = 5, max = 20, message = "Введите не менее 5 и не более 20 символов.")
    private String phoneNumber;

    @Email(message = "Похоже, вы ввели не email. Попробуйте ещё раз.")
    private String email;

    @Size(min = 5, max = 300, message = "Введите не менее 5 и не более 300 символов.")
    private String address;

    @Size(max = 300, message = "Введите не более 300 символов.")
    private String comment;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
