/****************************************************************************
  * Author: Devina Singh
  * 
  * Program Name: ErrorCorrect.java
  * 
  * Description: This program error corrects all the barcodes in a dataset 
  * by using the Levenshtein Edit Distance to locate the closest original
  * un-mutated barcode to each barcode in the dataset. It then groups these
  * error-corrected barcodes together and considers their counts in order to 
  * determine he number of cells that can be recovered. 
  ****************************************************************************/
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.lang.*;
import java.util.Iterator;
import java.lang.Object;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
    
    public class ErrorCorrect  {
    
    private static final String FILENAME = "1millbarcodes.txt";
    // his method error corrects all the barcodes in a dataset and groups
    // these barcodes together to consider how many cells can be recovered. 
    public ErrorCorrect() throws FileNotFoundException  {
        GetOriginalBarcodes get = new GetOriginalBarcodes();
        HashSet<String> original = get.originalBarc(); // gets the original un-mutated 589 barcodes
        
         double similarityVal = 0.0;
         String closest = "";
         
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));
            String line;
            List<String> list = new ArrayList<String>();
            int index = 0;
            int longerLength = 0;
            HashMap<String, Integer> cluster = new  HashMap<String, Integer>();
            // Go through each barcode in dataset
            while ((line = br.readLine() ) != null) {
                StringBuilder barc = new StringBuilder(line.substring(0,6));
                // No change if barcode contained in set of original barcodes
                if (original.contains(barc.toString())) {
                    list.add(barc.toString());
                }
                // if barcode is mutated...
                else {
                    // find closest original barcode using Levenshtein Edit Distance
                    StringSimilarity sim = new StringSimilarity();
                    for (String temp : original) {
                        // find levenshtein distance between mutated barcode and each of the original barcodes
                        double currentSim = sim.similarity(barc.toString(), temp);
                       // find closest original barcode
                        if (currentSim >= similarityVal) {
                            similarityVal = currentSim; 
                            closest = temp; } 
                   }
                        list.add(closest); // replace mutated barcode with closest original barcode
                }
            }

    // place error corrected barcodes in HashMap and count their occurances 
    // This allows us to determine the number of cells 
    String[] stringArr = list.toArray(new String[0]);
    for (int i = 0; i < stringArr.length; i++) {
       if (!cluster.containsKey(stringArr[i]))
                cluster.put(stringArr[i], 1); 
            else {
                cluster.put(stringArr[i], cluster.get(stringArr[i])+1); }
    }
    // print HashMap of error corrected barcodes and their counts
    for (Map.Entry<String, Integer> entry : cluster.entrySet()) {
    String key = entry.getKey().toString();
    Integer value = entry.getValue();
    System.out.println(key + "    " + value);
    
}
    // Always close files.
            br.close();

}
            catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '");                  
        }
 
    }
     public static void main(String[] args) throws FileNotFoundException {
        ErrorCorrect ec = new ErrorCorrect();
        
    }
}

        
        
        