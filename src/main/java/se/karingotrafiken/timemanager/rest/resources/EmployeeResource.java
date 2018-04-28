package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;
import se.karingotrafiken.timemanager.rest.service.employees.EmployeeService;
import se.karingotrafiken.timemanager.rest.utils.UriUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ScheduleResource scheduleResource;

    @Autowired
    private ReportResource reportResource;

    @Autowired
    private WithdrawalResource withdrawalResource;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addEmployee(@Valid EmployeeDTO employee, @Context UriInfo info) {
        EmployeeDTO newEmployee = employeeService.create(employee);
        URI atLocation = UriUtils.toUri(info, newEmployee.getId());
        return Response.created(atLocation).entity(newEmployee).build();
    }

    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") int id) {
        EmployeeDTO employee = employeeService.getById(id);
        return Response.ok().entity(employee).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateEmployee(@PathParam("id") long id, @Valid EmployeeDTO employee) {
        employee.setId(id);
        EmployeeDTO updatedEmployee = employeeService.update(employee);
        return Response.ok().entity(updatedEmployee).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deletePerson(@PathParam("id") int id) {
        employeeService.deleteById(id);
        return Response.ok().build();
    }

    @GET
    public Response getAllEmployees(@HeaderParam("Authorization") String token) {
        List<EmployeeDTO> employees = employeeService.getAll();
        return Response.ok().entity(employees).build();
    }

    @Path("/{employeeId}/scheduleddays")
    public ScheduleResource getScheduleResource() {
        return scheduleResource;
    }

    @Path("/{employeeId}/reports")
    public ReportResource getReportResource() {
        return reportResource;
    }

    @Path("/{employeeId}/withdrawals")
    public WithdrawalResource getWithdrawalResource() {
        return withdrawalResource;
    }
}
