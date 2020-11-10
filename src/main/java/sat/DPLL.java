package sat;

public class DPLL {
    Formula f;
    DecisionHeuristic dh;

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
        return false;
    }

    /**
     * returns true if a conflict is encountered, false otherwise
     * @return
     */
    private boolean propogate() {
        return false;
    }
}
