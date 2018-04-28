package se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers;

import org.springframework.security.access.AccessDeniedException;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException e) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ErrorMessageDTO.ErrorCode.AUTHORIZATION_FAILURE, "You don´t have permission to access this resource");
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }
}