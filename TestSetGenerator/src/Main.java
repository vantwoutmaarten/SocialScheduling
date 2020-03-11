import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        int numJobs;
        int numAgents;
        //Realistic test sets
        for (int i = 1; i <= 35; i++) {
            if (i <= 5) {
                numJobs = 10;
                numAgents = 10;
            }else if(i<= 10){
                numJobs = 20;
                numAgents = 10;
            }else if(i <= 15){
                numJobs = 10;
                numAgents = 20;
            }else if(i <= 20){
                numJobs = 20;
                numAgents = 20;
            }else if(i <= 25){
                numJobs = 10;
                numAgents = 30;
            }else if(i <= 30){
                numJobs = 30;
                numAgents = 10;
            }else{
                numJobs = 30;
                numAgents = 30;
            }
            try (FileWriter fileWriter = new FileWriter("TestSets/RealisticSets/"+ i+ "_realistic" + numJobs + "J" + numAgents + "A_.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min
                    procTime = (int)((Math.random() * ((60 - 35))) + 35);
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
                    for(int j = 0; j < numJobs; j++){
                        jobNum = (int)(Math.random() * (numJobs)+1);
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
        for (int i = 1; i <= 35; i++) {
            if (i <= 5) {
                numJobs = 10;
                numAgents = 10;
            }else if(i<= 10){
                numJobs = 20;
                numAgents = 10;
            }else if(i <= 15){
                numJobs = 10;
                numAgents = 20;
            }else if(i <= 20){
                numJobs = 20;
                numAgents = 20;
            }else if(i <= 25){
                numJobs = 10;
                numAgents = 30;
            }else if(i <= 30){
                numJobs = 30;
                numAgents = 10;
            }else{
                numJobs = 30;
                numAgents = 30;
            }
            try (FileWriter fileWriter = new FileWriter("TestSets/HighSimilarityPref_Sets/"+ i+ "_highsim" + numJobs + "J" + numAgents + "A_.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min
                    procTime = (int)((Math.random() * ((55 - 45))) + 45);
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
                    for(int j = 0; j < numJobs; j++){
                        jobNum = (int)(Math.random() * (numJobs)+1);
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
        for (int i = 1; i <= 35; i++) {
            if (i <= 5) {
                numJobs = 10;
                numAgents = 10;
            }else if(i<= 10){
                numJobs = 20;
                numAgents = 10;
            }else if(i <= 15){
                numJobs = 10;
                numAgents = 20;
            }else if(i <= 20){
                numJobs = 20;
                numAgents = 20;
            }else if(i <= 25){
                numJobs = 10;
                numAgents = 30;
            }else if(i <= 30){
                numJobs = 30;
                numAgents = 10;
            }else{
                numJobs = 30;
                numAgents = 30;
            }
            try (FileWriter fileWriter = new FileWriter("TestSets/LowSimilarityPref_Sets/"+ i+ "_lowsim" + numJobs + "J" + numAgents + "A_.dzn" )) {

                fileWriter.write("numJobs = "+ numJobs + ";\nnumAgents = "+ numAgents +";\nprocessingTimes = [");
                //processingtimes
                int procTime;
                for(int j = 0; j < numJobs; j++){
                    //(Math.random() * ((max - min) + 1)) + min
                    procTime = (int)((Math.random() * ((75 - 15))) + 15);
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
                    for(int j = 0; j < numJobs; j++){
                        jobNum = (int)(Math.random() * (numJobs)+1);
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
