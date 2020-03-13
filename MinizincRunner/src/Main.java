import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) throws IOException {
        MiniZincRunner runMZ = new MiniZincRunner();
        runMZ.looper();
        int[] MZOrdering = runMZ.getOrdering();
        int[] MZCompletionTimes = runMZ.getCompletionTimes();
        File dir = new File("/Users/berendjanlange/GitDrive/TU Delft/Algorithms for Intelligent Decision Making/SocialScheduling/TestSetGenerator/TestSets/RealisticSets");
        File[] directoryListing = dir.listFiles();
        for (int p = 0; p < directoryListing.length && p < 1; p++) {
            File file = directoryListing[p];
            DataReader reader = new DataReader(file);
            int numJobs = reader.numJobs;
            int numAgents = reader.numAgents;
            int[] processingTimes = reader.processingTimes;
            int[][] preferences = reader.preferences;
            Problem problem = new Problem(numJobs, numAgents, processingTimes, preferences);
            problem.init();

            System.out.println("\nCalculating criteria for minizinc output.");
            int mztardiness = problem.calculateTardiness(MZCompletionTimes);
            boolean mzisPTACondorcetMatrix = problem.isPTACondorcetMatrix(MZOrdering);
            boolean mzisParetoOptimal = problem.isParetoOptimal(MZOrdering);

            System.out.println("\nCalculating criteria for new voting rule.");
            int[] ordering = problem.calculateVotingRuleOrdering();
            int[] completionTimes = problem.calculateCompletionTimeInverse(ordering);
            int tardiness = problem.calculateTardiness(completionTimes);
            boolean isPTACondorcetMatrix = problem.isPTACondorcetMatrix(ordering);
            boolean isParetoOptimal = problem.isParetoOptimal(ordering);

        }
    }
}
