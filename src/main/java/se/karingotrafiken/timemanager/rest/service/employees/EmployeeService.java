package se.karingotrafiken.timemanager.rest.service.employees;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface EmployeeService extends StrongEntityCrudService<EmployeeDTO, Employee> {
}
