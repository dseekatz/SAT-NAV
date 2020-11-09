package sat;

public interface DecisionHeuristic {
    Variable getNextAssignment(Formula f);
}
