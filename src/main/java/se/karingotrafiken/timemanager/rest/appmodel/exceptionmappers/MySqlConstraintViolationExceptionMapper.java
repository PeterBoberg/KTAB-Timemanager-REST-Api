package se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MySqlConstraintViolationExceptionMapper implements ExceptionMapper<MySQLIntegrityConstraintViolationException> {

    @Override
    public Response toResponse(MySQLIntegrityConstraintViolationException e) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ErrorMessageDTO.ErrorCode.DUPLICATE_ENTRY, e.getMessage());
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }
}
