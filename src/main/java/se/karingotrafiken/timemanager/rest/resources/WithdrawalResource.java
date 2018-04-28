package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;
import se.karingotrafiken.timemanager.rest.service.withdrawal.WithdrawalService;
import se.karingotrafiken.timemanager.rest.utils.UriUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WithdrawalResource {

    @Autowired
    private WithdrawalService withdrawalService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response withDraw(@PathParam("employeeId") long employeeId, @Valid WithdrawalDTO withdrawalDTO, @Context UriInfo info) {
        withdrawalDTO.setEmployeeId(employeeId);
        WithdrawalDTO saved = withdrawalService.create(withdrawalDTO);
        URI atLocation = UriUtils.toUri(info, saved.getId());
        return Response.created(atLocation).entity(saved).build();
    }

    @GET
    @Path("/{id}")
    public Response getWithdrawalById(@PathParam("employeeId") long employeeId, @PathParam("id") long id) {
        WithdrawalDTO withdrawalDTO = withdrawalService.getById(id);
        return Response.ok().entity(withdrawalDTO).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateWithdrawal(@PathParam("employeeId") long employeeId, @Valid WithdrawalDTO withdrawalDTO) {
        withdrawalDTO.setEmployeeId(employeeId);
        WithdrawalDTO updated = withdrawalService.update(withdrawalDTO);
        return Response.ok().entity(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteWithdrawalById(@PathParam("employeeId") long employeeId, @PathParam("id") long id) {
        withdrawalService.deleteById(id);
        return Response.ok().build();
    }

    @GET
    public Response getAllWithdrawalsForEmployee(@PathParam("employeeId") long employeeId) {
        List<WithdrawalDTO> withdrawalDTOS = withdrawalService.getAllWithdrawalsForEmployee(employeeId);
        GenericEntity<List<WithdrawalDTO>> genericEntity = new GenericEntity<List<WithdrawalDTO>>(withdrawalDTOS) {
        };
        return Response.ok().entity(genericEntity).build();
    }
}
