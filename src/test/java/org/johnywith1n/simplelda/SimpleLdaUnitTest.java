package org.johnywith1n.simplelda;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SimpleLdaUnitTest {

    @Test
    public void testRunLda () {
        SimpleLda lda = new SimpleLda ( new LdaConfig ( 1.0, 0.1, 3 ) );

        String doc1 = "Pokémon Pokémon Pokémon Pokémon video games fictional world Pokémon";
        String doc2 = "Pokémon Pokémon Pokémon Pokémon video games fictional world Pokémon";
        String doc3 = "Pokémon Pokémon Pokémon Pokémon video games fictional world Pokémon";
        String doc4 = "Java Java Java Java computer programming language concurrent implementation";
        String doc5 = "Java Java Java Java computer programming language concurrent implementation";
        String doc6 = "Java Java Java Java computer programming language concurrent implementation";
        String doc7 = "derivative derivative derivative derivative function real variable measures sensitivity";
        String doc8 = "derivative derivative derivative derivative  function real variable measures sensitivity";
        String doc9 = "derivative derivative derivative derivative  function real variable measures sensitivity";

        List<List<String>> documents = new ArrayList<> ();
        documents.add ( Arrays.asList ( doc1.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc2.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc3.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc4.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc5.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc6.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc7.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc8.toLowerCase ().split ( "\\s+" ) ) );
        documents.add ( Arrays.asList ( doc9.toLowerCase ().split ( "\\s+" ) ) );

        Set<String> terms = new HashSet<> ();

        documents.forEach ( ( doc ) -> doc.forEach ( ( token ) -> terms
                .add ( token ) ) );

        LdaResult result = lda.runLda ( documents );

        assertThat ( result.numDocuments ).isEqualTo ( 9 );
        assertThat ( result.numTopics ).isEqualTo ( 3 );
        assertThat ( result.numTerms ).isEqualTo ( terms.size () );
        assertThat ( result.documentTopicProbs.length ).isEqualTo (
                result.numDocuments );
        assertThat ( result.topicTermProbs.length ).isEqualTo (
                result.numTopics );

        for (double[] array : result.documentTopicProbs)
            assertThat ( array.length ).isEqualTo ( result.numTopics );

        for (double[] array : result.topicTermProbs)
            assertThat ( array.length ).isEqualTo ( result.numTerms );

        int doc1Topic = result.getTopicForDocument ( 0 );
        int doc2Topic = result.getTopicForDocument ( 1 );
        int doc3Topic = result.getTopicForDocument ( 2 );

        assertThat ( doc1Topic ).isEqualTo ( doc2Topic );
        assertThat ( doc2Topic ).isEqualTo ( doc3Topic );

        assertThat ( result.getTopNWordsForTopic ( doc1Topic, 1 ).get ( 0 ) )
                .isEqualTo ( "pokémon" );

        int doc4Topic = result.getTopicForDocument ( 3 );
        int doc5Topic = result.getTopicForDocument ( 4 );
        int doc6Topic = result.getTopicForDocument ( 5 );

        assertThat ( doc4Topic ).isEqualTo ( doc5Topic );
        assertThat ( doc5Topic ).isEqualTo ( doc6Topic );

        assertThat ( result.getTopNWordsForTopic ( doc4Topic, 1 ).get ( 0 ) )
                .isEqualTo ( "java" );

        int doc7Topic = result.getTopicForDocument ( 6 );
        int doc8Topic = result.getTopicForDocument ( 7 );
        int doc9Topic = result.getTopicForDocument ( 8 );

        assertThat ( doc7Topic ).isEqualTo ( doc8Topic );
        assertThat ( doc8Topic ).isEqualTo ( doc9Topic );

        assertThat ( result.getTopNWordsForTopic ( doc7Topic, 1 ).get ( 0 ) )
                .isEqualTo ( "derivative" );

    }
}
