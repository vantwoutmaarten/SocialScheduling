        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException {
        MiniZincRunner runMZ = new MiniZincRunner();
        runMZ.looper();
        File dir = new File("/Users/berendjanlange/GitDrive/TU Delft/Algorithms for Intelligent Decision Making/SocialScheduling/TestSetGenerator/TestSets/examples");
        File[] directoryListing = dir.listFiles();
        for (int i = 0; i < directoryListing.length; i++) {
            File file = directoryListing[i];
            DataReader reader = new DataReader(file);
            int numJobs = reader.numJobs;
            int numAgents = reader.numAgents;
            int[] processingTimes = reader.processingTimes;
            int[][] preferences = reader.preferences;

            System.out.println(numJobs);
            System.out.println(numAgents);
            System.out.println(Arrays.toString(processingTimes));
            System.out.println(Arrays.toString(preferences[0]));
            for (int y = 1; y < numAgents; y++) {
                System.out.println(Arrays.toString(preferences[y]));
            }
        }
    }
}