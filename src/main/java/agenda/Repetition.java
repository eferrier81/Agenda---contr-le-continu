package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Repetition {
    private final ChronoUnit myFrequency;
    private final Set<LocalDate> exceptions;
    private Termination termination;

    public Repetition(ChronoUnit myFrequency) {
        this.myFrequency = myFrequency;
        this.exceptions = new HashSet<>();
    }

    public ChronoUnit getFrequency() {
        return myFrequency;
    }

    public void addException(LocalDate date) {
        this.exceptions.add(date);
    }

    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    public Termination getTermination() {
        return termination;
    }
}
