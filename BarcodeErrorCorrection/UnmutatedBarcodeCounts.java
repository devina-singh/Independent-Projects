 import java.util.*;
import java.lang.String;
import java.io.FileNotFoundException;
import java.util.HashMap; 
import java.util.Queue; 
import java.util.LinkedList; 

public class UnmutatedBarcodeCounts {
    
    // hashmap containing barcodes and number of times they occur
    private HashMap<String, Integer> barcodes;
    
    /* This method places all the barcodes in the dataset as well 
     as all their possible substitution, deletion and insertion 
     mutation permutations in a HashMap along with their counts */
    public UnmutatedBarcodeCounts(String[] input, int barcodeLen) {
    	barcodes = new HashMap<String, Integer>();
    	String barcode = "";
    	for (int i = 0; i < input.length; i++) {
    		barcode = input[i].substring(0,7);
    		if (!barcodes.containsKey(barcode))
                barcodes.put(barcode, 1); 
            else 
                barcodes.put(barcode, barcodes.get(barcode)+1);
    	}

    	for (Map.Entry<String, Integer> entry : barcodes.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
             System.out.println(key + "  " + value);
         }
     }





  public static void main(String[] args) throws FileNotFoundException {
        ReadFile rf = new ReadFile();
        String[] input2 = rf.read();
        UnmutatedBarcodeCounts errors2 = new UnmutatedBarcodeCounts(input2, 7);
        
    }
 }