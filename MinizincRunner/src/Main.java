import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        String csvFile = "realisticPerformance.csv";
        FileWriter writer = new FileWriter(csvFile);
        CSVUtils.writeLine(writer, Arrays.asList("filename", "numAgents", "numJobs", "MZtardiness", "MZisPTACondorcet", "MZisParetoOptimal", "MZgini", "MZRuntime", "CLtardiness", "CPisPTACondorcet", "isParetoOptimal", "gini", "runtime"));

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
            int[] MZtardinessPerAgent = problem.calculateTardiness(MZCompletionTimes);
            int MZsumTardiness = Arrays.stream(MZtardinessPerAgent).sum();
            boolean MZisPTACondorcetMatrix = problem.isPTACondorcetMatrix(MZOrdering);
            boolean MZisParetoOptimal = problem.isParetoOptimal(MZOrdering);
            float MZgini = problem.calculateGiniCoefficient(MZtardinessPerAgent);
            double MZRuntime = runMZ.getRuntime();

            System.out.println("\nCalculating criteria for new voting rule.");
            long startTime = System.nanoTime();
            int[] ordering = problem.calculateVotingRuleOrdering();
            int[] completionTimes = problem.calculateCompletionTimeInverse(ordering);
            int[] tardinessPerAgent = problem.calculateTardiness(completionTimes);
            int sumTardiness = Arrays.stream(tardinessPerAgent).sum();
            boolean isPTACondorcetMatrix = problem.isPTACondorcetMatrix(ordering);
            boolean isParetoOptimal = problem.isParetoOptimal(ordering);
            float gini = problem.calculateGiniCoefficient(tardinessPerAgent);
            double runtime = (System.nanoTime() - startTime) / 1000000000;

            CSVUtils.writeLine(writer, Arrays.asList(filename, String.valueOf(numAgents), String.valueOf(numJobs), String.valueOf(MZsumTardiness), String.valueOf(MZisPTACondorcetMatrix), String.valueOf(MZisParetoOptimal), String.valueOf(MZgini), String.valueOf(MZRuntime),  String.valueOf(sumTardiness), String.valueOf(isPTACondorcetMatrix), String.valueOf(isParetoOptimal), String.valueOf(gini), String.valueOf(runtime)));

        }
        writer.flush();
        writer.close();

    }
}