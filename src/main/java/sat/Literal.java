package sat;

/**
 * A single propositional logic literal, of form:
 * x, or
 * ~x
 * for some x in V, the set of variables.
 */
public class Literal {
    private Variable v;
    private boolean isNegated;


    public Literal(Variable v, boolean isNegated) {
        this.v = v;
        this.isNegated = isNegated;
    }

    public Variable variable() {
        return v;
    }

    public boolean isNegated() {
        return isNegated;
    }
}
