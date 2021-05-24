package cdp.d2.m2.event.service;

import cdp.d2.m2.event.entity.Event;
import java.util.List;
import java.util.Optional;

/**
 * Service, that provides base operation for events
 */
public interface EventService {

    /**
     * Retrieves event by id
     *
     * @param id identifier of event
     * @return founded event
     */
    Optional<Event> getEventById(Long id);

    /**
     * Retrieves all events
     *
     * @return all founded events
     */
    List<Event> getAllEvents();

    /**
     * Retrieves event by title
     *
     * @param title title of event
     * @return All events found by specified title
     */
    List<Event> getAllEventsByTitle(String title);

    /**
     * Creates event
     *
     * @param event event for creating
     * @return created event
     */
    Event createEvent(Event event);

    /**
     * Updates event
     *
     * @param event event for updating
     * @return updated event
     */
    Event updateEvent(Event event);

    /**
     * Removes event by specified id
     *
     * @param id identifier of event
     */
    void deleteEvent(Long id);

}
