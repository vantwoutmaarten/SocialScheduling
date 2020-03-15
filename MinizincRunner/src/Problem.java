import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Problem class is generated for each individual problem.
 */
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

    /**
     * Initializer for problem, also calculates the preferencesInverse, completionTimePreferences and completionTimePreferencesInverse.
     * @param numJobs               Number of jobs in problem
     * @param numAgents             Number of agents in problem
     * @param processingTimes       List of processing times per job
     * @param preferences           Matrix of preference list per agent
     */
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
            preferencesInverse[a][preferences[a][0] - 1] = 1;
            completionTimePreferencesInverse[a][preferences[a][0] - 1]  = completionTimePreferences[a][0];
            for (int j = 1; j < numJobs; j++) {
                completionTimePreferences[a][j] = completionTimePreferences[a][j - 1] + processingTimes[preferences[a][j] - 1];
                preferencesInverse[a][preferences[a][j] - 1] = j + 1;
                completionTimePreferencesInverse[a][preferences[a][j] - 1] = completionTimePreferences[a][j];
            }
        }
//        System.out.println("Preferences");
//        for (int i = 0; i < numAgents; i++) {
//            System.out.println(Arrays.toString(preferences[i]));
//        }
//        System.out.println("Preferences Inverse");
//        for (int i = 0; i < numAgents; i++) {
//            System.out.println(Arrays.toString(preferencesInverse[i]));
//        }
//        System.out.println("processingTimes");
//        System.out.println(Arrays.toString(processingTimes));
    }

    /**
     * Calculates jobScore, PTACondorcetMatrix and ParetoOptimalityMatrix.
     */
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
                    ParetoOptimalityMatrix[jobk][jobl] = true;
                    for (int agent = 0; agent < numAgents; agent++) {
                        if (preferencesInverse[agent][jobk] < preferencesInverse[agent][jobl]) {
                            count++;
                        } else {
                            ParetoOptimalityMatrix[jobk][jobl] = false;
                        }
                    }
                    if ((float) count >= ((float) processingTimes[jobk] / ((float) processingTimes[jobk] + (float) processingTimes[jobl]) * (float) numAgents)) {
                        jobScore[jobk]++;
                        PTACondorcetMatrix[jobk][jobl] = true;
                    }
                }
            }
        }
//        System.out.println("jobScore");
//        System.out.println(Arrays.toString(jobScore));
//        System.out.println("Pareto");
//        for (int i = 0; i < numJobs; i++) {
//            System.out.println(Arrays.toString(ParetoOptimalityMatrix[i]));
//        }
//        System.out.println("PTACondorcet");
//        for (int i = 0; i < numJobs; i++) {
//            System.out.println(Arrays.toString(PTACondorcetMatrix[i]));
//        }
    }

    /**
     * Calculates the collective schedule based on Copeland's voting rule.
     * @return array of jobs in order.
     */
    public int[] calculateVotingRuleOrdering() {
        // Job on position
        int[] algorithmOrdering = new int[numJobs];

        // Used Job
        boolean[] jobUsed = new boolean[numJobs];

        // Ties Breaker
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) return 1;
                else if (o2 < o1) return -1;
                else return 0;
            }
        });
        for (int i = 0; i < numJobs; i++) {
            queue.add(jobScore[i]);
        }

        // Calculate job position from job scores from PTA Copeland's Method.
        for (int i = 0; i < numJobs; i++) {
            ArrayList<Integer> ties = new ArrayList<>();
            int score = queue.poll();
            for (int j = 0; j < numJobs; j++) {
                if (score == jobScore[j] && !jobUsed[j]) {
                    ties.add(j);
                }
            }
            // If multiple, choose one that does not violate Condorcet Criteria, if not possible choose one that does not violate ParetoOptimality
            if (ties.size() > 1) {
                int j = tieBreaker(PTACondorcetMatrix, ties);
                if (j == -1) {
                    j = tieBreaker(ParetoOptimalityMatrix, ties);
                }
                jobUsed[ties.get(j)] = true;
                algorithmOrdering[i] = ties.get(j) + 1;
            } else {
                jobUsed[ties.get(0)] = true;
                algorithmOrdering[i] = ties.get(0) + 1;
            }
        }
        System.out.println("algorithmOrdering:" + Arrays.toString(algorithmOrdering));
        return algorithmOrdering;
    }

    /**
     * If 2 jobs have equal score we use a tieBreaker, this checks for jobs which are not preferred by another job
     * in the list of ties. If all jobs are dominated by another job in the list, we have cyclical domination.
     * Cyclical domination can occur for the PTACondorcetMatrix, not for ParetoOptimalityMatrix.
     * @param matrix        Matrix to check whether a job is preferred
     * @param ties          List of jobs with equal jobScore
     * @return index of preferred job in list of ties or -1 if cyclically preferred.
     */
    private int tieBreaker(boolean[][] matrix, ArrayList<Integer> ties) {
        for (int j = 0; j < ties.size(); j++) {
            boolean dominated = false;
            for (int k = 0; k < ties.size(); k++) {
                if (j != k) {
                    if (matrix[k][j]) {
                        dominated = true;
                    }
                }
            }
            if (!dominated) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Calculate completionTimeAlgorithm and completionTimeAlgorithmInverse
     * @param ordering      Collective schedule
     * @return              Returns array of completion times of jobs ordered by index of job.
     */
    public int[] calculateCompletionTimeInverse(int[] ordering) {
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

    /**
     * Calculates tardiness per agent.
     * @param completionTimeAlgorithmInverse    Array of completion times of jobs ordered by index of job.
     * @return                                  Array of length numAgents with tardiness.
     */
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

    /**
     * Checks whether the collective schedule is Pareto Optimal.
     * @param ordering      Collective schedule
     * @return              true if Pareto Optimal
     */
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

    /**
     * Checks whether the collective schedule is PTA Condorcet consistent.
     * @param ordering      Collective schedule
     * @return              true if Condorcet consistent
     */
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

    /**
     * Calculates the Gini Coefficient for inequality from tardiness per agent.
     * Source: https://stackoverflow.com/questions/31321810/gini-coefficient-in-julia-efficient-and-accurate-code)
     * (!) PERMANTENTLY CHANGES PARAMETER tardinessPerAgent ARRAY (!)
     * @param tardinessPerAgent     Lateness for each agent based on collective schedule
     * @return                      Gini Coefficient between 0 and 1.
     */
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
}
