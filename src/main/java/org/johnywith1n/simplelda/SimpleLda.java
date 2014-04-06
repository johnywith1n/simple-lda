package org.johnywith1n.simplelda;

/**
 * @author johnylam
 *
 *         Runs the LDA (Latent Dirichlet Allocation) algorithm on a passed set
 *         of tokenized documents.
 */
public class SimpleLda
{
    private final LdaConfig config;

    public SimpleLda ()
    {
        this.config = new LdaConfig();
    }

    public SimpleLda (LdaConfig config)
    {
        this.config = config;
    }

}
