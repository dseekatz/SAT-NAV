package sat;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JeroslowWang implements DecisionHeuristic {

    private Map<Variable, Double> jValues = new HashMap<>();
    private Formula f;
    private Random r = new Random();

    public JeroslowWang(Formula f) {
        this.f = f;
    }

    @Override
    public Variable getAndAssignNextVar() {
        f.getLiterals().forEach(this::computeJValue);
        Variable bestChoiceVar = jValues.entrySet().stream()
                .max(new MaxFinder())
                .orElseThrow(RuntimeException::new)
                .getKey();
        // Randomly assign either true or false to the var with highest jValue?
        bestChoiceVar.setAssignment(r.nextBoolean());
        return bestChoiceVar;
    }

    private void computeJValue(Variable v) {
        double jValue = f.getClauses().stream()
                .filter(c -> c.containsLiteral(v))
                .mapToDouble(c -> Math.pow(2, c.length()))
                .sum();
        jValues.put(v, jValue);
    }

    private static class MaxFinder implements Comparator<Map.Entry<Variable, Double>> {

        @Override
        public int compare(Map.Entry<Variable, Double> o1, Map.Entry<Variable, Double> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }
}
