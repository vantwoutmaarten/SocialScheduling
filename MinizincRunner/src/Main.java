import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Main {
    public static void main(String[] args) throws IOException {
        MiniZincRunner runMZ = new MiniZincRunner();
        runMZ.looper();
//        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\RealisticSets");
//        File[] directoryListing = dir.listFiles();
//        for (int problem = 35; problem < 36; problem++) {
//
//            File file = directoryListing[problem];
//            DataReader reader = new DataReader(file);
//            int numJobs = reader.numJobs;
//            int numAgents = reader.numAgents;
//            int[] processingTimes = reader.processingTimes;
//            //preferences = int[numAgents][numJobs];
//            int[][] preferences = reader.preferences;
//            int[][] preferencesInverse = new int[numAgents][numJobs];
//
//            for (int a = 0; a < numAgents; a++) {
//                for (int j = 0; j < numJobs; j++) {
//                    preferencesInverse[a][preferences[a][j] - 1] = j + 1;
//                }
//            }
//
//            System.out.println(numJobs);
//            System.out.println(numAgents);
//            System.out.println(Arrays.toString(processingTimes));
//            System.out.println(Arrays.toString(preferences[0]));
//            for (int y = 1; y < numAgents; y++) {
//                System.out.println(Arrays.toString(preferences[y]));
//            }
//
//            //score Array
//            int[] jobScore = new int[numJobs];
//
//            for (int jobk = 0; jobk < numJobs; jobk++) {
//                for (int jobl = 0; jobl < numJobs; jobl++) {
//                    int count = 0;
//                    if (jobk != jobl) {
//                        for (int agent = 0; agent < numAgents; agent++) {
//                            if (preferencesInverse[agent][jobk] < preferencesInverse[agent][jobl]) {
//                                count++;
//                            }
//                        }
//                        if ((float) count >= (((float) processingTimes[jobk] / ((float) processingTimes[jobk] + (float) processingTimes[jobl])) * (float) numAgents)) {
//                            jobScore[jobk]++;
//                        }
//                    }
//                }
//            }
//
//            System.out.println("Jobscore: ");
//            for(int q = 0; q < jobScore.length; q++){
//                System.out.print(jobScore[q] + ", ");
//            }
//
//            // Job on position
//            int[] positionJob = new int[numJobs];
//
//            // Used Job
//            boolean[] jobUsed = new boolean[numJobs];
//
//            for (int i = 0; i < numJobs; i++) {
//                int score = -1;
//                int index = -1;
//                for (int j = 0; j < numJobs; j++) {
//                    if (score < jobScore[j] && jobUsed[j] == false) {
//                        score = jobScore[j];
//                        index = j;
//                    }
//                }
//                jobUsed[index] = true;
//                positionJob[i] = index + 1;
//            }
//
//            System.out.println("\nPositionJob: ");
//            for(int q = 0; q < positionJob.length; q++){
//                System.out.print(positionJob[q] + ", ");
//            }
//        }
    }
}