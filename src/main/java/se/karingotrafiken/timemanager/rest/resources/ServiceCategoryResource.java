package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.ServiceCategoryDTO;
import se.karingotrafiken.timemanager.rest.service.enums.ServiceCategoryService;
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
@Path("/enums/servicecategories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceCategoryResource {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @POST
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response createServiceCategory(@Valid ServiceCategoryDTO serviceCategory, @Context UriInfo info) {
        ServiceCategoryDTO newServiceCategory = serviceCategoryService.create(serviceCategory);
        URI atLocation = UriUtils.toUri(info, serviceCategory.getId());
        return Response.created(atLocation).entity(newServiceCategory).build();
    }

    @GET
    public Response getAllServiceCategories() {
        List<ServiceCategoryDTO> serviceCategoryDTOS = serviceCategoryService.getAll();
        return Response.ok().entity(serviceCategoryDTOS).build();
    }

    @GET
    @Path("/{id}")
    public Response getServiceCategoryWithId(@PathParam("id") int id) {
        ServiceCategoryDTO serviceCategory = serviceCategoryService.getById(id);
        return Response.ok(serviceCategory).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateServiceCategory(@PathParam("id") long id,  @Valid ServiceCategoryDTO serviceCategory) {
        serviceCategory.setId(id);
        ServiceCategoryDTO updatedServiceCategory = serviceCategoryService.update(serviceCategory);
        return Response.ok().entity(updatedServiceCategory).build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteServiceCategoryWithId(@PathParam("id") int id) {
        serviceCategoryService.deleteById(id);
        return Response.ok().build();
    }
}
