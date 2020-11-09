package sat;

import java.util.Set;

/**
 * A CNF clause
 */
public class Clause implements Observer {
    private Set<Term> terms;
    private State state = State.UNRESOLVED;
    private boolean isSatisfied = false;

    protected enum State{
        SATISFIED,
        CONFLICTING,
        UNIT,
        UNRESOLVED
    }

    public Clause(Set<Term> terms) {
        this.terms = terms;
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
            this.isSatisfied = true;
        }
    }


}
