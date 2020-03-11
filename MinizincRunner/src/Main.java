import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //MiniZincRunner runMZ = new MiniZincRunner();
        //runMZ.looper();

        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\examples");
        File[] directoryListing = dir.listFiles();
        for (int i = 0; i <  directoryListing.length; i++) {
            File file = directoryListing[i];
            String filename = file.getName();
            System.out.println("Filename: " + filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line1 = br.readLine();
            int numJobs =  Integer.parseInt(line1.substring(line1.indexOf("= ")+2, line1.indexOf(";")));

            String line2 = br.readLine();
            int numAgents = Integer.parseInt(line2.substring(line2.indexOf("= ")+2, line2.indexOf(";")));

            String line3 = br.readLine();
            String[] proctimesstring = line3.substring(line3.indexOf("[")+1,line3.indexOf("]")).split(", ");
            int [] processingTimes = new int [numJobs];
            for(int x=0; x<numJobs; x++) {
                processingTimes[x] = Integer.parseInt(proctimesstring[x]);
            }

            int[][] preferences = new int[numAgents][numJobs];
            String line4 = br.readLine();
            String[] pref0 = line4.substring(line4.indexOf("|")+1,line4.indexOf("|", line4.indexOf("|")+1)).split(", ");
            for(int x=0; x<numJobs; x++) {
                preferences[0][x] = Integer.parseInt(pref0[x]);
            }

            for(int y = 1; y<numAgents; y++){
                String line = br.readLine();
                String[] pref = line.substring(line.indexOf(""),line.indexOf("|")).split(", ");
                for(int x=0; x<numJobs; x++) {
                    preferences[y][x] = Integer.parseInt(pref[x]);
                }
            }

            System.out.println(numJobs);
            System.out.println(numAgents);
            System.out.println(Arrays.toString(processingTimes));
            System.out.println(Arrays.toString(preferences[0]));
            for(int y = 1; y<numAgents; y++){
                System.out.println(Arrays.toString(preferences[y]));
            }

            br.close();
        }

    }
}