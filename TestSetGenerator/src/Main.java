import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int numJobs;
        int numAgents;
        ArrayList isprefered;
        //Realistic test sets
        for (int i = 1; i <= 40; i++) {
            if (i <= 10) {
                numJobs = 5;
                numAgents = 5;
            }else if(i<= 20){
                numJobs = 5;
                numAgents = 10;
            }else if(i <= 30){
                numJobs = 10;
                numAgents = 5;
            }else {
                numJobs = 10;
                numAgents = 10;
            }
            String doubledigit_i = (i < 10 ? "0" : "") + i;
            try (FileWriter fileWriter = new FileWriter("TestSets/RealisticSets/"+ doubledigit_i+ "_realistic" + numJobs + "J" + numAgents + "A.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min

                        fileWriter.write(procTime + ", ");
                    }else{procTime = (int)((Math.random() * ((60 - 35))) + 35);
                        if(j != numJobs-1) {
                        fileWriter.write(procTime + "");
                    }
                }
                fileWriter.write("];\npreferences = [");
                int jobNum;
                fileWriter.write("|");
                for (int z = 0; z < numAgents; z++){
                    isprefered = new ArrayList();
                    for(int j = 0; j < numJobs; j++) {
                        jobNum = (int) (Math.random() * (numJobs) + 1);
                        while (isprefered.contains(jobNum)) {
                            jobNum = (int) (Math.random() * (numJobs) + 1);
                        }
                        isprefered.add(jobNum);
                        if(j != numJobs-1) {
                            fileWriter.write(jobNum + ", ");
                        }else{
                            fileWriter.write(jobNum + "");
                        }
                    }
                    if(z != numAgents-1) {
                        fileWriter.write("|\n");
                    }else{
                        fileWriter.write("|];");
                    }
                }
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       //High Similarity Sets
        for (int i = 1; i <= 40; i++) {
            if (i <= 10) {
                numJobs = 5;
                numAgents = 5;
            }else if(i<= 20){
                numJobs = 5;
                numAgents = 10;
            }else if(i <= 30){
                numJobs = 10;
                numAgents = 5;
            }else {
                numJobs = 10;
                numAgents = 10;
            }
            String doubledigit_i = (i < 10 ? "0" : "") + i;
            try (FileWriter fileWriter = new FileWriter("TestSets/HighSimilarityPref_Sets/"+ doubledigit_i + "_highsim" + numJobs + "J" + numAgents + "A.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min
                    procTime = (int)((Math.random() * ((53 - 47))) + 47);
                    if(j != numJobs-1) {
                        fileWriter.write(procTime + ", ");
                    }else{
                        fileWriter.write(procTime + "");
                    }
                }
                fileWriter.write("];\npreferences = [");
                int jobNum;
                fileWriter.write("|");
                for (int z = 0; z < numAgents; z++){
                    isprefered = new ArrayList();
                    for(int j = 0; j < numJobs; j++) {
                        jobNum = (int) (Math.random() * (numJobs) + 1);
                        while (isprefered.contains(jobNum)) {
                            jobNum = (int) (Math.random() * (numJobs) + 1);
                        }
                        isprefered.add(jobNum);

                        if(j != numJobs-1) {
                            fileWriter.write(jobNum + ", ");
                        }else{
                            fileWriter.write(jobNum + "");
                        }
                    }
                    if(z != numAgents-1) {
                        fileWriter.write("|\n");
                    }else{
                        fileWriter.write("|];");
                    }
                }
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Low Similarity Sets
        for (int i = 1; i <= 40; i++) {
            if (i <= 10) {
                numJobs = 5;
                numAgents = 5;
            }else if(i<= 20){
                numJobs = 5;
                numAgents = 10;
            }else if(i <= 30){
                numJobs = 10;
                numAgents = 5;
            }else {
                numJobs = 10;
                numAgents = 10;
            }
            String doubledigit_i = (i < 10 ? "0" : "") + i;
            try (FileWriter fileWriter = new FileWriter("TestSets/LowSimilarityPref_Sets/"+ doubledigit_i + "_lowsim" + numJobs + "J" + numAgents + "A.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min
                    procTime = (int)((Math.random() * ((80 - 10))) + 10);
                    if(j != numJobs-1) {
                        fileWriter.write(procTime + ", ");
                    }else{
                        fileWriter.write(procTime + "");
                    }
                }
                fileWriter.write("];\npreferences = [");
                int jobNum;
                fileWriter.write("|");
                for (int z = 0; z < numAgents; z++){
                    isprefered = new ArrayList();
                    for(int j = 0; j < numJobs; j++) {
                        jobNum = (int) (Math.random() * (numJobs) + 1);
                        while (isprefered.contains(jobNum)) {
                            jobNum = (int) (Math.random() * (numJobs) + 1);
                        }
                        isprefered.add(jobNum);
                        if(j != numJobs-1) {
                            fileWriter.write(jobNum + ", ");
                        }else{
                            fileWriter.write(jobNum + "");
                        }
                    }
                    if(z != numAgents-1) {
                        fileWriter.write("|\n");
                    }else{
                        fileWriter.write("|];");
                    }
                }
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    }
