import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MiniZincRunner {
    int numJobs;
    int numAgents;
    int[] processingTimes;
    //preferences = int[numAgents][numJobs];
    int[][] preferences;
    //on jobposition[0] it is specified which job comes first
    int[] jobPosition;
    int[][] agentcompletionTimes;
    int[] completionTimes;

    public void looper() throws IOException {
        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\RealisticSets");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (int problem = 0; problem < 1; problem++) {

                File file = directoryListing[problem];
                DataReader reader = new DataReader(file);
                numJobs = reader.numJobs;
                numAgents = reader.numAgents;
                processingTimes = reader.processingTimes;
                //preferences = int[numAgents][numJobs];
                preferences = reader.preferences;

//                System.out.println(numJobs);
//                System.out.println(numAgents);
//                System.out.println(Arrays.toString(processingTimes));
//                System.out.println(Arrays.toString(preferences[0]));
//                for (int y = 1; y < numAgents; y++) {
//                    System.out.println(Arrays.toString(preferences[y]));
//                }

                // Do something with child
                long startTime = System.nanoTime();
                miniZincRunner("minizinc", "C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\model.mzn", file);
                long endTime = System.nanoTime();
                long runtime = endTime - startTime;
                System.out.println("!!! Runtime = " + runtime/1000000000 + " seconds!!!");

            }
        } else {
            System.out.println("directory is empty");
        }
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
        System.out.println(Arrays.toString(jobPosition));
        System.out.println(Arrays.toString(completionTimes));
        return;
    }
}
