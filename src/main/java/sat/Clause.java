package sat;

import com.google.errorprone.annotations.Var;

import java.util.HashSet;
import java.util.Set;

/**
 * A CNF clause
 */
public abstract class Clause implements Observer, Observable {
    private Set<Term> terms = new HashSet<>();
    private State state = State.UNRESOLVED;
    private Formula formula;

    protected enum State{
        SATISFIED,
        CONFLICTING,
        UNIT,
        UNRESOLVED
    }

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

    protected Term solveUnit() {
        Term unitTerm = terms.stream()
                .filter(t -> !t.isSatisfied())
                .findAny()
                .orElseThrow(ImplicationNotPresentException::new);
        if (unitTerm.isNegated()) {
            unitTerm.variable().setAssignment(false);
        } else {
            unitTerm.variable().setAssignment(true);
        }
        state = State.SATISFIED;
        notifyObservers();
        return unitTerm;
    }

    protected boolean allAssigned() {
        return terms.stream()
                .allMatch(t -> t.variable().isAssigned());
    }

    protected Set<Term> getTerms() {
        return terms;
    }

    public boolean containsLiteral(Variable v) {
        return terms.stream()
                .map(Term::variable)
                .anyMatch(var -> var.equals(v));
    }

    public int length() {
        return terms.size();
    }


}
