package sat;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.Objects;

public class ImplicationGraph {
    private MutableValueGraph<TermAtLevel, Clause> backingGraph =
            ValueGraphBuilder
            .directed()
            .allowsSelfLoops(false)
            .build();
    private TermAtLevel lastAssignment;

    private void setLastAssignment(Term t, int level) {
        lastAssignment = new TermAtLevel(t, level);
    }

    public boolean hasConflict() {
        return false;
    }

    public void addImpliedAssignment(Term impliedTerm, Clause implyingClause, int level) {
        backingGraph.putEdgeValue(
                lastAssignment,
                new TermAtLevel(impliedTerm, level),
                implyingClause
        );
        setLastAssignment(impliedTerm, level);
    }

    public void addDecisionAssignment(Term decision, int level) {
        setLastAssignment(decision, level);
    }


    protected static class TermAtLevel {
        private Term term;
        private int level;

        protected TermAtLevel(Term t, int level) {
            this.term = t;
            this.level = level;
        }

        protected Term term() {
            return term;
        }

        protected int level() {
            return level;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TermAtLevel that = (TermAtLevel) o;
            return level == that.level &&
                    term.equals(that.term);
        }

        @Override
        public int hashCode() {
            return Objects.hash(term, level);
        }
    }
}
