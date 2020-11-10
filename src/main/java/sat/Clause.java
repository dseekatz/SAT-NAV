package sat;

import java.util.HashSet;
import java.util.Set;

/**
 * A CNF clause
 */
public abstract class Clause implements Observer, Observable {
    private Set<Term> terms = new HashSet<>();
    private State state = State.UNRESOLVED;
    private Formula formula;

    public Clause(Formula f) {
        setFormula(f);
    }

    public abstract void setFormula(Formula f);

    public boolean isSatisfied() {
        return state == State.SATISFIED;
    }

    @Override
    public void notifyObservers() {
        formula.update(this);
    }

    @Override
    public void addObserver(Observer o) {
        if (o instanceof Formula) {
            setFormula(((Formula) o));
        }
    }

    @Override
    public void removeObserver(Observer o) {
        // not applicable
    }

    protected enum State{
        SATISFIED,
        CONFLICTING,
        UNIT,
        UNRESOLVED
    }

    public void addTerm(Term t) {
        terms.add(t);
    }

    public void removeTerm(Term t) {
        terms.remove(t);
    }

    public State getState() {
        return state;
    }

    @Override
    public void update(Observable o) {
        if (!(o instanceof Term)) {
            o.removeObserver(this);
            return;
        }
        Term t = ((Term) o);
        if (t.isSatisfied()) {
            state = State.SATISFIED;
        } else if (!isSatisfied() && allAssigned()) {
            state = State.CONFLICTING;
        } else if (!isSatisfied() && isUnitClause()) {
            state = State.UNIT;
        } else {
            state = State.UNRESOLVED;
        }
        notifyObservers();
    }

    protected boolean isUnitClause() {
        return terms.stream()
                .filter(t -> !t.variable().isAssigned())
                .count() == 1;
    }

    protected boolean allAssigned() {
        return terms.stream()
                .allMatch(t -> t.variable().isAssigned());
    }


}
