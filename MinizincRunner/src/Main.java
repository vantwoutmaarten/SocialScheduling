import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Main {
    static int numAgents;
    static int numJobs;
    static int[] processingTimes;

    public static void main(String[] args) throws IOException {
        String csvFile = "realisticPerformance.csv";
        FileWriter writer = new FileWriter(csvFile);
        CSVUtils.writeLine(writer, Arrays.asList("filename", "numAgents", "numJobs", "MZtardiness", "MZisPTACondorcet", "MZisParetoOptimal", "MZRuntime", "CLtardiness", "CPisPTACondorcet", "isParetoOptimal"));


        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\RealisticSets");
        File[] directoryListing = dir.listFiles();
        for (int p = 0; p < directoryListing.length; p++) {
            File file = directoryListing[p];
            String filename = file.getName();
            MiniZincRunner runMZ = new MiniZincRunner();
            runMZ.runMinizinc(file);
            int[] MZOrdering = runMZ.getOrdering();
            int[] MZCompletionTimes = runMZ.getCompletionTimes();

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
            double mzRuntime = runMZ.getRuntime();

            System.out.println("\nCalculating criteria for new voting rule.");
            int[] ordering = problem.calculateVotingRuleOrdering();
            int[] completionTimes = problem.calculateCompletionTimeInverse(ordering);
            int tardiness = problem.calculateTardiness(completionTimes);
            boolean isPTACondorcetMatrix = problem.isPTACondorcetMatrix(ordering);
            boolean isParetoOptimal = problem.isParetoOptimal(ordering);
            double CLRuntime = problem.getRuntime();

            CSVUtils.writeLine(writer, Arrays.asList(filename, String.valueOf(numAgents), String.valueOf(numJobs), String.valueOf(mztardiness), String.valueOf(mzisPTACondorcetMatrix), String.valueOf(mzisParetoOptimal), String.valueOf(mzRuntime),  String.valueOf(tardiness), String.valueOf(isPTACondorcetMatrix), String.valueOf(isParetoOptimal), String.valueOf(CLRuntime)));

        }
        writer.flush();
        writer.close();
    }
}