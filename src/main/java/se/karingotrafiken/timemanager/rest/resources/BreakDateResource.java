package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.BreakDateDTO;
import se.karingotrafiken.timemanager.rest.service.breakdate.BreakDateService;
import se.karingotrafiken.timemanager.rest.utils.UriUtils;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/breakdates")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BreakDateResource {

    @Autowired
    private BreakDateService breakDateService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addBreakDate(@Valid BreakDateDTO breakDate, @Context UriInfo info) {
        BreakDateDTO newBreakDate = breakDateService.create(breakDate);
        URI atLocation = UriUtils.toUri(info, newBreakDate.getId());
        return Response.created(atLocation).entity(newBreakDate).build();
    }
}
