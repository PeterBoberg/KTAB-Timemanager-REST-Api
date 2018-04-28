package se.karingotrafiken.timemanager.rest.testutils;

import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;

public class EmployeeTestUtils {

    public static Employee exampleEmployee() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Peter");
        employee.setLastName("Boberg");
        employee.setEmail("test@test.se");
        employee.setPersonalNumber("820822-1111");
        employee.setPhoneNumber("0707-332211");
        employee.setStreetAddress("Teststreet 5");
        employee.setPostalCode("12345");
        employee.setCity("Test city");
        employee.setServiceCategory(ServiceCategoryTestUtils.commanderCategory());
        employee.setUser(UserTestUtils.testUser());
        employee.getUser().setEmployee(employee);
        return employee;
    }

    public static EmployeeDTO exampleEmployeeDto() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setFirstName("Peter");
        employee.setLastName("Boberg");
        employee.setEmail("test@test.se");
        employee.setPersonalNumber("820822-1111");
        employee.setPhoneNumber("0707-332211");
        employee.setStreetAddress("Teststreet 5");
        employee.setPostalCode("12345");
        employee.setCity("Test city");
        employee.setServiceCategory(ServiceCategoryTestUtils.commanderCategoryDto());
        employee.setUser(UserTestUtils.newUser());
        return employee;
    }


}
