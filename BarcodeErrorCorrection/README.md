# BarcodeErrorCorrection
During a single cell sequencing experiment, hundreds of thousands of cells are often sequenced together. These reads are tagged with a short DNA barcode in order to group and identify reads that originate from the same cell. However, many of these barcodes undergo substitution, deletion and insertion errors during the amplification process and this results in a significant amount of discarded reads. This repositary outlines an algorithm to identify and error-correct mutated barcodes within large datasets. The algorithm builds upon existing motif finding approaches and builds a table to explore all possible substitution, deletion and insertion permutations of a barcode in order to determine the original un-mutated barcodes. Each barcode in the data set is then error corrected by using the Levenshtein edit distance to determine the nearest original barcode to each mutated barcode. Through this algorithm, we found that we were able to generate 91% of the initial barcode library in our set of original barcodes. Furthermore, by error-correcting barcodes and keeping track of their counts, we were able to recover 533 out of 589 cells that were sequenced in a previously published Drop-Seq experiment by Macosko et al.

### Program Name: ReadFile.java
##### Description: 
This program reads through a dataset containing 400 million DNA reads obtained from single cell sequencing experiments and parses and stores the first 1 million barcodes (barcode: first 7 characters of a read ) of those reads within a string array.

### Program Name: BarcodeErrorCorrection.java
##### Description: 
This program places every barcode in the data set within a HashMap along with a count of its occurence. It also generates every single subsitution, deletion and insertion mutation permutation for each barcode and places them in the HashMap, updating their counts.

### Program Name: GetOriginalBarcodes.java
##### Description: 
This program goes through the first 589 barcodes with the highest counts in the HashMap of all barcode mutation permutations
and places them in a HashSet. 


### Program Name: StringSimilarity.java (adapted from https://ideone.com/oOVWYj)
##### Description: 
This program calculates the levenshtein edit distance between twwo strings and returns a number between 0 and 1 to represent  how similar they are. 

### Program Name: ErrorCorrect.java 
##### Description: 
This program error corrects all the barcodes in a dataset by using the Levenshtein Edit Distance to locate the closest original un-mutated barcode to each barcode in the dataset. It then groups these error-corrected barcodes together and considers their counts in order to determine he number of cells that can be recovered. 


             
             
