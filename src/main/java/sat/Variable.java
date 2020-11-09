package sat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A single propositional logic variable
 */
public class Variable {

    private boolean isAssigned = false;
    private Boolean assignment;
    private Set<Clause> clauses = new HashSet<>();

    public Optional<Boolean> getAssignment() {
        return assignment == null ? Optional.empty() : Optional.of(assignment);
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssignment(boolean assignment) {
        this.assignment = assignment;
        isAssigned = true;
        notifyClauses();
    }

    public void unassign() {
        assignment = null;
        isAssigned = false;
        notifyClauses();
    }

    public void addListener(Clause clause) {
        clauses.add(clause);
    }

    public void removeListener(Clause clause) {
        clauses.remove(clause);
    }

    public void notifyClauses() {
        clauses.forEach(c -> c.update(this));
    }
}
