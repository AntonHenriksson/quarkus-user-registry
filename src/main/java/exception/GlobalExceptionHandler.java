package exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;


public class GlobalExceptionHandler {
    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @ServerExceptionMapper
    public Response mapNotFoundException(NotFoundException missing) {
        LOG.error(missing.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(missing.getMessage(), 404, LocalDateTime.now().toString()))
                .build();
    }

    @ServerExceptionMapper
    public Response mapGeneralException(Exception exc) {
        LOG.error(exc.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Server error", 500, LocalDateTime.now().toString()))
                .build();
    }

    @ServerExceptionMapper
    public Response mapConstraintViolationException(ConstraintViolationException constraint) {
        LOG.error(constraint.getMessage());
        List<ValidationError> errors = constraint.getConstraintViolations().stream()
                .map(violation -> new ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()

                ))
                .toList();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ValidationResponse("Validation failed", 400, errors))
                .build();
    }


    public record ErrorResponse(String message, int status, String timeStamp) {
    }

    public record ValidationError(String field, String message) {
    }

    public record ValidationResponse(String message, int status, List<ValidationError> errors) {
    }

}

