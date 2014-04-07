simple-lda
==========

This is a library for running the LDA (Latent Dirichlet Allocation) algorithm on a set of documents. **This library requires Java 8**.

###Classes

**LdaConfig**: a container for configuration values for the LDA algorithm. You probably only want to configure the alpha, beta, and topic count values, unless you know what you are doing.

The alpha value controls what you believe about the relationship between the documents and the topics they contain. Higher values means each document is more likely to contain a mixture of most of the topics. Lower values means each document is more likely to contain a mixture of fewer topics.

The beta value controls what you believe about the relationship between the topics and the terms they contain. Higher values means each topic is more likely to contain a mixture of most of the tokens in the corpus. Lower values means each document is more likely to contain a mixture of fewer tokens in the corpus.

The number of topics is simply how many topics you want the LDA algorithm to assume exists.

**SimpleLda**: A runner for the LDA algorithm. You can pass in a ```List<List<String>>``` to runLda and it will run LDA on the set of documents and return a ```LdaResult``` object. The ```List<List<String>>``` you pass in is a list of documents where each document is a ```List<String>```. Each document should already be tokenized and lowercased (different cased words will be treated as different tokens). Be sure to have already removed stop words or you'll get topics that are dominated with stop words (words like: the, of, is, a, etc.).

**LdaResult**: A result object containing utility methods. You can get the topic or topics for each document and you can get the terms for each topic. A "topic" is really an just an index number (0 to number of topics in the LDAConfig used). See the javadoc for more details.

###Usage

Clone the repo and then run ```mvn package``` to get the uber jar. You can then add it to your build path. You can generate the javadocs by running ```mvn javadoc:javadoc```; the javadocs will be at target/site/apidocs.

