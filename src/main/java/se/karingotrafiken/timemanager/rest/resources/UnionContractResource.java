package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.UnionContractDTO;
import se.karingotrafiken.timemanager.rest.service.unoincontracts.UnionContractService;
import se.karingotrafiken.timemanager.rest.utils.UriUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Component
@Path("/unioncontracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UnionContractResource {

    @Autowired
    private UnionContractService unionContractService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response createUnionContract(@Valid UnionContractDTO unionContract, @Context UriInfo info) {
        UnionContractDTO newUnionContract = unionContractService.create(unionContract);
        URI atLocation = UriUtils.toUri(info, newUnionContract.getId());
        return Response.created(atLocation).entity(newUnionContract).build();
    }

    @GET
    @Path("/{id}")
    public Response getUnionContractByInd(@PathParam("id") long id) {
        UnionContractDTO contractDTO = unionContractService.getById(id);
        return Response.ok().entity(contractDTO).build();
    }

    @GET
    public Response getAllUnionContracts() {
        List<UnionContractDTO> unionContractDTOS = unionContractService.getAll();
        GenericEntity<List<UnionContractDTO>> entityList = new GenericEntity<List<UnionContractDTO>>(unionContractDTOS) {};
        return Response.ok().entity(entityList).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateUnionContractById(@PathParam("id") long id, @Valid UnionContractDTO unionContractDTO) {
        unionContractDTO.setId(id);
        UnionContractDTO updated = unionContractService.update(unionContractDTO);
        return Response.ok().entity(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteUnionContractById(@PathParam("id") long id) {
        unionContractService.deleteById(id);
        return Response.ok().build();
    }
}
