package sat;

import java.util.HashMap;
import java.util.Map;

public class Formula implements Observer {
    private Map<Clause, Boolean> clauses = new HashMap<>();
    boolean isSatisfied;

    public void addClause(Clause c) {
        clauses.put(c, false);
    }

    @Override
    public void update(Observable o) {
        if (!(o instanceof Clause)) {
            o.removeObserver(this);
            return;
        }
        Clause c = ((Clause) o);
        if (c.isSatisfied()) {
            clauses.replace(c, true);
            if (allSatisfied()) {
                isSatisfied = true;
                return;
            }
        }
    }

    private boolean allSatisfied() {
        return clauses.values().stream().allMatch(b -> b);
    }
}
