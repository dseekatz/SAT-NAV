package sat;

import java.util.HashSet;
import java.util.Set;

/**
 * A single propositional logic term, either a variable or its negation
 */
public class Term implements Observer, Observable{
    private Variable v;
    private boolean isNegated;
    private boolean isSatisfied = false;
    private Set<Observer> observers = new HashSet<>();


    public Term(Variable v, boolean isNegated) {
        this.v = v;
        this.isNegated = isNegated;
    }

    public Variable variable() {
        return v;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    @Override
    public void update(Observable o) {
        if (!(o instanceof Variable)) {
            // should not be registered to anything other than a variable
            o.removeObserver(this);
            return;
        }
        Variable v = ((Variable) o);
        if (v.isAssigned()) {
                isSatisfied = v.getAssignment() ^ isNegated; // bitwise xor
        } else {
                isSatisfied = false;
        }
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update(this));
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
}
