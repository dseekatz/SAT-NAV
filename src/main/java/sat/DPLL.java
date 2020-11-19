package sat;

import java.util.Collection;

/**
 * DPLL implementation based on Chapter 2 of Kroenig and Strichman. Decision Procedures: An Algorithmic Point of View.
 *
 * @author David Seekatz
 */
public class DPLL {
    Formula f;
    DecisionHeuristic dh;
    ImplicationGraph impGraph = new ImplicationGraph();
    int currentLevel = 1;

    public DPLL(Formula f, DecisionHeuristic dh) {
        this.f = f;
        this.dh = dh;
    }

    /**
     * determine if the provided formula is satisfiable
     * @return
     */
    public boolean isSatisfiable() {
        boolean hasInitialBCPConflict = propogate();
        if (hasInitialBCPConflict) {
            return false;
        }
        while (decide()) {
            while (propogate()) {
                int backtrackLevel = analyzeConflict();
                if (backtrackLevel < 0) {
                    return false;
                }
                backtrack(backtrackLevel);
            }
        }
        return true;
    }

    /**
     * Reverts to the specified decision level, along the way undoing all assignments at level l > backtrackLevel
     * @param backtrackLevel
     */
    private void backtrack(int backtrackLevel) {

    }

    /**
     * Returns the decision level to which the solver should backtrack, or -1 if the formula is unsatisfiable
     * @return
     */
    private int analyzeConflict() {
        return 0;
    }

    /**
     * returns false when all variables are assigned, true otherwise
     * @return
     */
    private boolean decide() {
        if (allAssigned()) {
            return false;
        }
        Variable assigned = dh.getAndAssignNextVar();
        // Add newly assigned terms to the decision graph
        f.getClauses().stream()
                .map(Clause::getTerms)
                .flatMap(Collection::stream)
                .filter(t -> t.variable().equals(assigned))
                .forEach(t -> impGraph.addDecisionAssignment(t, currentLevel));
        currentLevel++;
        return true;
    }

    /**
     * returns true if all variables in the formula have been assigned, false otherwise
     * @return
     */
    private boolean allAssigned() {
        return f.getClauses().stream()
                .noneMatch(c -> c.getState() == Clause.State.UNIT || c.getState() == Clause.State.UNRESOLVED);
    }

    /**
     * returns true if a conflict is encountered, false otherwise
     * @return
     */
    private boolean propogate() {
        while (!impGraph.hasConflict() && f.hasImplications()) {
            Clause unit = f.getNextImplication();
            Term unitTerm = unit.solveUnit();
            impGraph.addImpliedAssignment(unitTerm, unit, currentLevel);
        }
        return impGraph.hasConflict();
    }
}
