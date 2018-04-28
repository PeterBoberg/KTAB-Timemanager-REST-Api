package se.karingotrafiken.timemanager.rest.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.ChangeScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.CommentRequestDTO;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SchedulingRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SwapScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.CheckScheduleResponse;
import se.karingotrafiken.timemanager.rest.dto.stored.ScheduledDayDTO;
import se.karingotrafiken.timemanager.rest.service.schedules.ScheduleService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This class is a sub resource to EmployeeResource
 * Path: {apiBase}/employees/{employeeId}/scheduleddays
 */

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @Autowired
    private ScheduleService scheduleService;

    @GET
    public Response getScheduleDaysByYearAndMonth(@PathParam("employeeId") long employeeId, @QueryParam("year") int year, @QueryParam("month") int month) {
        List<ScheduledDayDTO> scheduledDayDTOList = scheduleService.getByEmployeeIdAndYearAndMonth(employeeId, year, month);
        GenericEntity<List<ScheduledDayDTO>> genericEntity = new GenericEntity<List<ScheduledDayDTO>>(scheduledDayDTOList) {
        };
        return Response.ok().entity(genericEntity).build();
    }

    @POST
    @Path("/schedule")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response scheduleEmployee(@Valid SchedulingRequest schedulingRequest, @PathParam("employeeId") long employeeId) {
        schedulingRequest.setEmployeeId(employeeId);
        scheduleService.createScheduleFrom(schedulingRequest);
        return Response.ok().build();
    }

    @GET
    @Path("/check")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response checkIfScheduledInPeriod(@PathParam("employeeId") long employeeId, @QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate) {
        CheckScheduleResponse response = scheduleService.checkScheduledDaysBetween(fromDate, toDate, employeeId);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getScheduledDayById(@PathParam("id") long id) {
        ScheduledDayDTO scheduledDayDTO = scheduleService.getById(id);
        return Response.ok().entity(scheduledDayDTO).build();
    }

    @PUT
    @Path("/swap")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response swapScheduledDates(@PathParam("employeeId") long askingEmployeeId, @Valid SwapScheduledDayRequest swapDayRequest) {
        swapDayRequest.setAskingEmployeeId(askingEmployeeId);
        scheduleService.swapScheduledDates(swapDayRequest);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateScheduledDay(@PathParam("id") long id, @Valid ChangeScheduledDayRequest changeRequest) {
        ScheduledDayDTO changedDay = scheduleService.updateScheduledDay(id, changeRequest);
        return Response.ok().entity(changedDay).build();
    }

    @PUT
    @Path("/{id}/comment")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_BOAT_MANAGER')")
    public Response addSeparateCommentToScheduledDAy(@PathParam("employeeId") long employeeId, @PathParam("id") long id, CommentRequestDTO commentRequest) {
        commentRequest.setScheduledDayId(id);
        ScheduledDayDTO dayWithAddedComment = scheduleService.addCommentForDay(employeeId, commentRequest);
        return Response.ok().entity(dayWithAddedComment).build();
    }

    @POST
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response resetDayById(@PathParam("id") long id) {
        ScheduledDayDTO freeDay = scheduleService.resetDay(id);
        return Response.ok().entity(freeDay).build();
    }
}
