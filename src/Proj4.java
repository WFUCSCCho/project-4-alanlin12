import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();
        // Making dataList to store terminal command
        ArrayList<World> dataList = new ArrayList<>();
        int index = 0;
        while(inputFileNameScanner.hasNextLine() && index < numLines){
            String line = inputFileNameScanner.nextLine();
            String[] entry = line.split(",");

            int year = Integer.parseInt(entry[0]);
            long population = Long.parseLong(entry[1]);

            dataList.add(new World(year, population));
            index++;
        }

        inputFileNameScanner.close();
        inputFileNameStream.close();

        ArrayList<World> sortedData = new ArrayList<>(dataList);
        Collections.sort(sortedData);

        ArrayList<World> shuffledData = new ArrayList<>(dataList);
        Collections.shuffle(shuffledData);

        ArrayList<World> reversedData = new ArrayList<>(dataList);
        Collections.sort(reversedData, Collections.reverseOrder());

        File analyzeFile = new File("analysis.txt");
        boolean newFile = !analyzeFile.exists();
        FileWriter analysisWriter = new FileWriter(analyzeFile, true);

        if(newFile){
            analysisWriter.write("#num_lines,sorting_type,operation,time\n");
        }

        SeparateChainingHashTable<World> temp;
        long start, end;

        // Sorted
        temp = new SeparateChainingHashTable<>();

        start = System.nanoTime();
        for(World val : sortedData) temp.insert(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, sorted, insert, %f\n", numLines, (double) (end - start)/1000000.));

        start = System.nanoTime();
        for(World val : sortedData) temp.contains(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, sorted, search, %f\n", numLines, (double) (end - start)/1000000.));
    
        start = System.nanoTime();
        for(World val : sortedData) temp.remove(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, sorted, remove, %f\n", numLines, (double) (end - start)/1000000.));


        // Shuffled
        temp = new SeparateChainingHashTable<>();

        start = System.nanoTime();
        for(World val : shuffledData) temp.insert(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, shuffled, insert, %f\n", numLines, (double) (end - start)/1000000.));

        start = System.nanoTime();
        for(World val : shuffledData) temp.contains(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, shuffled, search, %f\n", numLines, (double) (end - start)/1000000.));
    
        start = System.nanoTime();
        for(World val : shuffledData) temp.remove(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, shuffled, remove, %f\n", numLines, (double) (end - start)/1000000.));

        // Reversed 
        temp = new SeparateChainingHashTable<>();

        start = System.nanoTime();
        for(World val : reversedData) temp.insert(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, reversed, insert, %f\n", numLines, (double) (end - start)/1000000.));

        start = System.nanoTime();
        for(World val : reversedData) temp.contains(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, reversed, search, %f\n", numLines, (double) (end - start)/1000000.));
    
        start = System.nanoTime();
        for(World val : reversedData) temp.remove(val);
        end = System.nanoTime();
        analysisWriter.write(String.format("%d, reversed, remove, %f\n", numLines, (double) (end - start)/1000000.));

        analysisWriter.close();
    }
}
