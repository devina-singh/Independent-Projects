import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;

/* This program takes in a FATSA file of DNA strings and assembles them into a genome
 * by calculating the longest overlap between the siffixes and prefixes of all the
 * strings. It also identifies and removes error sequences from the genome 
 * by checking to see if there exist sequences that have a <50% overlap 
 * when compared with all of the other DNA strings.
 */
public class Genome {
    
    String[] error; // array of error DNA sequences
    String[] sequences; // array of all DNA sequences
    
    // Construtor takes in a FATSA file and creates an array of all viral DNA
    // sequences and a empty array to hold incorrect reads 
    public Genome(String filename) throws FileNotFoundException {
        
        sequences = parse(filename);
        error = new String[sequences.length];   
    }
    
    // parses FATSA file and creates Strings of 100 bases
    private String[] parse(String filename) throws FileNotFoundException {
        
        String in = new Scanner(new File(filename)).next(); // read in FATSA file
        int k = 0;
        String[] bases = new String[in.length()]; 
        
        // creates Strings of a 100 DNA bases and stores them in array 'bases'
        for (int i = 0; i < in.length(); i++) {
            int j = 0;
            String a = "";
            while ((j <= 100) && ((in.charAt(i) == 'A') || (in.charAt(i) == 'T')
                                      || (in.charAt(i) == 'C') || (in.charAt(i) == 'G'))) {
                j++;
                a += in.charAt(i);
            }
            
            bases[k] = a;
            k++;
        }
        
        return bases;   
    }
    
    // Constructor that takes in an array of Strings
    // created for unit testing purposes
    public Genome(String[] a) {
        sequences = a; 
        error = new String[a.length];        
    }
    
    // Calculates the longest overlap between two Strings of DNA
    public int longestOverlap(String a, String b) {
        
        String read1 = a;
        String read2 = b;
        char letter = read2.charAt(0); // first letter of second string
        int index = read1.indexOf(letter); // first incidence of letter on first string
        int maxLength = 0; 
        int i = index;
        int j = 0;
        
        
        // finds maximum overlap
        while (i < read1.length()) {
            if (i == -1) // corner case checks to see if there is no overlap anywhere 
                break;
            int currLength = 0; 
            // checks to see if there is any overlap between the strings
            while (i < read1.length() && j < read2.length() && read1.charAt(i) == read2.charAt(j)) { 
                i++;
                j++;
                currLength++;
            }
            // checks to see if overlap is optimized and if suffix and prefix are alligned
            if ((i == read1.length()) && (currLength > maxLength)) 
                maxLength = currLength;
            j = 0;
            
            // sets i to the next incidence on read1 of read2's first letter 
            i = read1.indexOf(letter, index + 1);
            index = i;
        }
        
        return maxLength;
        
    }
    // finds DNA strands that do not fit into the Genome
    public String[] findWrongDNA() {
        
        int i = 0;
        int overlap = 0;
        // checks to see each string has a true overlap with some other string
        for (int j = 0; j < sequences.length; j++)  {
            for (int k = j + 1; k < sequences.length; k++) {
                if (longestOverlap(sequences[j], sequences[k]) >= (sequences[j].length()/2)) {
                    overlap++; 
                }
            }
            
            if (overlap == 0) {  
                error[i] = sequences[j]; 
                i++; }
            overlap = 0;   
        }
        
        return error; 
    }
    
    // Assembles all the DNA sequences without the error sequences
    public String assemble() {
        
        String[] correct = new String[sequences.length]; // array of only viral DNA
        String assembled = "";
        int errorDNA = 0;
        int k = 0;
        
        // Removing error DNA sequences from the other DNA sequences
        for (int i = 0; i < sequences.length; i++) {
            for (int j = 0; j < sequences.length; j++) {
                if (sequences[i] == error[j]) errorDNA++;
            }
            if (errorDNA == 0) {
                correct[k] = sequences[i];
                k++; 
            }
            errorDNA = 0;      
        }
        
        // Assembling the Genome
        
        for (int i = 0; i < correct.length; i++)
            System.out.println(correct[i]);
        
        String[] a = optimizedOverlap(correct);
        
        assembled = a[0];
        
        return assembled;
        
    }
    // calculates the order in which the strings of DNA are assembled
    private String[] optimizedOverlap(String[] a) {
        
        int index = -1;
        int size = 0;
        
        while (size != a.length - 1) {
            int maxOverlap = 0;
            for (int j = 1; j < a.length; j++) {
                int currOverlap = longestOverlap(a[0], a[j]);
                if (currOverlap > maxOverlap) {
                    maxOverlap = currOverlap;
                    index = j;
                }
            }
            
            String b = "";
            for (int i = maxOverlap; i < a[index].length(); i++) {
                b += a[index].charAt(i); }
            a[0] += b;
            a[index] = " ";
            size++;
        }
        
        return a;
    }
    
    // unit testing
    public static void main(String[] args) throws FileNotFoundException {
        
        String a = "AAGGTGAG";
        String b = "TGAGTGGA";
        String filename1 = "lambda_scramble.fa";
        String[] c = new String[4];
        c[0] = "AAGGTGAG";
        c[1] = "GGTGATGA";
        c[2] = "TGAGTGGA";
        c[3] = "TGGAGGTG";
        
        Genome genome = new Genome(c);
        System.out.println(genome.longestOverlap(a, b));
        System.out.println(genome.assemble());
        
        Genome genome2 = new Genome(filename1);
        
    }
}

      
    

 