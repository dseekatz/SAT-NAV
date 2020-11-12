package sat;

public interface DecisionHeuristic {

    Variable getAndAssignNextVar(Formula f);

}
