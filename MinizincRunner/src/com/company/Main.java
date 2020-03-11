package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.lang.ProcessBuilder;
import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException{
        File dir = new File("C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\TestSetGenerator\\TestSets\\examples");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Do something with child
                long startTime = System.nanoTime();
                runner("minizinc.exe", "C:\\Users\\Maarten\\Desktop\\IDM\\gametheory_project\\model.mzn", child);
                long endTime = System.nanoTime();
                long runtime = endTime - startTime;
                System.out.println("!!! Runtime = " + runtime + "nanoseconds!!!");
            }
        } else {
            System.out.println("directory is empty");
        }
    }

    public static void runner(String commd, String model, File dataset)  throws IOException{
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