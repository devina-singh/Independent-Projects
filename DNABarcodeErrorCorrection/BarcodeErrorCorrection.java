/****************************************************************************
  * Author: Devina Singh
  * 
  * Program Name: BarcodeErrorCorrection.java
  * 
  * Description: This program places every barcode in the data set within 
  * a HashMap along with a count of its occurence. It also generates
  * every single subsitution, deletion and insertion mutation permutation
  * for each barcode and places them in the HashMap, updating their counts. 
  ****************************************************************************/
import java.util.*;
import java.lang.String;
import java.io.FileNotFoundException;
import java.util.HashMap; 
import java.util.Queue; 
import java.util.LinkedList; 

public class BarcodeErrorCorrection {
    
    // hashmap containing barcodes and number of times they occur
    private HashMap<String, Integer> barcodes;
    private int bLen; // barcode Length
    private Queue<String> oneSub;
    private Queue<String> oneIns;
    private Queue<String> oneDel;
 
    /* This method places all the barcodes in the dataset as well 
     as all their possible substitution, deletion and insertion 
     mutation permutations in a HashMap along with their counts */
    public BarcodeErrorCorrection(String[] input, int barcodeLen) {
        bLen = barcodeLen;
        barcodes = new HashMap<String, Integer>(); // <barcodes, counts>
        
        int len = input.length;

        // Go through the 1 million barcodes
        for (int i = 0; i < len; i++) { 
            oneSub = new LinkedList<String>();
            oneIns = new LinkedList<String>();
            oneDel = new LinkedList<String>();
            // store barcode in HashMap
            StringBuilder barcode = new StringBuilder(input[i].substring(0,barcodeLen));
            // update it's count
            if (!barcodes.containsKey(barcode.toString()))
                barcodes.put(barcode.toString(), 1); 
            else {
                barcodes.put(barcode.toString(), barcodes.get(barcode.toString())+1); }
            // use barcode + extra character to generate all deletion edit permutations 
            // and place them in HashMap
           
            StringBuilder extdBar = new StringBuilder(input[i].substring(0,barcodeLen + 1));
            possibleDeletion(extdBar);
            StringBuilder extdBar2 = new StringBuilder(input[1]);
  
            // generate all substitution and insertion edit permutations and place them in HashMap
            possibleStrings(input[i].substring(0, barcodeLen)); 
           
            /* uncomment to generate two mutation permutations
            possibleDeletion2Mutations(extdBar2);  
            possibleSubstitution2Mutations();
            possibleInsertion2Mutations();  */         
            

            }
        for (Map.Entry<String, Integer> entry : barcodes.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
             System.out.println(key + "  " + value);
        }
    }
    
    /* generate all substitution and insertion edit permutations and place them in HashMap
     example:
     inital barcode: ACG
     substitution mutations generated: GCG, CCG, TCG, AGG, ATG, AAG, ACC, ACT, ACA 
     insertion mutations generated: GAC, CAC, TAC, AAC, ATC, ACC, AGC, ACT, ACA */

