package sat;

import java.util.HashSet;
import java.util.Set;

/**
 * A single propositional logic variable
 */
public class Variable implements Observable {

    private boolean isAssigned = false;
    private Boolean assignment;
    private Set<Observer> observers = new HashSet<>();

    public boolean getAssignment() {
        return assignment;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssignment(boolean assignment) {
        this.assignment = assignment;
        isAssigned = true;
        notifyObservers();
    }

    public void unassign() {
        assignment = null;
        isAssigned = false;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update(this));
    }
}
