import java.io.*;

public class MiniZincRunner {

    public static void looper()  throws IOException {
        File dir = new File("/Users/berendjanlange/GitDrive/TU Delft/Algorithms for Intelligent Decision Making/SocialScheduling/TestSetGenerator/TestSets/examples");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (int i = 0; i < directoryListing.length; i++) {
                // Do something with child
                File file = directoryListing[i];
                long startTime = System.nanoTime();
                miniZincRunner("minizinc", "/Users/berendjanlange/GitDrive/TU Delft/Algorithms for Intelligent Decision Making/SocialScheduling/model.mzn", file);
                long endTime = System.nanoTime();
                long runtime = endTime - startTime;
                System.out.println("!!! Runtime = " + runtime + "nanoseconds!!!");
            }
        } else {
            System.out.println("directory is empty");
        }
    }

    public static void miniZincRunner(String commd, String model, File dataset)  throws IOException {
        Process process = new ProcessBuilder(commd, model, dataset+"").start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        System.out.printf("Output of running dataset %s is:", dataset.getName());

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        return;
    }
}
