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
            problem.solve();
            int tardiness = problem.getTardiness(problem.getCompletionTimeInverse(problem.algorithmOrdering));

            boolean isPTACondorcetMatrix = problem.isPTACondorcetMatrix();
            boolean isParetoOptimal = problem.isParetoOptimal();

            System.out.println(tardiness);
            System.out.println(isPTACondorcetMatrix);
            System.out.println(isParetoOptimal);
        }
    }
}
