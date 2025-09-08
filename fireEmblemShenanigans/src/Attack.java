import java.util.HashMap;

public class Attack {
    public static final HashMap<String, Integer> outcomeBurns = initializeOutcomeBurns();

    private final int hit, crit;
    private final boolean brave;

    public Attack(int hit, int crit, boolean brave) {
        this.hit = hit;
        this.crit = crit;
        this.brave = brave;
    }

    private static HashMap<String, Integer> initializeOutcomeBurns() {
        HashMap<String, Integer> ob = new HashMap<>();
        ob.put("m", 2);
        ob.put("h", 3);
        if (SeedFinder.isLethalityCheck()) {
            ob.put("c", 4);
        } else {
            ob.put("c", 3);
        }

        return ob;
    }

    public String getOutcome(int rnIndex, int[] rnArray) {
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

//    public String getOutcome(int rnIndex, int[] rnArray) {
//        int rn1 = rnArray[rnIndex];
//        int rn2 = rnArray[rnIndex + 1];
//        int avg = (rn1 + rn2) / 2;
//
//        if (avg >= hit) {
//            return "m";
//        }
//
//        if (rnArray[rnIndex + 2] < crit) {
//            return "c";
//        }
//
//        return "h";
//    }

    public String toString() {
        return hit + "h " + crit + "c";
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
