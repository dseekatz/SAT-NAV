package sat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Formula implements Observer {
    private Map<Clause, Boolean> clausesSatisfied = new HashMap<>();
    boolean isSatisfied;

    public void addClause(Clause c) {
        clausesSatisfied.put(c, false);
    }

    @Override
    public void update(Observable o) {
        if (!(o instanceof Clause)) {
            o.removeObserver(this);
            return;
        }
        Clause c = ((Clause) o);
        if (c.isSatisfied()) {
            clausesSatisfied.replace(c, true);
            if (allSatisfied()) {
                isSatisfied = true;
                return;
            }
        }
    }

    private boolean allSatisfied() {
        return clausesSatisfied.values().stream().allMatch(b -> b);
    }

    public boolean hasImplications() {
        return clausesSatisfied.keySet().stream().anyMatch(Clause::isUnitClause);
    }

    public Clause getNextImplication() {
        return clausesSatisfied.keySet().stream()
                .filter(Clause::isUnitClause)
                .findAny()
                .orElseThrow(ImplicationNotPresentException::new);
    }

    protected Set<Clause> getClauses() {
        return clausesSatisfied.keySet();
    }
}
