import java.util.HashMap;

public class Attack {
    public static final HashMap<Character, Integer> outcomeBurns = initializeOutcomeBurns();

    private final int hit, crit;
    private final boolean brave;

    public Attack(int hit, int crit, boolean brave) {
        this.hit = hit;
        this.crit = crit;
        this.brave = brave;
    }

    private static HashMap<Character, Integer> initializeOutcomeBurns() {
        HashMap<Character, Integer> ob = new HashMap<>();
        ob.put('m', 2);
        ob.put('h', 3);
        if (SeedFinder.isLethalityCheck()) {
            ob.put('c', 4);
        } else {
            ob.put('c', 3);
        }

        return ob;
    }

    private String getSingleAttackOutcome(int rnIndex, int[] rnArray) {
        int rn1 = rnArray[rnIndex];
        int rn2 = rnArray[rnIndex + 1];
        int avg = (rn1 + rn2) / 2;

        if (avg >= hit) {
            return "m";
        }

        if (rnArray[rnIndex + 2] < crit) {
            return "c";
        }

        return "h";
    }

    public String getOutcome(int rnIndex, int[] rnArray) {
        if (brave) {
            String firstAttackOutcome = getSingleAttackOutcome(rnIndex, rnArray);
            int secondAttackRnIndex = rnIndex + getOutcomeBurns(firstAttackOutcome);
            String secondAttackOutcome = getSingleAttackOutcome(secondAttackRnIndex, rnArray);
            return firstAttackOutcome + secondAttackOutcome;
        }

        return getSingleAttackOutcome(rnIndex, rnArray);
    }

    public static int getOutcomeBurns(String outcome) {
        int burns = 0;

        for (int i = 0; i < outcome.length(); i++) {
            burns += outcomeBurns.get(outcome.charAt(i));
        }

        return burns;
    }

    public String toString() {
        String result = hit + "h " + crit + "c";

        if (brave) {
            return result + ", Brave";
        }

        return result;
    }

    public int getCrit() {
        return crit;
    }

    public int getHit() {
        return hit;
    }

    public boolean isBrave() {
        return brave;
    }
}
