package cdp.d2.m2.event.rest;

import cdp.d2.m2.event.dto.EventDto;
import cdp.d2.m2.event.entity.Event;
import cdp.d2.m2.event.rest.exception.NoContentFoundException;
import cdp.d2.m2.event.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * 10. Document methods in EventServiceController using Swagger 2 annotations.
 */
@RestController
@RequestMapping(value = "events", produces = {"application/json", "application/xml", "application/hal+json"})
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Api(value = "event-rest-api", description = "Simple event rest api for working with events")
public class EventServiceRestController {

    private final EventService eventService;

    @Autowired
    public EventServiceRestController(final EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View a list of available events", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved events"),
            @ApiResponse(code = 204, message = "No events were found"),
            @ApiResponse(code = 404, message = "Resource not Found"),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> getAllEvents() {

        List<EntityModel<EventDto>> entityModels = eventService.getAllEvents()
                .stream()
                .map(this::convert)
                .map(e -> EntityModel.<EventDto>of(e, getEventByIdLink(e), getAllEventsByTitle(e), getUpdateLink(), getDeleteLink(e)))
                .collect(Collectors.toList());

        if (entityModels.isEmpty()) {
            throw new NoContentFoundException();
        }
        return CollectionModel.of(entityModels, getAllEventsLink());
    }

    @GetMapping("title")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View a list of available events by specified event title", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved events"),
            @ApiResponse(code = 204, message = "No events were found by specified title"),
            @ApiResponse(code = 400, message = "Specified event title is missing"),
            @ApiResponse(code = 404, message = "Resource not Found"),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> getAllEventsByTitle(@RequestParam(name = "title") String title) {
        List<EntityModel<EventDto>> entityModels = eventService.getAllEventsByTitle(title)
                .stream()
                .map(this::convert)
                .map(e -> EntityModel.of(e, getEventByIdLink(e), getAllEventsByTitle(e), getUpdateLink(), getDeleteLink(e)))
                .collect(Collectors.toList());

        if (entityModels.isEmpty()) {
            throw new NoContentFoundException();
        }

        return CollectionModel.of(entityModels, getAllEventsLink());
    }

    @GetMapping("id")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View available event by specified event identifier", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved events"),
            @ApiResponse(code = 204, message = "No event was found by specified event identifier"),
            @ApiResponse(code = 400, message = "Specified event identifier is missing"),
            @ApiResponse(code = 404, message = "Resource not Found"),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> getEventById(@RequestParam(value = "id") Long id) {
        return eventService.getEventById(id)
                .map(this::convert)
                .map(e -> EntityModel.of(e, getEventByIdLink(e), getAllEventsByTitle(e), getUpdateLink(), getDeleteLink(e)))
                .map(model -> CollectionModel.of(List.of(model), List.of(getAllEventsLink())))
                .orElseThrow(NoContentFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create event by specified request body", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event successfully created"),
            @ApiResponse(code = 204, message = "No event was created"),
            @ApiResponse(code = 400, message = "Specified event is invalid"),
            @ApiResponse(code = 404, message = "Resource not Found"),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> createEvent(@RequestBody @Validated EventDto event) {
        return Optional.of(event)
                .map(this::convert)
                .map(eventService::createEvent)
                .map(this::convert)
                .map(e -> EntityModel.of(e, getEventByIdLink(e), getAllEventsByTitle(e), getUpdateLink(), getDeleteLink(e)))
                .map(model -> CollectionModel.of(List.of(model), List.of(getAllEventsLink())))
                .orElseThrow(NoContentFoundException::new);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update event by specified request body", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event successfully updated"),
            @ApiResponse(code = 204, message = "No event was updated"),
            @ApiResponse(code = 400, message = "Specified event is invalid"),
            @ApiResponse(code = 404, message = "Resource not Found"),
            @ApiResponse(code = 415, message = "The content type is unsupported"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> updateEvent(@RequestBody @Validated EventDto event) {
        return createEvent(event);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove event by specified event identifier", response = CollectionModel.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Event successfully removed"),
            @ApiResponse(code = 400, message = "Specified event identifier is missing"),
            @ApiResponse(code = 404, message = "Resource not Found"),@ApiResponse(code = 415, message = "The content type is unsupported"),

            @ApiResponse(code = 500, message = "Server error")
    })
    public CollectionModel<EntityModel<EventDto>> deleteEvent(@PathVariable(value = "id") Long id) {
        eventService.deleteEvent(id);
        return CollectionModel.empty(getAllEventsLink());
    }

    public Link getAllEventsLink() {
        return linkTo(methodOn(EventServiceRestController.class).getAllEvents())
                .withRel("get all events, HTTP GET");
    }

    private Link getEventByIdLink(EventDto event) {
        return linkTo(methodOn(EventServiceRestController.class).getEventById(event.getId()))
                .withRel("get event by id, HTTP GET");
    }

    private Link getAllEventsByTitle(EventDto event) {
        return linkTo(methodOn(EventServiceRestController.class).getAllEventsByTitle(event.getTitle()))
                .withRel("get all events by title, HTTP GET");
    }

    private Link getUpdateLink() {
        return linkTo(methodOn(EventServiceRestController.class).updateEvent(null))
                .withRel("update event, HTTP PUT");
    }

    private Link getDeleteLink(EventDto event) {
        return linkTo(methodOn(EventServiceRestController.class).deleteEvent(event.getId()))
                .withRel("remove event by id, HTTP DELETE");
    }

    private EventDto convert(Event event) {
        return EventDto
                .builder()
                .id(event.getId())
                .title(event.getTitle())
                .place(event.getPlace())
                .speaker(event.getSpeaker())
                .eventType(event.getEventType())
                .dateTime(event.getDateTime())
                .build();
    }

    private Event convert(EventDto event) {
        return Event
                .builder()
                .id(event.getId())
                .title(event.getTitle())
                .place(event.getPlace())
                .speaker(event.getSpeaker())
                .eventType(event.getEventType())
                .dateTime(event.getDateTime())
                .build();
    }

}
