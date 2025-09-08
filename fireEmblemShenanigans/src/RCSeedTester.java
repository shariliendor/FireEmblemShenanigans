import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RCSeedTester implements SeedTester {
    private RoundCombat[] combats;

    private int[] rnArray; // made a class variable for simpler, more readable code
    private int rnIndex; // same

    private boolean endEarly = false;

    // temp variables for optimization
    private int outcomeIndex; // keeps track of how far into outcome strings we are

    public RCSeedTester(RoundCombat[] combats) {
        this.combats = combats;
    }

    @Override
    public boolean testSeed(int index, int[] rns) {
        this.rnArray = rns;
        this.rnIndex = index;

        for (RoundCombat combat: combats) {
            if (!testCombat(combat)) {
                return false;
            }
        }

        return true;
    }

    private boolean testCombat(RoundCombat combat) {
        ArrayList<String> validOutcomes = new ArrayList<>(List.of(combat.getOutcomes()));
        //ArrayList<Character> outcomeSoFar = new ArrayList<>();
        outcomeIndex = 0;
        endEarly = false;

        rnIndex += combat.getPreburn();

        // debug
        System.out.println("burned " + combat.getPreburn());

        // check first attack
        if (attackFails(combat.getAttack1(), validOutcomes)) {
            return false;
        }

        if (endEarly) {
            System.out.println("Ending early");
            endEarly = false;
            return true;
        }

        // if second attack is not null, process that
        if (combat.hasSecondAttack()) {
            if (attackFails(combat.getAttack2(), validOutcomes)) {
                return false;
            }

            if (endEarly) {
                endEarly = false;
                return true;
            }
        }

        // if first attack doubled, process that
        if (combat.getDouble1()) {
            if (attackFails(combat.getAttack1(), validOutcomes)) {
                return false;
            }

            if (endEarly) {
                endEarly = false;
                return true;
            }
        }

        // if second attack doubled process that
        if (combat.getDouble2()) {
            // last attack, make or break
            // no need to check endEarly 'cause it's the last one anyway
            return !attackFails(combat.getAttack2(), validOutcomes);
        }

        return true;
    }

    private boolean attackFails(Attack attack, ArrayList<String> validOutcomes) {
        String outcome = attack.getOutcome(rnIndex, rnArray);
        updateValidOutcomes(validOutcomes, outcome);
        // modify for brave
        rnIndex += Attack.outcomeBurns.get(outcome);
        outcomeIndex += outcome.length();

        // debug
        System.out.println();
        System.out.println(attack);

        System.out.println("Outcome: " + outcome);
        System.out.print("RNs: ");

        // modify for brave
        for (int i = rnIndex - Attack.outcomeBurns.get(outcome); i < rnIndex; i++) {
            System.out.print(rnArray[i] + " ");
        }
        System.out.println();

        if (validOutcomes.isEmpty()) {
            System.out.println("Failed");
        }


        return validOutcomes.isEmpty();
    }

    private void updateValidOutcomes(ArrayList<String> validOutcomes, String outcome) {
        for (int i = validOutcomes.size() - 1; i >= 0; i--) {
            // add extra function to check equality with x's
            System.out.println("Outcome: " + outcome + ", check: " + validOutcomes.get(i).substring(outcomeIndex, outcomeIndex + outcome.length()));
            if (!(validOutcomes.get(i).substring(outcomeIndex, outcomeIndex + outcome.length()).equals(outcome))) {
                System.out.print("removing " + validOutcomes.get(i));
                validOutcomes.remove(i);
            } else if (validOutcomes.get(i).length() == outcomeIndex + outcome.length()) {
                System.out.println("Ending early on " + validOutcomes.get(i));
                endEarly = true;
                return;
            }
        }
    }
}
