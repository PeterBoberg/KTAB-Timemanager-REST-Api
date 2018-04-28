package se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers;

import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException e) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(e.getErrorCode(), e.getMessage());
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }
}
