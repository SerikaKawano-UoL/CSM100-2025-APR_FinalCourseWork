package main;

import game.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Runs the programme via the text (console) interface.
 */
public class TXTmain {
    public static void main(String[] args) {
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        int repeatNumberIndex = argList.indexOf("-n");
        int numTimesToRun = 1;
        if (repeatNumberIndex >= 0) {
            try {
                numTimesToRun = Math.max(Integer.parseInt(argList.get(repeatNumberIndex + 1)), 1);
            } catch (Exception e) {
                System.err.println("Couldn't parse argument for -n option");
            }
        }

        // minseed, maxseed options
        long minSeed = 0;
        long maxSeed = 0;
        int minSeedIndex = argList.indexOf("-minseed");
        int maxSeedIndex = argList.indexOf("-maxseed");
        boolean useSeedRange = false;
        if (minSeedIndex >= 0 && maxSeedIndex >= 0) {
            try {
                minSeed = Long.parseLong(argList.get(minSeedIndex + 1));
                maxSeed = Long.parseLong(argList.get(maxSeedIndex + 1));
                if (minSeed > maxSeed) {
                    System.err.println("minseed must be less than or equal to maxseed");
                    return;
                }
                useSeedRange = true;
                numTimesToRun = (int)(maxSeed - minSeed + 1);
            } catch (Exception e) {
                System.err.println("Couldn't parse arguments for -minseed or -maxseed option");
                return;
            }
        }

        Optional<Long> seed = Utilities.parseSeedArgs(args);

        double totalScore = 0;

        for (int i = 0; i < numTimesToRun; i++) {
            long currentSeed;
            if (useSeedRange) {
                currentSeed = minSeed + i;
            } else {
                currentSeed = (seed.isPresent() ? seed.get() : 0);
            }
            double bonus = GameState.runNewGame(currentSeed, false);
            System.out.println("Bonus for run " + (i + 1) + " (seed=" + currentSeed + "): " + bonus);
            totalScore += bonus;
            System.out.println();
        }

        if (numTimesToRun > 0) {
            double average = totalScore / (double) numTimesToRun;
            System.out.println("Average bonus : " + average);
        }
    }
}
