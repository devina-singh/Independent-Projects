/****************************************************************************
 * Author: Devina Singh
 * 
 * Program Name: ReadFile.java
 * 
 * Description: This program reads through a dataset containing 400 million
 * DNA reads obtained from single cell sequencing experiments
 * and parses and stores the first 1 million barcodes 
 * (first 8 characters) of those reads within a string array.  
 ****************************************************************************/

import java.util.*;
import java.lang.Object;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ReadFile {
    // dataset file
    private static final String FILENAME = "data4mill.txt";
    
    
    public ReadFile() throws FileNotFoundException {
    }
    
    // This method goes through the dataset and stores the 
    // first million barcodes that only consist of bases 
    // A, T, C and G
    public String[] read() throws FileNotFoundException {
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String line;
            List<String> list = new ArrayList<String>();
            int index = 0;
            int size = 0;
            int sizeReads = 0;
            // read through DNA reads in data set
            while ((line = br.readLine() ) != null) {
                size++;
                // Only store first million reads that don't have the character 'N' in them 
                if ((index == 1) && (line.indexOf('N') == -1) && (sizeReads <= 1000000)) {
                    sizeReads++;
                    // barcode consists of first 7 characters of read
                    // extra character taken for cases with deletion errors
                    StringBuilder str = new StringBuilder(line.substring(0,8));
                    list.add(str.toString()); 
                }
                
                if (index == 3) index = 0;
                else index++;  
            }
            
            String[] stringArr = list.toArray(new String[0]);
            // Always close files.
            br.close();
            
            return stringArr;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '");                  
        }
        return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        
        ReadFile rf = new ReadFile();
        String[] result = rf.read();
    }
}