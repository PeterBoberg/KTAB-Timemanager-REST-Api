package se.karingotrafiken.timemanager.rest.service.employees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.repository.EmployeeRepository;
import se.karingotrafiken.timemanager.rest.repository.ServiceCategoryRepository;
import se.karingotrafiken.timemanager.rest.testutils.EmployeeTestUtils;
import se.karingotrafiken.timemanager.rest.testutils.ServiceCategoryTestUtils;

import static org.assertj.core.api.Java6Assertions.*;
import static org.mockito.Matchers.*;


@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestConfig {

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ServiceCategoryRepository serviceCategoryRepository;

    @Before
    public void setUp() throws Exception {
        Employee existingEmpl = EmployeeTestUtils.exampleEmployee();
        // Encode default password before test
        existingEmpl.getUser().setPassword(passwordEncoder.encode(existingEmpl.getUser().getPassword()));

        ServiceCategory existingCategory = ServiceCategoryTestUtils.commanderCategory();

        Mockito.when(serviceCategoryRepository.findOne(existingCategory.getId()))
                .thenReturn(existingCategory);

        Mockito.when(employeeRepository.save((Employee) anyObject())).thenReturn(existingEmpl);
        Mockito.when(employeeRepository.findOne(existingEmpl.getId())).thenReturn(existingEmpl);
    }

    @Test
    public void testFindEmployeeById() {
        // Given
        Employee savedEmployee = EmployeeTestUtils.exampleEmployee();

        // When
        EmployeeDTO found = employeeService.getById(savedEmployee.getId());

        //Then
        assertThat(found.getId()).isEqualTo(savedEmployee.getId());
        assertThat(found.getFirstName()).isEqualTo(savedEmployee.getFirstName());
    }

    @Test
    public void testSaveNewEmployee() {
        // Given
        Employee employeeToBeReturned = EmployeeTestUtils.exampleEmployee();

        // When
        EmployeeDTO willBeSaved = EmployeeTestUtils.exampleEmployeeDto();
        EmployeeDTO savedEmployee = employeeService.create(willBeSaved);

        // Then
        assertThat(willBeSaved.getId()).isEqualTo(0);
        assertThat(savedEmployee.getId()).isEqualTo(employeeToBeReturned.getId());
    }


    @Test
    public void testUpdateEmployee() {
        // Given
        Employee employeeReturnedFromUpdate = EmployeeTestUtils.exampleEmployee();
        employeeReturnedFromUpdate.setLastName("Olsson");
        Mockito.when(serviceCategoryRepository.findOne(anyLong())).thenReturn(ServiceCategoryTestUtils.commanderCategory());
        Mockito.when(employeeRepository.exists(anyLong())).thenReturn(true);
        Mockito.when(employeeRepository.findOne(anyLong())).thenReturn(employeeReturnedFromUpdate);
        Mockito.when(employeeRepository.save((Employee) anyObject())).thenReturn(employeeReturnedFromUpdate);

        // When
        EmployeeDTO willBeUpdated = EmployeeTestUtils.exampleEmployeeDto();
        willBeUpdated.setLastName("Olsson");
        EmployeeDTO updated = employeeService.update(willBeUpdated);

        //Then
        assertThat(updated.getLastName()).isEqualTo(employeeReturnedFromUpdate.getLastName());
    }

    @After
    public void tearDown() throws Exception {
    }
}