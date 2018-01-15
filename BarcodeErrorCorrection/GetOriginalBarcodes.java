/****************************************************************************
  * Author: Devina Singh
  * 
  * Program Name: GetOriginalBarcodes.java
  * 
  * Description: This program goes through the first 589 barcodes
  * with the highest counts in the HashMap of all barcode mutation permutations
  * and places them in a HashSet. 
  ****************************************************************************/
import java.util.*;
import java.lang.*;
import java.lang.Object;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class GetOriginalBarcodes {
    private static final String FILENAME = "580originalbarcodes.txt"; 
    
    public GetOriginalBarcodes() {
    }
    // This method goes through sorted hashmap stores the first 589 original 
    // barcodes with the highest counts in a HashSet
    public HashSet<String> originalBarc() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String line;
            HashSet<String> barcodes = new HashSet<String>();
            int size = 0;
            
            // place all the barcodes in an HashSet
            while (( line = br.readLine() ) != null) {
                barcodes.add(line); 
            }
            
            // Always close files.
            br.close();
            
            return barcodes;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '");                  
        }
        return null;
    }
}