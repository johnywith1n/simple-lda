package org.johnywith1n.simplelda;

import gov.sandia.cognition.text.term.TermIndex;
import gov.sandia.cognition.text.topic.LatentDirichletAllocationVectorGibbsSampler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A wrapper for the results from running LDA and methods for interacting with
 * the results.
 * 
 * The references to document index in this class refer to the index of the
 * document in the list passed into the LDA algorithm.
 * 
 * @author johnylam
 *
 */
public class LdaResult {

    /**
     * The number of documents.
     */
    public final int        numDocuments;

    /**
     * The number of topics.
     */
    public final int        numTopics;

    /**
     * The number of unique terms in the set of documents.
     */
    public final int        numTerms;

    /**
     * The probability table between document index numbers and topics. The
     * index numbers of the documents are the index numbers of the documents in
     * the list passed into LDA. The first index is the document and the second
     * index is the topic.
     */
    public final double[][] documentTopicProbs;

    /**
     * The probability table between topics and the terms. The term for a
     * particular array index can be retrieved by calling getTermForIndexNumber.
     * The first index is the topic and the second index is the term.
     */
    public final double[][] topicTermProbs;

    /**
     * An index mapping all the unique terms in the documents to index numbers.
     */
    private final TermIndex index;

    /**
     * Creates the LDA Result object.
     * 
     * @param result
     *            The result from running LDA.
     * @param index
     *            The term index from the vector transform created for this set of documents.
     */
    public LdaResult (
            LatentDirichletAllocationVectorGibbsSampler.Result result,
            TermIndex index ) {
        this.numDocuments = result.getDocumentCount ();
        this.numTopics = result.getTopicCount ();
        this.numTerms = result.getTermCount ();
        this.documentTopicProbs = result.getDocumentTopicProbabilities ();
        this.topicTermProbs = result.getTopicTermProbabilities ();
        this.index = index;
    }

    /**
     * Get the term for <I>indexNumber</i> where <I>indexNumber</i> is the index
     * of the topic to terms probability table.
     * 
     * @param indexNumber
     *            The index number.
     * @return The term.
     */
    public String getTermForIndexNumber ( int indexNumber ) {
        return index.getTerm ( indexNumber ).getName ();
    }

    /**
     * Gets a stream of pairs of array indices and values of a particular
     * probability array. The array is sorted so that the values of the pairs
     * are in descending order.
     * 
     * @param probabilities
     *            the array of probabilities
     * @return The stream of index value pairs.
     */
    private Stream<IndexValuePair> getSortedIndexValuePairStream (
            double[] probabilities ) {
        List<IndexValuePair> pairs = new ArrayList<> ();

        for (int i = 0; i < probabilities.length; i++)
            pairs.add ( new IndexValuePair ( i, probabilities[i] ) );

        return pairs.stream ().sorted ( Collections.reverseOrder () );
    }

    /**
     * Get a list of the top n words for a particular topic.
     * 
     * @param topicIndex
     *            The index of the topic to get words for.
     * @param n
     *            The number of words to return for the topic.
     * @return A list of words representing the topic.
     */
    public List<String> getTopNWordsForTopic ( int topicIndex, int n ) {
        return getSortedIndexValuePairStream ( topicTermProbs[topicIndex] )
                .limit ( n ).map ( ( pair ) -> {
                    return getTermForIndexNumber ( pair.index );
                } ).collect ( Collectors.toList () );
    }

    /**
     * Get a list of the words that have at least a certain probability of being
     * in the selected topic.
     * 
     * @param topicIndex
     *            The index of the topic to get words for.
     * @param minProb
     *            The minimum probability of a term being in a topic.
     * @return A list of words representing the topic.
     */
    public List<String> getTopProbabilityWordsForTopic ( int topicIndex,
            double minProb ) {
        return getSortedIndexValuePairStream ( topicTermProbs[topicIndex] )
                .filter ( ( pair ) -> pair.value > minProb )
                .map ( ( pair ) -> {
                    return getTermForIndexNumber ( pair.index );
                } ).collect ( Collectors.toList () );
    }

    /**
     * Get the top N topics for a given document.
     * 
     * @param documentIndex
     *            The index of the document. This is the index of the document
     *            in the list passed into the LDA algorithm.
     * @param n
     *            The number of topics to get for the document.
     * @return A list of topic indices that this document is assocaited with.
     */
    public List<Integer> getTopNTopicsForDocument ( int documentIndex, int n ) {
        return getSortedIndexValuePairStream (
                documentTopicProbs[documentIndex] ).limit ( n )
                .map ( ( pair ) -> {
                    return pair.index;
                } ).collect ( Collectors.toList () );
    }

    /**
     * Get the most likely topic for this document.
     * 
     * @param documentIndex
     *            The index of the document. This is the index of the document
     *            in the list passed into the LDA algorithm.
     * @return The index of the topic this document most likely belongs to.
     */
    public int getTopicForDocument ( int documentIndex ) {
        return getTopNTopicsForDocument ( documentIndex, 1 ).get ( 0 );
    }

    /**
     * Get the topics that exceed a minimum probability of being associated with
     * the document.
     * 
     * @param documentIndex
     *            The index of the document. This is the index of the document
     *            in the list passed into the LDA algorithm.
     * @param minProb
     *            The minimum probability that a topic is associated with the
     *            document.
     * @return A list of topic indices.
     */
    public List<Integer> getTopProbablityTopicsForDocument ( int documentIndex,
            double minProb ) {
        return getSortedIndexValuePairStream (
                documentTopicProbs[documentIndex] )
                .filter ( ( pair ) -> pair.value > minProb )
                .map ( ( pair ) -> {
                    return pair.index;
                } ).collect ( Collectors.toList () );
    }

    public class IndexValuePair implements Comparable<IndexValuePair> {
        public final int    index;
        public final double value;

        public IndexValuePair ( int index, double value ) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo ( IndexValuePair o ) {
            return Double.compare ( value, o.value );
        }

    }
}
