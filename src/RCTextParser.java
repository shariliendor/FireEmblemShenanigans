import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RCTextParser {
    Scanner sc;
    public RCTextParser() {}

    public RoundCombat[] parseCombats(String fileName) {
        File file = new File(fileName);

        try {
            sc = new Scanner(file);
        } catch (Exception e) {
            System.out.println("file error");
            e.printStackTrace();
            return null;
        }

        ArrayList<RoundCombat> combats = new ArrayList<>();

        // loop through every line of the file,
        // calling parseCombat on each line
        // and adding the result to the ArrayList
        // blank line between combats
        while (sc.hasNextLine()) {
            RoundCombat newCombat = parseCombat();
            combats.add(newCombat);

            if (sc.hasNextLine()) {
                // skip one blank line in between rounds of combat
                sc.nextLine();
            }
        }

        return combats.toArray(new RoundCombat[0]);
    }

    private RoundCombat parseCombat() {
        // follow format
        int preburn = parseBurn(sc.nextLine());
        boolean[] doubles = parseDoubles(sc.nextLine());
        boolean double1 = doubles[0];
        Attack attack1 = parseAttack(sc.nextLine());

        if (doubles.length == 1) {
            // only one attack
            String[] outcomes = parseOutcomes(sc.nextLine());
            return new RoundCombat(preburn, attack1, double1, outcomes);
        }

        // otherwise 2 attacks
        boolean double2 = doubles[1];
        Attack attack2 = parseAttack(sc.nextLine());
        String[] outcomes = parseOutcomes(sc.nextLine());

        return new RoundCombat(preburn, attack1, double1, attack2, double2, outcomes);
    }

    private String[] parseOutcomes(String s) {
        String[] words = s.split(" ");
        String[] outcomes = new String[words.length - 1];

        for (int i = 0; i < outcomes.length; i++) {
            outcomes[i] = words[i+1];
        }

        return outcomes;
    }

    // SYNTAX: [hit]h, [crit]c [_, b]
    private Attack parseAttack(String s) {
        String[] words = s.split(" ");
        // take off the last character of each token and convert to int
        int hit = Integer.parseInt(words[0].substring(0, words[0].length() - 1));
        int crit = Integer.parseInt(words[1].substring(0, words[1].length() - 1));

        boolean brave = false;
        if (words.length > 2) {
            if (words[2].equals("brave")) {
                brave = true;
            }
        }

        return new Attack(hit, crit, brave);
    }

    // SYNTAX: combat 1[d, _] [[2][d, _]]
    private boolean[] parseDoubles(String s) {
        String[] words = s.split(" ");
        boolean[] result = new boolean[words.length - 1]; // one token for each attack, -1 for "combat"

        for (int i = 1; i < words.length; i++) {
            result[i - 1] = words[i].endsWith("d");
        }

        return result;
    }

    // SYNTAX: burn [num]
    int parseBurn(String s) {
        String[] words = s.split(" ");
        return Integer.parseInt(words[1]);
    }
}
