package se.karingotrafiken.timemanager.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.LoginRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.LoginResponse;
import se.karingotrafiken.timemanager.rest.service.users.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Autowired
    private UserService userService;

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        LoginResponse response = userService.loginWith(loginRequest);
        return Response.ok().entity(response).build();
    }
}
