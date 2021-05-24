package cdp.d2.m2.event.rest.handler;

import cdp.d2.m2.event.dto.ErrorResponse;
import cdp.d2.m2.event.rest.EventServiceRestController;
import cdp.d2.m2.event.rest.exception.NoContentFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class EventServiceControllerAdvice extends ResponseEntityExceptionHandler {

    private final EventServiceRestController eventServiceRestController;

    @Autowired
    public EventServiceControllerAdvice(EventServiceRestController eventServiceRestController) {
        this.eventServiceRestController = eventServiceRestController;
    }

    @ExceptionHandler(NoContentFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CollectionModel<EntityModel<ErrorResponse>> handleNoContentFoundException(NoContentFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();
        return new <EntityModel<ErrorResponse>>CollectionModel(List.of(new <ErrorResponse>EntityModel(errorResponse)), eventServiceRestController
                .getAllEventsLink());
    }

}
