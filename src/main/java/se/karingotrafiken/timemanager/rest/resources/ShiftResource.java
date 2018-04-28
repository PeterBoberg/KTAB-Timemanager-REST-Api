package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.ShiftDTO;
import se.karingotrafiken.timemanager.rest.service.shifts.ShiftService;
import se.karingotrafiken.timemanager.rest.utils.UriUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Component
@Path("/shifts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShiftResource {

    @Autowired
    private ShiftService shiftService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response createShift(@Valid ShiftDTO shift, @Context UriInfo info) {
        ShiftDTO newShift = shiftService.create(shift);
        URI atLocation = UriUtils.toUri(info, newShift.getId());
        return Response.created(atLocation).entity(newShift).build();
    }

    @GET
    @Path("/{id}")
    public Response getShiftById(@PathParam("id") long id) {
        ShiftDTO shift = shiftService.getById(id);
        return Response.ok().entity(shift).build();
    }

    @PUT
    @Path("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateShift(@PathParam("id") long id, @Valid ShiftDTO shift) {
        shift.setId(id);
        ShiftDTO updated = shiftService.update(shift);
        return Response.ok().entity(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteShift(@PathParam("id") long id) {
        shiftService.deleteById(id);
        return Response.ok().build();
    }

    @GET
    public Response getAllShifts() {
        List<ShiftDTO> shifts = shiftService.getAll();
        GenericEntity<List<ShiftDTO>> entityList = new GenericEntity<List<ShiftDTO>>(shifts) {
        };
        return Response.ok().entity(entityList).build();
    }
}