    private void possibleStrings(String key) {
        
        String cmp = key;
        
        // go through each character in barcode
        for (int i = 0; i < key.length(); i++) {
            StringBuilder newString = new StringBuilder(key);
            StringBuilder newString2 = new StringBuilder(key);
            StringBuilder newString3 = new StringBuilder(key);
            // substitute character with other 3 bases. 
            if (key.charAt(i) == 'A') {
                
                newString.setCharAt(i, 'C');
                newString2.setCharAt(i, 'T');
                newString3.setCharAt(i, 'G');
            }
            if (key.charAt(i) == 'C') {
                
                newString.setCharAt(i, 'A');
                newString2.setCharAt(i, 'T');
                newString3.setCharAt(i, 'G');
            }
            if (key.charAt(i) == 'G') {
                
                newString.setCharAt(i, 'C');
                newString2.setCharAt(i, 'T');
                newString3.setCharAt(i, 'A');
                
            }
            if (key.charAt(i) == 'T') {
                
                newString.setCharAt(i, 'C');
                newString2.setCharAt(i, 'A');
                newString3.setCharAt(i, 'G');
                
            }
            // place all substitution mutations in HashMap, updating their counts
            if (!barcodes.containsKey(newString.toString()))
                barcodes.put(newString.toString(), 1); 
            else 
                barcodes.put(newString.toString(), barcodes.get(newString.toString())+1);
            if (!barcodes.containsKey(newString2.toString()))
                barcodes.put(newString2.toString(), 1); 
            else 
                barcodes.put(newString2.toString(), barcodes.get(newString2.toString())+1);
            if (!barcodes.containsKey(newString3.toString()))
                barcodes.put(newString3.toString(), 1); 
            else 
                barcodes.put(newString3.toString(), barcodes.get(newString3.toString())+1);

            oneSub.add(newString.toString());
            oneSub.add(newString2.toString());
            oneSub.add(newString3.toString());
            // generate insertion mutations by inserting each base at every position
            StringBuilder insert = new StringBuilder(key).insert(key.length()-i-1, "A");
            StringBuilder insert1 = new StringBuilder(key).insert(key.length()-i-1, "T");
            StringBuilder insert2 = new StringBuilder(key).insert(key.length()-i-1, "C");
            StringBuilder insert3 = new StringBuilder(key).insert(key.length()-i-1, "G");
            // delete last character
            insert.deleteCharAt(bLen);
            insert1.deleteCharAt(bLen);
            insert2.deleteCharAt(bLen);
            insert3.deleteCharAt(bLen);
            // place insertion mutations in HashMap, updating counts
            if (!insert.toString().equals(cmp)) {
                if (!barcodes.containsKey(insert.toString()))
                    barcodes.put(insert.toString(), 1); 
                else 
                    barcodes.put(insert.toString(), barcodes.get(insert.toString())+1); }
            
            if (!insert1.toString().equals(cmp)) {
                
                if (!barcodes.containsKey(insert1.toString()))
                    barcodes.put(insert1.toString(), 1); 
                else 
                    barcodes.put(insert1.toString(), barcodes.get(insert1.toString())+1); }
            
            if (!insert2.toString().equals(cmp)) {
                if (!barcodes.containsKey(insert2.toString()))
                    barcodes.put(insert2.toString(), 1); 
                else 
                    barcodes.put(insert2.toString(), barcodes.get(insert2.toString())+1); }
            
            if (!insert3.toString().equals(cmp)) {
                if (!barcodes.containsKey(insert3.toString()))
                    barcodes.put(insert3.toString(), 1); 
                else 
                    barcodes.put(insert3.toString(), barcodes.get(insert3.toString())+1); }   

            oneIns.add(insert.toString());
            oneIns.add(insert1.toString());
            oneIns.add(insert2.toString());
            oneIns.add(insert3.toString());
        }     
    }


     private void possibleInsertion2Mutations() {
        for (String sub: oneIns) {
            String key = sub;
            String cmp = key;
            for (int i = 0; i < key.length(); i++) {
                StringBuilder insert = new StringBuilder(key).insert(key.length()-i-1, "A");
                StringBuilder insert1 = new StringBuilder(key).insert(key.length()-i-1, "T");
                StringBuilder insert2 = new StringBuilder(key).insert(key.length()-i-1, "C");
                StringBuilder insert3 = new StringBuilder(key).insert(key.length()-i-1, "G");
                // delete last character
                insert.deleteCharAt(bLen);
                insert1.deleteCharAt(bLen);
                insert2.deleteCharAt(bLen);
                insert3.deleteCharAt(bLen);
                // place insertion mutations in HashMap, updating counts
                if (!insert.toString().equals(cmp)) {
                    if (!barcodes.containsKey(insert.toString()))
                        barcodes.put(insert.toString(), 1); 
                    else 
                        barcodes.put(insert.toString(), barcodes.get(insert.toString())+1); }
                
                if (!insert1.toString().equals(cmp)) {
                    
                    if (!barcodes.containsKey(insert1.toString()))
                        barcodes.put(insert1.toString(), 1); 
                    else 
                        barcodes.put(insert1.toString(), barcodes.get(insert1.toString())+1); }
                
                if (!insert2.toString().equals(cmp)) {
                    if (!barcodes.containsKey(insert2.toString()))
                        barcodes.put(insert2.toString(), 1); 
                    else 
                        barcodes.put(insert2.toString(), barcodes.get(insert2.toString())+1); }
                
                if (!insert3.toString().equals(cmp)) {
                    if (!barcodes.containsKey(insert3.toString()))
                        barcodes.put(insert3.toString(), 1); 
                    else 
                        barcodes.put(insert3.toString(), barcodes.get(insert3.toString())+1); }
            }
        }
    } 
    

