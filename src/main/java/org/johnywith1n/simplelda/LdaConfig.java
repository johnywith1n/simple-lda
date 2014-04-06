package org.johnywith1n.simplelda;

import gov.sandia.cognition.text.topic.LatentDirichletAllocationVectorGibbsSampler;

import java.util.Random;

/**
 * @author johnylam
 *
 *         Config object to store values controlling the LDA algorithm.
 */
public class LdaConfig
{
    public final double alpha;

    public final double beta;

    public final int    burnInInterations;

    public final int    iterationsPerSamples;

    public final int    maxIterations;

    public final Random random;

    public final int    topicCount;

    /**
     * Creates an LDA config object with default values for the unspecified
     * parameters.
     * 
     * @param alpha
     *            Higher values means each document is more likely to contain a
     *            mixture of most of the topics. Lower values means each
     *            document is more likely to contain a mixture of fewer topics.
     * @param beta
     *            Higher values means each topic is more likely to contain a
     *            mixture of most of the tokens in the corpus. Lower values
     *            means each document is more likely to contain a mixture of
     *            fewer tokens in the corpus.
     * @param topicCount
     *            The number of topics.
     */
    public LdaConfig (double alpha, double beta, int topicCount)
    {
        this.alpha = alpha;
        this.beta = beta;
        this.topicCount = topicCount;

        this.burnInInterations = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_BURN_IN_ITERATIONS;
        this.iterationsPerSamples = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_BURN_IN_ITERATIONS;
        this.maxIterations = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_MAX_ITERATIONS;
        this.random = new Random();
    }

    /**
     * Creates an LDA config object.
     * 
     * @param alpha
     *            Higher values means each document is more likely to contain a
     *            mixture of most of the topics. Lower values means each
     *            document is more likely to contain a mixture of fewer topics.
     * @param beta
     *            Higher values means each topic is more likely to contain a
     *            mixture of most of the tokens in the corpus. Lower values
     *            means each document is more likely to contain a mixture of
     *            fewer tokens in the corpus.
     * @param burnInIterations
     *            The number of iterations to discard before sampling for the
     *            Markov Chain Monte Carlo Algorithm.
     * @param iterationsPerSamples
     *            The number of iterations between samples for the Markov Chain
     *            Monte Carlo algorithm (after burn-in iterations).
     * @param maxIterations
     *            Maximum number of iterations before stopping.
     * @param random
     *            The random number generator to use.
     * @param topicCount
     *            The number of topics.
     */
    public LdaConfig (double alpha, double beta, int burnInIterations,
            int iterationsPerSamples, int maxIterations, Random random,
            int topicCount)
    {
        this.alpha = alpha;
        this.beta = beta;
        this.burnInInterations = burnInIterations;
        this.iterationsPerSamples = iterationsPerSamples;
        this.maxIterations = maxIterations;
        this.random = random;
        this.topicCount = topicCount;
    }

    /**
     * Creates a config object with the default values.
     */
    public LdaConfig ()
    {
        this.alpha = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_ALPHA;
        this.beta = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_BETA;
        this.burnInInterations = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_BURN_IN_ITERATIONS;
        this.iterationsPerSamples = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_BURN_IN_ITERATIONS;
        this.maxIterations = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_MAX_ITERATIONS;
        this.random = new Random();
        this.topicCount = LatentDirichletAllocationVectorGibbsSampler.DEFAULT_TOPIC_COUNT;
    }

}
