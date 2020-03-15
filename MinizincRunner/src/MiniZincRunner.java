import java.io.*;

public class MiniZincRunner {
    int numJobs;
    int numAgents;
    int[] processingTimes;
    int[][] preferences;
    int[] jobPosition;
    int[] completionTimes;
    double runtime;

    public void runMinizinc(File file) throws IOException {

        DataReader reader = new DataReader(file);
        numJobs = reader.numJobs;
        numAgents = reader.numAgents;
        processingTimes = reader.processingTimes;
        preferences = reader.preferences;

        long startTime = System.nanoTime();
        miniZincRunner("minizinc", "/Users/berendjanlange/GitDrive/TU Delft/Algorithms for Intelligent Decision Making/SocialScheduling/model.mzn", file);
        long endTime = System.nanoTime();
        runtime = (double) (endTime - startTime) / 1000000000;
    }

    public void miniZincRunner(String commd, String model, File dataset) throws IOException {
        Process process = new ProcessBuilder(commd, model, dataset + "").start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        String line1 = br.readLine();
        String[] schedulestring = line1.substring(line1.indexOf("[") + 1, line1.indexOf("]")).split(", ");
        this.jobPosition = new int[numJobs];
        for (int x = 0; x < numJobs; x++) {
            this.jobPosition[x] = Integer.parseInt(schedulestring[x]);
        }
        String line2 = br.readLine();
        String[] completionstring = line2.substring(line2.indexOf("[") + 1, line2.indexOf("]")).split(", ");
        this.completionTimes = new int[numJobs];
        for (int x = 0; x < numJobs; x++) {
            this.completionTimes[x] = Integer.parseInt(completionstring[x]);
        }
        return;
    }

    public int[] getOrdering() {
        return jobPosition;
    }

    public int[] getCompletionTimes() {
        return completionTimes;
    }

    public double getRuntime() {
        return runtime;
    }
}