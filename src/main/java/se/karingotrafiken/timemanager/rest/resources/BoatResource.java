package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.BoatDTO;
import se.karingotrafiken.timemanager.rest.service.boat.BoatService;
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
@Path("/boats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoatResource {

    @Autowired
    private BoatService boatService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addBoat(@Valid BoatDTO boatDTO, @Context UriInfo info) {
        BoatDTO newBoat = boatService.create(boatDTO);
        URI atLocation = UriUtils.toUri(info, newBoat.getId());
        return Response.created(atLocation).entity(newBoat).build();
    }

    @GET
    @Path("/{id}")
    public Response getBoatById(@PathParam("id") long id) {
        BoatDTO boat = boatService.getById(id);
        return Response.ok().entity(boat).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateBoat(@PathParam("id") long id, @Valid BoatDTO boat) {
        boat.setId(id);
        BoatDTO updated = boatService.update(boat);
        return Response.ok().entity(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteBoat(@PathParam("id") long id) {
        boatService.deleteById(id);
        return  Response.ok().build();
    }

    @GET
    public Response getAllBoats() {
        List<BoatDTO> boats = boatService.getAll();
        return Response.ok().entity(boats).build();
    }

}
