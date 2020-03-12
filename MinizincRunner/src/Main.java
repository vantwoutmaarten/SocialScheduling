import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException {
//        MiniZincRunner runMZ = new MiniZincRunner();
//        runMZ.looper();
        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\RealisticSets");
        File[] directoryListing = dir.listFiles();
//        for (int i = 0; i < directoryListing.length; i++)
        for (int i = 0; i < 1; i++) {

            File file = directoryListing[i];
            DataReader reader = new DataReader(file);
            int numJobs = reader.numJobs;
            int numAgents = reader.numAgents;
            int[] processingTimes = reader.processingTimes;
            //preferences = int[numAgents][numJobs];
            int[][] preferences = reader.preferences;

            System.out.println(numJobs);
            System.out.println(numAgents);
            System.out.println(Arrays.toString(processingTimes));
            System.out.println(Arrays.toString(preferences[0]));
            for (int y = 1; y < numAgents; y++) {
                System.out.println(Arrays.toString(preferences[y]));
            }

            //give job 1 a score of 1 if p_1/(p_1+p_2)*numAgents >
            int _job1 = 0;

            for (int agent = 0; agent < numAgents; agent++) {
                for (int job = 0; job < numJobs; job++) {

                }
            }




        }
    }
}