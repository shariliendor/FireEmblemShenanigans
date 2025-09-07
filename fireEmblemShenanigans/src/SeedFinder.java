import java.math.BigInteger;
import java.util.Arrays;

public class SeedFinder {
    private static final boolean lethalityCheck = true;
    private static final int rnArrayLength = 128;
    private SeedTester seedTester;
    private int[] startingSeed; // the seed it starts checking from

    private int[] arrayStartSeed, arrayMidSeed, arrayEndSeed;
    private int[] rns;

    private BigInteger burned = new BigInteger("0");
    private int[] finalSeed = {0, 0, 0};
    private int finalRNStart;

    public SeedFinder(int[] startingSeed, SeedTester seedTester) {
        initialize(startingSeed, seedTester);
    }

    public SeedFinder(SeedTester seedTester) {
        initialize(RNGUtil.DEFAULT_SEED, seedTester);
    }

    private void initialize(int[] startingSeed, SeedTester seedTester) {
        this.startingSeed = startingSeed;
        this.arrayEndSeed = startingSeed;
        this.seedTester = seedTester;

        // call advanceRNArray twice to fill it up entirely
        rns = new int[rnArrayLength];
        advanceRNArray();
        advanceRNArray();
    }

    private void advanceRNArray() {
        System.arraycopy(rns, rns.length / 2, rns, 0, rns.length / 2);
        arrayStartSeed = arrayMidSeed;
        arrayMidSeed = arrayEndSeed;

        for (int i = rns.length / 2; i < rns.length; i++) {
            advanceRNG();
            rns[i] = RNGUtil.getNum(arrayEndSeed[2]);
        }

        burned = burned.add(new BigInteger(String.valueOf(rns.length / 2)));
    }

    private void advanceRNG() {
        arrayEndSeed = RNGUtil.nextSeed(arrayEndSeed);
    }

    public int[] findSeed() {
        int i = 0;
        while (!seedTester.testSeed(i, rns)) {
            i++;
            if (i >= rns.length / 2) {
                advanceRNArray();
                i -= rns.length / 2;
            }
        }

        burned = getBurned(i);
        finalSeed = getExactSeed(i);
        finalRNStart = i;

        return finalSeed;
    }

    private int[] getExactSeed(int index) {
        // burn RNs from the previous seed up to the index
        int[] seed = arrayStartSeed;
        for (int i = 0; i < index; i++) {
            seed = RNGUtil.nextSeed(seed);
        }
        return seed;
    }

    private BigInteger getBurned(int index) {
        return burned.subtract(new BigInteger(String.valueOf(rns.length - index)));
    }

    public void printOutput(int numRNs) {
        System.out.println("Burned: " + burned);
        System.out.println("Seed: " + Arrays.toString(finalSeed));
        System.out.println("RNs:");

        for (int i = finalRNStart; i < finalRNStart + numRNs; i++) {
            System.out.println(rns[i]);
        }
    }

    public BigInteger getBurned() {
        return burned;
    }

    public int[] getStartingSeed() {
        return startingSeed;
    }

    public static boolean isLethalityCheck() {
        return lethalityCheck;
    }
}
