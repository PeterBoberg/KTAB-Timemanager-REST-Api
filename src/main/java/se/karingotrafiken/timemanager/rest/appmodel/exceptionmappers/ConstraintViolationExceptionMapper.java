package se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers;

import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ErrorMessageDTO.ErrorCode.INVALID_INPUT, prepareMessage(e));
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            msg.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }
        return msg.toString();
    }
}