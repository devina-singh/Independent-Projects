# Independent-Projects

### ScaffCC Quantum Programming Compiler
ScaffCC is a compiler for the Scaffold programing language - with the purpose of writing and analyzing code for quantum computing applications.

ScaffCC enables researchers to compile quantum applications written in Scaffold to a low-level quantum assembly format (QASM), apply error correction, and generate resource estimation metrics. It is written to be scalable up to problem sizes in which quantum algorithms outperform classical ones, and as such provide valuable insight into the overheads involved and possible optimizations for a realistic implementation on a future device technology.

### Classifying Amazon Fine Food Review Helpfulness

Consumers everywhere today are inundated with seemingly unlimited product reviews  How does one parse through all of this information to make the most informed decisions? What sort of information is most relevant and helpful to consumers when making these decisions? This project uses 50,000 Amazon’s Fine Food Reviews to predict the helpfulness of a review. 

We use several different supervised learning models in order to classify each review into helpful, neutral, or unhelpful. These classification methods are: recurrent neural networks (RNN) including long short-term memory (LSTM) and bi-directional long short-term memory (BiLSTM), Random Forests (RF), and Support Vector Machine (SVM). For the last two, we use a bag-of-words representation of the reviews. We evaluate all the classifiers on their accuracy in categorizing helpfulness.


### DNA Barcode Error Correction  

Java program that implements a motif-finding algorithm to identify and error-correct DNA barcode tags used in single cell sequencing. The algorithm uses k-words (sequence used for pattern alignment) with an associated set of neighbors in the set of all k-letter words to identify a ‘consensus’ barcode by recording the occurance of all possible substitution, deletion and insertion error mutation permutations.  

Through this algorithm, we were able to recover and identify 570 out of 589 cells that were sequenced in a previously published Drop-Seq experiment by Macosko et al. 

### Optimizing Auction Bids

Python program that implements low regret algorithms and takes a machine learning approach to calculating the price at which a bidder should bid at in different types of auction scenarios to secure his or her lowest regret. Other applications of these algorithms could include credit scoring and price determination. 

### Genome Assembly

Java program that takes in a file of DNA sequences and then assembles the genome by identifying the strings that don't belong in the genome, removing them and calculating the overlap between the prefixes and suffixes of the remaining strings. 

### TigerTalk

![Homepage of TigerTalk](images/main.png)

TigerTalk is a web-app designed to facilitate campus discussion across Princeton’s student body. Inspired by websites such as Quora, Reddit and RealTalk Princeton, TigerTalk will allow users to post text entries, questions and discussion topics to generate campus conversation. These posts could be anything from a question about possible fall courses to student opinions on an upcoming referendum and can be tagged to allow for easy searching.

Features include:
* Princeton CAS authentication
* Anonymous posts and comments
* Randomized colors for comment authors 
* Upvoting and downvoting
* Sorting posts by 'recent' and 'popular'
* Search functionality
* Sharing individual posts by URL
* Reporting posts and comments to admins

## Stack

TigerTalk is built on Django and the [Django REST Framework](http://www.django-rest-framework.org/), with a PostgreSQL database. The front-end is built with [React](https://reactjs.org/) and [React-Bootstrap](https://react-bootstrap.github.io/), which are compiled and bundled into a Django template using [Babel](https://babeljs.io/) and [Webpack](https://webpack.js.org/). Authentication is handled by [Princeton's CAS server](https://csguide.cs.princeton.edu/publishing/cas) and [django-cas-ng](https://github.com/mingchen/django-cas-ng). TigerTalk is deployed using Heroku.
