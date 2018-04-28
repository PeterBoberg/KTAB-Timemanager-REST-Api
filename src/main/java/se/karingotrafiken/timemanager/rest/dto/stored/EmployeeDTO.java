package se.karingotrafiken.timemanager.rest.dto.stored;


import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.NotNull;

public class EmployeeDTO implements DtoObject {

    private long id;

    @NotNull(message = "First name can not be null")
    @NotEmpty(message = "First name can not be empty")
    private String firstName;

    @NotNull(message = "Last name can not be null")
    @NotEmpty(message = "Last name can not be empty")
    private String lastName;

    @NotNull(message = "Personal number name can not be null")
    @NotEmpty(message = "Personal number can not be empty")
    private String personalNumber;
    private String phoneNumber;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String email;

    @NotNull(message = "User data can not be null")
    private UserDTO user;

    @NotNull(message = "Service category can not be null")
    private ServiceCategoryDTO serviceCategory;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ServiceCategoryDTO getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategoryDTO serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
