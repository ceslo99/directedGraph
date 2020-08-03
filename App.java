/*
Program 3
Robert Mopia cssc0856
Cesar Lopez cssc0830
CS 310 T/TH
 */
package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App extends DirectedGraph {

    private static ArrayList<String> readFile(File csvFile) throws IOException {
        ArrayList<String> contents = new ArrayList<>();
        String line;

        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        while ((line = reader.readLine()) != null) {
            contents.add(line);
        }
        reader.close();
        return contents;
    }

    private static void parseData(ArrayList<String> strings, DirectedGraph dg) {
        String line;
        String[] arr = new String[strings.size()];
        String[] splitLine;
        String v1, v2;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = strings.get(i);
        }
        for (int j = 0; j < arr.length; j++) {
            line = arr[j];
            splitLine = line.split(",");
            if (splitLine.length == 2) {
                v1 = splitLine[0];
                v2 = splitLine[1];
                if (!dg.contains(v1)) {
                    dg.add(v1);
                }
                if (!dg.contains(v2)) {
                    dg.add(v2);
                }
                dg.connect(v1, v2);
            } else {
                dg.add(splitLine[0]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File defaultFile, givenFile;
        if (args.length < 1) {
            defaultFile = new File("simpleLine.csv");
            if (defaultFile.exists()) {

                ArrayList<String> result = readFile(defaultFile);
                DirectedGraph directedGraph = new DirectedGraph();
                parseData(result, directedGraph);

                System.out.println(directedGraph.toString());

                Scanner userInput = new Scanner(System.in);

                System.out.println("Calculate shortest path?(Y/N)");
                String answer = userInput.nextLine();
                if (answer.equalsIgnoreCase("Y") ||
                        answer.equalsIgnoreCase("yes")) {
                    System.out.println("Enter starting point: ");
                    String start = userInput.nextLine();
                    System.out.println("Enter destination: ");
                    String end = userInput.nextLine();
                    userInput.close();
                    System.out.println("Shortest path: "
                            + directedGraph.shortestPath(start, end));
                } else {
                    return;
                }
            } else {
                System.out.println("Error: Unable to open filename. Verify the "
                        + "file exists, is accessible, and meets the syntax "
                        + "requirements.");
                return;
            }
        } else {
            givenFile = new File(args[0]);
            if (givenFile.exists()) {

                ArrayList<String> result = readFile(givenFile);
                DirectedGraph directedGraph = new DirectedGraph();
                parseData(result, directedGraph);

                System.out.println(directedGraph.toString());

                Scanner userInput = new Scanner(System.in);
                System.out.println("Calculate shortest path?(Y/N)");
                String answer = userInput.nextLine();
                if (answer.equalsIgnoreCase("Y") ||
                        answer.equalsIgnoreCase("yes")) {
                    System.out.println("Enter starting point: ");
                    String start = userInput.nextLine();
                    System.out.println("Enter destination: ");
                    String end = userInput.nextLine();
                    userInput.close();
                    System.out.println("Shortest path: "
                            + directedGraph.shortestPath(start, end));
                } else {
                    return;
                }

            } else {
                System.out.println("Error: Unable to open filename. Verify the "
                        + "file exists, is accessible, and meets the syntax "
                        + "requirements.");
                return;
            }
        }
    }
}
