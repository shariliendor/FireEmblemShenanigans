public class Main {
    public static void main(String[] args) {
        // output preferences
        boolean displayCombatInfo = true;
        boolean debug = false;

        System.out.println("Preprocessing...");

        String fileName = args[0];
        RCTextParser textParser = new RCTextParser();
        RoundCombat[] combats = textParser.parseCombats(fileName);

        System.out.println("Preprocessing complete");

        // text parser test
        if (displayCombatInfo) {
            for (RoundCombat combat: combats) {
                System.out.println(combat);
            }
        }

        SeedTester seedTester = new RCSeedTester(combats, debug);

        SeedFinder seedFinder = new SeedFinder(seedTester);

        seedFinder.findSeed();
        seedFinder.printOutput(40);
    }
}
