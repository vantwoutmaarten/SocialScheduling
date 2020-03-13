import sun.plugin.javascript.navig.Array;

import java.io.File;
import java.util.ArrayList;
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

    private int[] jobScore;
    private double runtime;

    public Problem(int numJobs, int numAgents, int[] processingTimes, int[][] preferences) {
        this.numJobs = numJobs;
        this.numAgents = numAgents;
        this.processingTimes = processingTimes;
        this.preferences = preferences;

        // Inverses are calculated as: if job 10 is preferred on position 3, then preferences[2] = 10 and preferencesInverse[9] = 3
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

    public void init() {
        PTACondorcetMatrix = new boolean[numJobs][numJobs];
        ParetoOptimalityMatrix = new boolean[numJobs][numJobs];

        //score Array
        jobScore = new int[numJobs];

        // Calculate jobscore for PTA Copeland's Method, PTACondorcetMatrix and ParetoOptimalityMatrix
        for (int jobk = 0; jobk < numJobs; jobk++) {
            for (int jobl = 0; jobl < numJobs; jobl++) {
                int count = 0;
                if (jobk != jobl) {
                    for (int agent = 0; agent < numAgents; agent++) {
                        if (preferencesInverse[agent][jobk] < preferencesInverse[agent][jobl]) {
                            count++;
                        }
                    }
                    if ((float) count >= ((float) processingTimes[jobk] / ((float) processingTimes[jobk] + (float) processingTimes[jobl]) * (float) numAgents)) {
                        jobScore[jobk]++;
                        PTACondorcetMatrix[jobk][jobl] = true;
                    }
                    ParetoOptimalityMatrix[jobk][jobl] = true;
                    for (int agent = 0; agent < numAgents; agent++) {
                        if (preferencesInverse[agent][jobk] > preferencesInverse[agent][jobl]) {
                            ParetoOptimalityMatrix[jobk][jobl] = false;
                            break;
                        }
                    }
                }
            }
        }
    }

    public int[] calculateVotingRuleOrdering() {
        // Job on position
        int[] algorithmOrdering = new int[numJobs];

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
        System.out.println("algorithmOrdering:" + Arrays.toString(algorithmOrdering));
        return algorithmOrdering;
    }

    public int[] calculateCompletionTimeInverse(int[] ordering) {
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

    public int[] calculateTardiness(int[] completionTimeAlgorithmInverse) {
        // Tardiness
        int[] tardinessPerAgent = new int[numAgents];
        for (int a = 0; a < numAgents; a++) {
            for (int j = 0; j < numJobs; j++) {
                tardinessPerAgent[a] += Math.max(0, completionTimeAlgorithmInverse[j] - completionTimePreferencesInverse[a][j]);
            }
        }
        System.out.println("Calculated Total Tardiness: " + Arrays.stream(tardinessPerAgent).sum());
        return tardinessPerAgent;
    }

    public boolean isParetoOptimal(int[] ordering) {
        for (int j1 = numJobs - 1; j1 > 0; j1--) {
            for (int j2 = j1 - 1; j2 >= 0; j2--) {
                if (ParetoOptimalityMatrix[ordering[j1] - 1][ordering[j2] - 1]) {
                    System.out.println("Calculated isParetoOptimal: " + false);
                    return false;
                }
            }
        }
        System.out.println("Calculated isParetoOptimal: " + true);
        return true;
    }

    public boolean isPTACondorcetMatrix(int[] ordering) {
        for (int j1 = numJobs - 1; j1 > 0; j1--) {
            for (int j2 = j1 - 1; j2 >= 0; j2--) {
                if (PTACondorcetMatrix[ordering[j1] - 1][ordering[j2] - 1]) {
                    System.out.println("Calculated isPTACondorcetMatrix: " + false);
                    return false;
                }
            }
        }
        System.out.println("Calculated isPTACondorcetMatrix: " + true);
        return true;
    }

    public float calculateGiniCoefficient(int[] tardinessPerAgent) {
        Arrays.sort(tardinessPerAgent);
        ArrayList<Integer> amount = new ArrayList<>();
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Float> prob = new ArrayList<>();
        amount.add(tardinessPerAgent[0]);
        counter.add(1);
        for (int i = 1; i < numAgents; i++) {
            if (tardinessPerAgent[i] == amount.get(amount.size() - 1)) {
                int next = counter.remove(counter.size() - 1);
                counter.add(++next);
            } else {
                prob.add(((float)counter.get(counter.size() - 1) / (float)numAgents));
                amount.add(tardinessPerAgent[i]);
                counter.add(1);
            }
        }
        prob.add(((float)counter.get(counter.size() - 1) / (float)numAgents));

        int[] sWages = new int[prob.size()];
        for (int i = 0; i < prob.size(); i++) {
            for (int j = 0; j <= i; j++) {
                sWages[i] += prob.get(j) * amount.get(j);
            }
        }

        float gWages = sWages[0] * prob.get(0);
        for (int i = 1; i < sWages.length; i++) {
            gWages += prob.get(i) * (sWages[i] + sWages[i - 1]);
        }
        float giniCoefficient = 1 - (gWages / sWages[sWages.length - 1]);

        System.out.println("Calculated Gini Coefficient: " + giniCoefficient);

        return giniCoefficient;
    }

    public double getRuntime() {
        return runtime;
    }
}