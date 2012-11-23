package es.rchavarria.raccount.model.comparator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.model.Concept;

public class ConceptNameComparatorTest {

    private ConceptNameComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new ConceptNameComparator();
    }

    @Test
    public void testConcept1NameIsNull() {
        Concept c1 = new Concept();
        Concept c2 = new Concept();
        
        assertEquals(1, comparator.compare(c1, c2));
    }
    
    @Test
    public void testConcept2NameIsNull() {
        Concept c1 = new Concept();
        c1.setName("");
        Concept c2 = new Concept();
        
        assertEquals(-1, comparator.compare(c1, c2));
    }
    
    @Test
    public void testConceptsAreSortedAlfabetically() {
        Concept c1 = new Concept();
        Concept c2 = new Concept();
        
        c1.setName("a");
        c2.setName("b");
        assertEquals(-1, comparator.compare(c1, c2));
        
        c1.setName("b");
        c2.setName("a");
        assertEquals(1, comparator.compare(c1, c2));
        
        c1.setName("a");
        c2.setName("a");
        assertEquals(0, comparator.compare(c1, c2));
    }
    
    @Test
    public void testConceptsAreSortedAlfabeticallyIgnoringCase() {
        Concept c1 = new Concept();
        Concept c2 = new Concept();
        
        c1.setName("a");
        c2.setName("B");
        assertTrue(comparator.compare(c1, c2) < 0);
        
        c1.setName("b");
        c2.setName("A");
        assertTrue(comparator.compare(c1, c2) > 0);
        
        c1.setName("A");
        c2.setName("a");
        assertEquals(0, comparator.compare(c1, c2));
    }

}
