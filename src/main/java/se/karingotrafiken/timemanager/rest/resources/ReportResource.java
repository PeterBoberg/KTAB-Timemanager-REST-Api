package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.MonthlyReportResponse;
import se.karingotrafiken.timemanager.rest.service.reports.ReportService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @GET
    @Path("/monthly")
    public Response getMonthlyReport(@PathParam("employeeId") long employeeId, @QueryParam("year") int year, @QueryParam("month") int month) {
        MonthlyReportResponse monthlyReport = reportService.getMonthlyReport(employeeId, year, month);
        return Response.ok().entity(monthlyReport).build();
    }
}
