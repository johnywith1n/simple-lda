package org.johnywith1n.simplelda;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.sandia.cognition.text.term.DefaultTerm;
import gov.sandia.cognition.text.term.Term;
import gov.sandia.cognition.text.term.TermIndex;
import gov.sandia.cognition.text.topic.LatentDirichletAllocationVectorGibbsSampler;
import gov.sandia.cognition.text.topic.LatentDirichletAllocationVectorGibbsSampler.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class LdaResultUnitTest {

    @Test
    public void testGetSortedIndexValuePairStream () {
        Result _result = mock ( LatentDirichletAllocationVectorGibbsSampler.Result.class );
        TermIndex index = mock ( TermIndex.class );

        Map<Integer, Term> terms = new HashMap<> ();
        terms.put ( 0, new DefaultTerm ( "what" ) );
        terms.put ( 1, new DefaultTerm ( "when" ) );
        terms.put ( 2, new DefaultTerm ( "where" ) );
        terms.put ( 3, new DefaultTerm ( "why" ) );
        terms.put ( 4, new DefaultTerm ( "who" ) );

        when ( index.getTerm ( anyInt () ) ).thenAnswer ( new Answer<Term> () {
            @Override
            public Term answer ( InvocationOnMock invocation ) throws Throwable {
                int index = (int) invocation.getArguments ()[0];
                return terms.get ( index );
            }
        } );

        double[][] probs = new double[][] { new double[] {},
                new double[] { 0.15, 0.3, 0.2, 0.25, 0.1 } };
        when ( _result.getTopicTermProbabilities () ).thenReturn ( probs );

        LdaResult result = new LdaResult ( _result, index );

        List<String> words = result.getTopNWordsForTopic ( 1, 3 );

        assertThat ( words.size () ).isEqualTo ( 3 );
        assertThat ( words.get ( 0 ) ).isEqualTo ( "when" );
        assertThat ( words.get ( 1 ) ).isEqualTo ( "why" );
        assertThat ( words.get ( 2 ) ).isEqualTo ( "where" );
    }

    @Test
    public void testGetTopProbabilityWordsForTopic () {
        Result _result = mock ( LatentDirichletAllocationVectorGibbsSampler.Result.class );
        TermIndex index = mock ( TermIndex.class );

        Map<Integer, Term> terms = new HashMap<> ();
        terms.put ( 0, new DefaultTerm ( "what" ) );
        terms.put ( 1, new DefaultTerm ( "when" ) );
        terms.put ( 2, new DefaultTerm ( "where" ) );
        terms.put ( 3, new DefaultTerm ( "why" ) );
        terms.put ( 4, new DefaultTerm ( "who" ) );

        when ( index.getTerm ( anyInt () ) ).thenAnswer ( new Answer<Term> () {
            @Override
            public Term answer ( InvocationOnMock invocation ) throws Throwable {
                int index = (int) invocation.getArguments ()[0];
                return terms.get ( index );
            }
        } );

        double[][] probs = new double[][] { new double[] {}, new double[] {},
                new double[] { 0.15, 0.3, 0.2, 0.25, 0.1 } };
        when ( _result.getTopicTermProbabilities () ).thenReturn ( probs );

        LdaResult result = new LdaResult ( _result, index );

        List<String> words = result.getTopProbabilityWordsForTopic ( 2, 0.249 );

        assertThat ( words.size () ).isEqualTo ( 2 );
        assertThat ( words.get ( 0 ) ).isEqualTo ( "when" );
        assertThat ( words.get ( 1 ) ).isEqualTo ( "why" );
    }

    @Test
    public void testGetTopNTopicsForDocument () {
        Result _result = mock ( LatentDirichletAllocationVectorGibbsSampler.Result.class );
        TermIndex index = mock ( TermIndex.class );

        double[][] probs = new double[][] { new double[] {}, new double[] {},
                new double[] { 0.15, 0.3, 0.2, 0.25, 0.1 } };
        when ( _result.getDocumentTopicProbabilities () ).thenReturn ( probs );

        LdaResult result = new LdaResult ( _result, index );

        List<Integer> topics = result.getTopNTopicsForDocument ( 2, 3 );

        assertThat ( topics.size () ).isEqualTo ( 3 );
        assertThat ( topics.get ( 0 ) ).isEqualTo ( 1 );
        assertThat ( topics.get ( 1 ) ).isEqualTo ( 3 );
        assertThat ( topics.get ( 2 ) ).isEqualTo ( 2 );
    }

    @Test
    public void testGetTopicForDocument () {
        Result _result = mock ( LatentDirichletAllocationVectorGibbsSampler.Result.class );
        TermIndex index = mock ( TermIndex.class );

        double[][] probs = new double[][] { new double[] {}, new double[] {},
                new double[] { 0.15, 0.3, 0.2, 0.25, 0.1 } };
        when ( _result.getDocumentTopicProbabilities () ).thenReturn ( probs );

        LdaResult result = new LdaResult ( _result, index );

        int topic = result.getTopicForDocument ( 2 );

        assertThat ( topic ).isEqualTo ( 1 );
    }

    @Test
    public void testGetTopProbablityTopicsForDocument () {
        Result _result = mock ( LatentDirichletAllocationVectorGibbsSampler.Result.class );
        TermIndex index = mock ( TermIndex.class );

        double[][] probs = new double[][] { new double[] {}, new double[] {},
                new double[] { 0.15, 0.3, 0.2, 0.25, 0.1 } };
        when ( _result.getDocumentTopicProbabilities () ).thenReturn ( probs );

        LdaResult result = new LdaResult ( _result, index );

        List<Integer> topics = result.getTopProbablityTopicsForDocument ( 2,
                0.249 );

        assertThat ( topics.size () ).isEqualTo ( 2 );
        assertThat ( topics.get ( 0 ) ).isEqualTo ( 1 );
        assertThat ( topics.get ( 1 ) ).isEqualTo ( 3 );
    }
}
