package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Termination {
    private final LocalDate terminationDateInclusive;
    private long numberOfOccurrences;

    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        this.terminationDateInclusive = terminationInclusive;
    }

    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
        this.terminationDateInclusive = start.plus(numberOfOccurrences - 1, frequency);
    }

    public LocalDate terminationDateInclusive() {
        return terminationDateInclusive;
    }

    public long numberOfOccurrences() {
        return numberOfOccurrences;
    }
}