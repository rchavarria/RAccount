package es.rchavarria.raccount.model.comparator;

import java.util.Comparator;

import es.rchavarria.raccount.model.Concept;

/**
 * Compara {@link Concept} según su nombre
 * 
 * @author rchavarria
 *
 */
public class ConceptNameComparator implements Comparator<Concept> {

    @Override
    public int compare(Concept c1, Concept c2) {
        if(c1.getName() == null) {
            return 1;
        }
        
        if(c2.getName() == null) {
            return -1;
        }
        
        return c1.getName().compareToIgnoreCase(c2.getName());
    }

}
