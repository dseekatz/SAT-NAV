package sat;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class ImplicationGraph {
    private MutableValueGraph<TermAtLevel, Clause> backingGraph =
            ValueGraphBuilder
            .directed()
            .allowsSelfLoops(false)
            .build();

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
    }
}
