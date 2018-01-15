# Independent-Projects
## Independent CS projects in Java and Python

### Bidder.py and Auction.py 

Python program that implements Professor Matthew Weinberg’s low regret algorithms and uses machine learning to calculate the price at which a bidder should bid at in different types of auction scenarios to secure his lowest regret. Other applications of these algorithms could include credit scoring and price determination. The program uses Seaborn to visualize the results. 

### Genome.java

Java program that parses a file of DNA into 100 sequence reads and then assembles a genome by identifying the strings that don't belong in the genome, removing them and calculating the overlap between the prefixes and suffixes of the remaining strings. 

### BarcodeErrorCorrection.java 

Java program that implements a motif-finding algorithm to identify and error-correct DNA barcode tags used in single cell sequencing. The algorithm uses k-words (sequence used for pattern alignment) with an associated set of neighborhoods in the set of all k-letter words to identify a ‘consensus’ barcode by building hash tables to identify and record the occurance of all possible substitution, deletion and insertion error mutation permutations.  Algorithm identifies the original un-mutated barcodes in a data set of sequenced barcodes and uses the Levenshtein edit distance to determine the nearest original barcode to each mutated barcode in order to error correct the data set. Through this algorithm, we found that we were able to generate 91% of the initial barcode library in our set of original barcodes. Furthermore, by error-correcting barcodes and keeping track of their counts, we were able to recover 533 out of 589 cells that were sequenced in a previously published Drop-Seq experiment by Macosko et al. 