    private void possibleSubstitution2Mutations() {
        for (String sub: oneSub) {
            String key = sub;
            for (int i = 0; i < key.length(); i++) {
                StringBuilder newString = new StringBuilder(key);
                StringBuilder newString2 = new StringBuilder(key);
                StringBuilder newString3 = new StringBuilder(key);
                // substitute character with other 3 bases. 
                if (key.charAt(i) == 'A') {
                    
                    newString.setCharAt(i, 'C');
                    newString2.setCharAt(i, 'T');
                    newString3.setCharAt(i, 'G');
                }
                if (key.charAt(i) == 'C') {
                    
                    newString.setCharAt(i, 'A');
                    newString2.setCharAt(i, 'T');
                    newString3.setCharAt(i, 'G');
                }
                if (key.charAt(i) == 'G') {
                    
                    newString.setCharAt(i, 'C');
                    newString2.setCharAt(i, 'T');
                    newString3.setCharAt(i, 'A');
                    
                }
                if (key.charAt(i) == 'T') {
                    
                    newString.setCharAt(i, 'C');
                    newString2.setCharAt(i, 'A');
                    newString3.setCharAt(i, 'G');
                    
                }
                // place all substitution mutations in HashMap, updating their counts
                if (!barcodes.containsKey(newString.toString()))
                    barcodes.put(newString.toString(), 1); 
                else 
                    barcodes.put(newString.toString(), barcodes.get(newString.toString())+1);
                if (!barcodes.containsKey(newString2.toString()))
                    barcodes.put(newString2.toString(), 1); 
                else 
                    barcodes.put(newString2.toString(), barcodes.get(newString2.toString())+1);
                if (!barcodes.containsKey(newString3.toString()))
                    barcodes.put(newString3.toString(), 1); 
                else 
                    barcodes.put(newString3.toString(), barcodes.get(newString3.toString())+1);

            }
        }
    } 



    /* generate all deletion edit permutations and place them in HashMap
     example:
     inital barcode + extra character: ACGT
     deletion mutations generated: CGT, AGT, ACG, ACT*/
    private void possibleDeletion(StringBuilder key) {
        
        StringBuilder cmp = new StringBuilder(key);
        cmp.deleteCharAt(key.length() - 1); // ACG
        for (int i = 0; i < key.length() - 1; i++) { //ACG
            StringBuilder sb = new StringBuilder(key);
            // delet character at every position
            sb.deleteCharAt(i); 
            // place in HashMap and update counts
            if (!sb.toString().equals(cmp)) {
                if (!barcodes.containsKey(sb.toString()))
                    barcodes.put(sb.toString(), 1); 
                else {
                    barcodes.put(sb.toString(), barcodes.get(sb.toString())+1); }
                
            }       

        }
    }
    

      /* example:
     inital barcode + two characters: ACGT 
     2 deletion mutations generated: GT GT CT CT CG CG AT AT AG AG AC AC*/
    private void possibleDeletion2Mutations(StringBuilder key) {
        
        StringBuilder cmp1 = new StringBuilder(key);
        cmp1.deleteCharAt(key.length() - 1); // ACG
        for (int i = 0; i < key.length() - 1; i++) { //ACG
            StringBuilder sb = new StringBuilder(key);
            // delet character at every position
            sb.deleteCharAt(i); 
            // place in HashMap and update counts

            oneDel.add(sb.toString());

        }

        String a = key.toString();
        StringBuilder cmp = new StringBuilder();
        cmp.append(a.charAt(0));
        cmp.append(a.charAt(1));
        cmp.append(a.charAt(2));
        cmp.append(a.charAt(3));
        cmp.append(a.charAt(4));
        cmp.append(a.charAt(5));
        cmp.append(a.charAt(6));
        //cmp.deleteCharAt(key.length() - 1);
        //cmp.deleteCharAt(key.length() - 2);


        for (String firstdel: oneDel) {
            for (int i = 0; i < firstdel.length() - 1; i++) {
                StringBuilder sb = new StringBuilder(firstdel);
                // delet character at every position
                sb.deleteCharAt(i);
                // place in HashMap and update counts
                if (!sb.toString().equals(cmp)) {
                    if (!barcodes.containsKey(sb.toString()))
                        barcodes.put(sb.toString(), 1); 
                    else {
                        barcodes.put(sb.toString(), barcodes.get(sb.toString())+1); }
                }
            }
        }
        
    } 
    
    public static void main(String[] args) throws FileNotFoundException {
        ReadFile rf = new ReadFile();
        String[] input2 = rf.read();
        BarcodeErrorCorrection errors2 = new BarcodeErrorCorrection(input2, 7);
        
    }
}






