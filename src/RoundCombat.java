import java.util.Arrays;

public class RoundCombat {
    private int preburn;
    private Attack attack1, attack2;
    private boolean double1, double2;
    private String[] outcomes;

    public RoundCombat(int preburn, Attack attack1, boolean double1, String[] outcomes) {
        setParameters(preburn, attack1, double1, null, false, outcomes);
    }

    public RoundCombat(int preburn, Attack attack1, boolean double1,
                       Attack attack2, boolean double2, String[] outcomes) {
        setParameters(preburn, attack1, double1, attack2, double2, outcomes);
    }

    private void setParameters(int preburn, Attack attack1, boolean double1,
                               Attack attack2, boolean double2, String[] outcomes) {
        this.preburn = preburn;
        this.attack1 = attack1;
        this.double1 = double1;
        this.attack2 = attack2;
        this.double2 = double2;
        this.outcomes = outcomes;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        addLine(result, "---ROUND COMBAT---");
        addLine(result, "Preburn: " + preburn);
        addLine(result, "Attack 1: " + attack1);
        addLine(result, "Double 1: " + double1);

        if (hasSecondAttack()) {
            addLine(result, "Attack 2: " + attack2);
            addLine(result, "Double 2: " + double2);
        }

        addLine(result, "Outcomes: ");
        for (int i = 0; i < outcomes.length; i++) {
            result.append(outcomes[i]);
            if (i != outcomes.length - 1) {
                result.append(", ");
            }
        }

        addLine(result, "------------------");

        return result.toString();
    }

    private void addLine(StringBuilder stringBuilder, String s) {
        stringBuilder.append("\n").append(s);
    }

    public boolean hasSecondAttack() {
        return attack2 != null;
    }

    public boolean getDouble1() {
        return double1;
    }

    public boolean getDouble2() {
        return double2;
    }

    public Attack getAttack1() {
        return attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public int getPreburn() {
        return preburn;
    }

    public String[] getOutcomes() {
        return outcomes;
    }
}
