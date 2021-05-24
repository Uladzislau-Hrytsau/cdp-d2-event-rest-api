package cdp.d2.m2.event.dao;

import cdp.d2.m2.event.entity.Event;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for persistence of {@link Event}
 */
public interface EventRepository extends CrudRepository<Event, Long> {

    /**
     * Retrieves all events
     *
     * @return all founded events
     */
    List<Event> findAll();

    /**
     * Retrieves event by title
     *
     * @param title title of event
     * @return All events found by specified title
     */
    List<Event> findAllByTitle(String title);

}
