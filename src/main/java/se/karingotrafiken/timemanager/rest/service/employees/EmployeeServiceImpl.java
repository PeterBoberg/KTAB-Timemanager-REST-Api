package se.karingotrafiken.timemanager.rest.service.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ServiceCategoryDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.UserDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.entitys.User;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.EmployeeRepository;
import se.karingotrafiken.timemanager.rest.repository.ServiceCategoryRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.LogicalValidator;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.utils.StringUtils;

@Component
public class EmployeeServiceImpl extends AbstractStrongEntityCrudService<EmployeeDTO, Employee> implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public CrudRepository<Employee, Long> getRepository() {
        return employeeRepository;
    }

    @Transactional
    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        if (!StringUtils.isValidPassword(employeeDTO.getUser().getPassword()))
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_PASSWORD_FORMAT, "Ogiltigt lösen format");

        getLogicalValidator().validate(employeeDTO);
        try {
            Employee employee = getRepository().save(getCreateEmployeeTranslator().translateFromDTO(employeeDTO));
            return getCreateEmployeeTranslator().translateFromEntity(employee);
        } catch (DataIntegrityViolationException e) {
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_OBJECT_MANIPULATION, "Kunde inte skapa anställd med person nummer "
                    + employeeDTO.getPersonalNumber() + " efter som denne redan finns");
        }
    }


    @Override
    public Translator<EmployeeDTO, Employee> getTranslator() {
        return new UpdateEmployeeTranslator();
    }


    private Translator<EmployeeDTO, Employee> getCreateEmployeeTranslator() {
        return new CreateEmployeeTranslator();
    }

    private class UpdateEmployeeTranslator extends Translator<EmployeeDTO, Employee> {

        @Override
        public EmployeeDTO translateFromEntity(Employee employee) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(employee.getId());
            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());
            dto.setPersonalNumber(employee.getPersonalNumber());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setStreetAddress(employee.getStreetAddress());
            dto.setCity(employee.getCity());
            dto.setPostalCode(employee.getPostalCode());
            dto.setEmail(employee.getEmail());

            ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
            serviceCategoryDTO.setId(employee.getServiceCategory().getId());
            serviceCategoryDTO.setName(employee.getServiceCategory().getName());
            dto.setServiceCategory(serviceCategoryDTO);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(employee.getUser().getId());
            userDTO.setUsername(employee.getUser().getUsername());
            userDTO.setPassword("**[PROTECTED]**");
            userDTO.setRoles(employee.getUser().getRoles());
            dto.setUser(userDTO);

            return dto;
        }

        @Override
        public Employee translateFromDTO(EmployeeDTO dto) {
            Employee employee = employeeRepository.findOne(dto.getId());
            populateEmployeeFields(dto, employee);
            employee.getUser().setUsername(dto.getUser().getUsername());
            employee.getUser().setRoles(dto.getUser().getRoles());
            return employee;
        }
    }

    private class CreateEmployeeTranslator extends UpdateEmployeeTranslator {

        @Override
        public Employee translateFromDTO(EmployeeDTO dto) {
            Employee employee = new Employee();
            populateEmployeeFields(dto, employee);

            User user = new User();
            user.setUsername(dto.getUser().getUsername());
            user.setPassword(passwordEncoder.encode(dto.getUser().getPassword()));
            user.setEnabled(true);
            user.setRoles(dto.getUser().getRoles());
            user.setEmployee(employee);
            employee.setUser(user);
            return employee;
        }
    }

    private void populateEmployeeFields(EmployeeDTO dto, Employee employee) {
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPersonalNumber(dto.getPersonalNumber());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setStreetAddress(dto.getStreetAddress());
        employee.setCity(dto.getCity());
        employee.setPostalCode(dto.getPostalCode());
        employee.setEmail(dto.getEmail());

        ServiceCategory serviceCategory = serviceCategoryRepository.findOne(dto.getServiceCategory().getId());
        if (serviceCategory == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Arbetskategori med id " + dto.getServiceCategory().getId() + " existerar inte");
        employee.setServiceCategory(serviceCategory);
    }

    @Override
    public LogicalValidator<EmployeeDTO> getLogicalValidator() {
        return employeeDTO -> {
            if (employeeDTO.getEmail() != null && !StringUtils.isValidEmail(employeeDTO.getEmail()))
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_EMAIL_FORMAT, "Ogilfige epost format för " + employeeDTO.getEmail());

            if (!StringUtils.isValidPersonalNumber(employeeDTO.getPersonalNumber()))
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_PERSONAL_NUMBER, "Ogiltigt personnummer-format för " + employeeDTO.getPersonalNumber());
        };
    }
}
