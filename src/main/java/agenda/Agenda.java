package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private final List<Event> events;

    public Agenda() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

        public List<Event> eventsInDay(LocalDate day) {
        List<Event> eventsInDay = new ArrayList<>();
        for (Event event : events) {
            if (event.isInDay(day)) {
                eventsInDay.add(event);
            }
        }
        return eventsInDay;
    }
}
