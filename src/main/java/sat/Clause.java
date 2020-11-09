package sat;

import java.util.HashSet;
import java.util.Set;

/**
 * A CNF clause
 */
public class Clause {
    private Set<Literal> literals = new HashSet<>();
    private State state = State.UNRESOLVED;

    protected enum State{
        SATISFIED,
        CONFLICTING,
        UNIT,
        UNRESOLVED
    }

    public State getState() {
        return state;
    }

    public void update(Variable v) {

    }


}
