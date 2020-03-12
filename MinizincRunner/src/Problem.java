import java.io.File;
import java.util.Arrays;

public class Problem {

    public int numJobs;
    public int numAgents;
    public int[] processingTimes;
    public int[][] preferences;
    private int[][] completionTimePreferencesInverse;
    private int[][] preferencesInverse;
    private int[][] completionTimePreferences;

    private boolean[][] PTACondorcetMatrix;
    private boolean[][] ParetoOptimalityMatrix;

    public int[] algorithmOrdering;

    public Problem(int numJobs, int numAgents, int[] processingTimes, int[][] preferences) {
        this.numJobs = numJobs;
        this.numAgents = numAgents;
        this.processingTimes = processingTimes;
        this.preferences = preferences;

        preferencesInverse = new int[numAgents][numJobs];
        completionTimePreferences = new int[numAgents][numJobs];
        completionTimePreferencesInverse = new int[numAgents][numJobs];

        for (int a = 0; a < numAgents; a++) {
            completionTimePreferences[a][0] = processingTimes[preferences[a][0] - 1];
            for (int j = 1; j < numJobs; j++) {
                completionTimePreferences[a][j] = completionTimePreferences[a][j - 1] + processingTimes[preferences[a][j] - 1];
            }
        }

        for (int a = 0; a < numAgents; a++) {
            for (int j = 0; j < numJobs; j++) {
                preferencesInverse[a][preferences[a][j] - 1] = j + 1;
                completionTimePreferencesInverse[a][preferences[a][j] - 1] = completionTimePreferences[a][j];
            }
        }
    }

    public void solve() {


        PTACondorcetMatrix = new boolean[numJobs][numJobs];
        ParetoOptimalityMatrix = new boolean[numJobs][numJobs];

        //score Array
        int[] jobScore = new int[numJobs];

        // Calculate jobscore for PTA Copeland's Method, PTACondorcetMatrix and ParetoOptimalityMatrix
        for (int jobk = 0; jobk < numJobs; jobk++) {
            toNextJobl:
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
                        PTACondorcetMatrix[jobk][jobl] = true;
                    }
                    for (int agent = 0; agent < numAgents; agent++) {
                        if (preferencesInverse[agent][jobk] < preferencesInverse[agent][jobl]) {
                            break toNextJobl;
                        }
                    }
                    ParetoOptimalityMatrix[jobk][jobl] = true;
                }
            }
        }

        // Job on position
        algorithmOrdering = new int[numJobs];

        // Used Job
        boolean[] jobUsed = new boolean[numJobs];

        // Calculate job position from job scores from PTA Copeland's Method.
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
            algorithmOrdering[i] = index + 1;
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

    public int[] getCompletionTimeInverse(int[] ordering) {
        // Calculate completionTimeAlgorithm and completionTimeAlgorithmInverse
        int[] completionTimeAlgorithm = new int[numJobs];
        int[] completionTimeAlgorithmInverse = new int[numJobs];
        int sum = 0;
        for (int j = 0; j < numJobs; j++) {
            sum += processingTimes[ordering[j] - 1];
            completionTimeAlgorithm[j] = sum;
            completionTimeAlgorithmInverse[ordering[j] - 1] = completionTimeAlgorithm[j];
        }
        return completionTimeAlgorithmInverse;
    }

    public int getTardiness(int[] completionTimeAlgorithmInverse) {
        int tardiness = 0;
        System.out.println(Arrays.toString(completionTimeAlgorithmInverse));
        System.out.println("\n");
        for (int a = 0; a < numAgents; a++) {
            System.out.println(Arrays.toString(completionTimePreferencesInverse[a]));
            for (int j = 0; j < numJobs; j++) {
                tardiness += Math.max(0, completionTimeAlgorithmInverse[j] - completionTimePreferencesInverse[a][j]);
            }
        }
        return tardiness;
    }

    public boolean isParetoOptimal() {
        for (int j1 = numJobs - 1; j1 > 0; j1--) {
            for (int j2 = j1 - 1; j2 >= 0; j2--) {
                if (ParetoOptimalityMatrix[j1][j2]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPTACondorcetMatrix() {
        for (int j1 = numJobs - 1; j1 > 0; j1--) {
            for (int j2 = j1 - 1; j2 >= 0; j2--) {
                if (PTACondorcetMatrix[j1][j2]) {
                    return false;
                }
            }
        }
        return true;
    }

}
