public class RNGUtil {
    public static final int[] DEFAULT_SEED = {5270, 37098, 13937};

    public static int nextRNG(int[] seed) {
        return nextRNG(seed[0], seed[1], seed[2]);
    }
    public static int nextRNG(int r1, int r2, int r3) {
        return (r3 >> 5 ^ r2 << 11 ^ r1 << 1 ^ r2 >> 15) & 0xFFFF;
    }

    public static int getNum(int rn) {
        return (int) Math.floor(rn / 655.36);
    }

    public static int[] nextSeed(int[] seed) {
        int newRN = nextRNG(seed);
        return new int[] {seed[1], seed[2], newRN};
    }
}
