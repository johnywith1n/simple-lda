package org.johnywith1n.simplelda;

import gov.sandia.cognition.math.matrix.Vector;
import gov.sandia.cognition.text.term.DefaultTerm;
import gov.sandia.cognition.text.term.DefaultTermIndex;
import gov.sandia.cognition.text.term.Term;
import gov.sandia.cognition.text.term.TermIndex;
import gov.sandia.cognition.text.term.vector.BagOfWordsTransform;
import gov.sandia.cognition.text.topic.LatentDirichletAllocationVectorGibbsSampler;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for running the LDA (Latent Dirichlet Allocation) algorithm on a set of
 * tokenized documents.
 * 
 * @author johnylam
 */
public class SimpleLda {
    private final LdaConfig config;

    /**
     * Creates a SimpleLda with the default configs.
     */
    public SimpleLda () {
        this.config = new LdaConfig ();
    }

    /**
     * Creates a SimpleLda with the supplied configs.
     * 
     * @param config
     *            The config object.
     */
    public SimpleLda ( LdaConfig config ) {
        this.config = config;
    }

    /**
     * Run the LDA algorithm on this set of documents and return the result.
     * 
     * @param documents
     *            The set of tokenized documents.
     * @return The LDA result object.
     */
    public LdaResult runLda ( List<List<String>> documents ) {
        BagOfWordsTransform transform = createTransform ( documents );
        List<Vector> vectors = generateDocumentVectors ( documents, transform );
        LatentDirichletAllocationVectorGibbsSampler lda = new LatentDirichletAllocationVectorGibbsSampler (
                config.topicCount, config.alpha, config.beta,
                config.maxIterations, config.burnInInterations,
                config.iterationsPerSamples, config.random );

        return new LdaResult ( lda.learn ( vectors ), transform.getTermIndex () );
    }

    /**
     * Creates a transform that will convert a list of terms into a vector in
     * the vector space that represents this set of documents.
     * 
     * @param documents
     *            The set of documents.
     * @return An object used to transform a document into its vector
     *         representation.
     */
    private BagOfWordsTransform createTransform ( List<List<String>> documents ) {
        TermIndex index = new DefaultTermIndex ();

        for (List<String> doc : documents)
            for (String token : doc)
                index.add ( new DefaultTerm ( token ) );

        return new BagOfWordsTransform ( index );
    }

    /**
     * Generates the vector representation of the documents.
     * 
     * @param documents
     *            The list of tokenized documents.
     * @param transform
     *            The vector transform for this set of documents.
     * @return A list of vectors representing the documents.
     */
    private List<Vector> generateDocumentVectors (
            List<List<String>> documents, BagOfWordsTransform transform ) {

        List<List<Term>> documentsWithTerms = new ArrayList<> ();

        for (List<String> doc : documents) {
            List<Term> terms = new ArrayList<> ();
            for (String token : doc)
                terms.add ( new DefaultTerm ( token ) );
            documentsWithTerms.add ( terms );
        }

        List<Vector> vectors = new ArrayList<> ();

        for (List<Term> terms : documentsWithTerms)
            vectors.add ( transform.convertToVector ( terms ) );

        return vectors;
    }
}
