package sat;

import java.util.HashSet;
import java.util.Set;

public class Formula implements Observer{
    private Set<Clause> clauses = new HashSet<>();

    @Override
    public void update(Observable o) {

    }
}
