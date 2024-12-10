package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Event {
    private final String myTitle;
    private final LocalDateTime myStart;
    private final Duration myDuration;
    private Repetition repetition;
    private final Set<LocalDate> exceptions;

    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
        this.exceptions = new HashSet<>();
    }

    public void setRepetition(ChronoUnit frequency) {
        this.repetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        if (this.repetition != null) {
            this.repetition.addException(date);
        }
        this.exceptions.add(date);
    }

    public void setTermination(LocalDate terminationInclusive) {
        if (this.repetition != null) {
            this.repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), terminationInclusive));
        }
    }

    public void setTermination(long numberOfOccurrences) {
        if (this.repetition != null) {
            this.repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), numberOfOccurrences));
        }
    }

    public int getNumberOfOccurrences() {
        if (this.repetition != null && this.repetition.getTermination() != null) {
            LocalDate startDate = myStart.toLocalDate();
            Termination termination = this.repetition.getTermination();

            if (termination.terminationDateInclusive() != null) {
                long daysBetween = ChronoUnit.DAYS.between(startDate, termination.terminationDateInclusive());
                long interval = repetition.getFrequency().getDuration().toDays();
                return (int) (daysBetween / interval) + 1; // +1 pour inclure le jour de début
            } else if (termination.numberOfOccurrences() > 0) {
                return (int) termination.numberOfOccurrences();
            }
        }
        return 1;
    }

    public LocalDate getTerminationDate() {
        if (this.repetition != null && this.repetition.getTermination() != null) {
            return this.repetition.getTermination().terminationDateInclusive();
        }
        return null;
    }

    public boolean isInDay(LocalDate aDay) {
        if (exceptions.contains(aDay)) {
            return false;
        }
        if (aDay.isBefore(myStart.toLocalDate())) {
            return false;
        }
        if (repetition != null) {
            LocalDate startDate = myStart.toLocalDate();
            Termination termination = repetition.getTermination();
            if (termination != null) {
                LocalDate terminationDate = termination.terminationDateInclusive();
                if (aDay.isAfter(terminationDate)) {
                    return true;
                }
                // Ici, on vérifie si la date est exactement la date de terminaison
                if (aDay.equals(terminationDate)) {
                    return true;
                }
            }
            // Calculer si la date correspond à une occurrence valide
            long daysBetween = ChronoUnit.DAYS.between(startDate, aDay);
            return daysBetween % repetition.getFrequency().getDuration().toDays() == 0;
        } else {
            LocalDateTime endDateTime = myStart.plus(myDuration);
            return !aDay.isBefore(myStart.toLocalDate()) && !aDay.isAfter(endDateTime.toLocalDate());
        }
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
