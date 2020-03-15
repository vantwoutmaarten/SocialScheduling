import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {
    int numJobs;
    int numAgents;
    int [] processingTimes;
    int[][] preferences;

    public DataReader(File file) throws IOException {

            String filename = file.getName();
            System.out.println("Filename: " + filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line1 = br.readLine();
            this.numJobs = Integer.parseInt(line1.substring(line1.indexOf("= ") + 2, line1.indexOf(";")));

            String line2 = br.readLine();
            this.numAgents = Integer.parseInt(line2.substring(line2.indexOf("= ") + 2, line2.indexOf(";")));

            String line3 = br.readLine();
            String[] proctimesstring = line3.substring(line3.indexOf("[") + 1, line3.indexOf("]")).split(", ");
            this.processingTimes = new int[numJobs];
            for (int x = 0; x < numJobs; x++) {
                this.processingTimes[x] = Integer.parseInt(proctimesstring[x]);
            }

            this.preferences = new int[numAgents][numJobs];
            String line4 = br.readLine();
            String[] pref0 = line4.substring(line4.indexOf("|") + 1, line4.indexOf("|", line4.indexOf("|") + 1)).split(", ");
            for (int x = 0; x < numJobs; x++) {
                this.preferences[0][x] = Integer.parseInt(pref0[x]);
            }

            for (int y = 1; y < numAgents; y++) {
                String line = br.readLine();
                String[] pref = line.substring(line.indexOf(""), line.indexOf("|")).split(", ");
                for (int x = 0; x < numJobs; x++) {
                    this.preferences[y][x] = Integer.parseInt(pref[x]);
                }
            }
            br.close();
    }
}
