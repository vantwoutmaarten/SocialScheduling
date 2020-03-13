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
        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\RealisticSets");
        File[] directoryListing = dir.listFiles();
        for (int problem = 0; problem < directoryListing.length && problem < 1; problem++) {
            File file = directoryListing[problem];
            DataReader reader = new DataReader(file);
            numJobs = reader.numJobs;
            numAgents = reader.numAgents;
            processingTimes = reader.processingTimes;
            int[][] preferences = reader.preferences;
            int[][] preferencesInverse = new int[numAgents][numJobs];
            int[][] completionTimePreferences = calculateCompletionTime(preferences);
            int[][] completionTimePreferencesInverse = new int[numAgents][numJobs];
            int[][] PTACondorcetMatrix = new int[numJobs][numJobs];

            for (int a = 0; a < numAgents; a++) {
                for (int j = 0; j < numJobs; j++) {
                    preferencesInverse[a][preferences[a][j] - 1] = j + 1;
                    completionTimePreferencesInverse[a][preferences[a][j] - 1] = completionTimePreferences[a][j];
                }
            }

            //score Array
            int[] jobScore = new int[numJobs];

            for (int jobk = 0; jobk < numJobs; jobk++) {
                for (int jobl = 0; jobl < numJobs; jobl++) {
                    int count = 0;
                    if (jobk != jobl) {
                        for (int agent = 0; agent < numAgents; agent++) {
                            if (preferencesInverse[agent][jobk] < preferencesInverse[agent][jobl]) {
                                count++;
                            }
                        }
                        if ((float) count >= (((float) processingTimes[jobk] / ((float) processingTimes[jobk] + (float) processingTimes[jobl])) * (float) numAgents)) {
                            jobScore[jobk]++;
                            PTACondorcetMatrix[jobk][jobl] = 1;
                        }
                    }
                }
            }
            /*
            Here happens the minizinc stuff
             */
            MiniZincRunner runMZ = new MiniZincRunner();
            runMZ.looper();
            runMZ.checkPTACondorcet(PTACondorcetMatrix);


            // Job on position
            int[] jobPosition = new int[numJobs];

            // Used Job
            boolean[] jobUsed = new boolean[numJobs];

            for (int i = 0; i < numJobs; i++) {
                int score = -1;
                int index = -1;
                for (int j = 0; j < numJobs; j++) {
                    if (score < jobScore[j] && jobUsed[j] == false) {
                        score = jobScore[j];
                        index = j;
                    }
                }
                jobUsed[index] = true;
                jobPosition[i] = index + 1;
            }

//            System.out.println(numJobs);
//            System.out.println(numAgents);
//            System.out.println(Arrays.toString(processingTimes));
//            for (int y = 0; y < numAgents; y++) {
//                System.out.println(Arrays.toString(preferences[y]));
//            }
//            System.out.println("\n");
//            for (int y = 0; y < numAgents; y++) {
//                System.out.println(Arrays.toString(preferencesInverse[y]));
//            }
//            System.out.println("\n");
//            for (int y = 0; y < numAgents; y++) {
//                System.out.println(Arrays.toString(completionTimePreferences[y]));
//            }
//            System.out.println("\n");
//            for (int y = 0; y < numAgents; y++) {
//                System.out.println(Arrays.toString(completionTimePreferencesInverse[y]));
//            }

//            System.out.println("Jobscore: ");
//            for(int q = 0; q < jobScore.length; q++){
//                System.out.print(jobScore[q] + ", ");
//            }

//            System.out.println("\njobPosition: ");
//            for(int q = 0; q < jobPosition.length; q++){
//                System.out.print(jobPosition[q] + ", ");
//            }
        }
    }

    public static int[][] calculateCompletionTime(int[][] jobOrdering) {
        int[][] completionTimes = new int[numAgents][numJobs];
        for (int a = 0; a < numAgents; a++) {
            completionTimes[a][0] = processingTimes[jobOrdering[a][0] - 1];
            for (int j = 1; j < numJobs; j++) {
                completionTimes[a][j] = completionTimes[a][j - 1] + processingTimes[jobOrdering[a][j] - 1];
            }
        }
        return completionTimes;
    }
}