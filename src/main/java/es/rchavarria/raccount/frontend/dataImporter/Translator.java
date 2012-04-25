package es.rchavarria.raccount.frontend.dataImporter;

import java.util.HashMap;
import java.util.Map;

public class Translator {
    /**
     * Mapa de idConcepts: clave -> antiguo, valor -> idConcept en BBDD
     */
    private static Map<Long, Long> concepts = new HashMap<Long, Long>();
    static{
        concepts.put(43L, 1L);
        concepts.put(44L, 2L);
        concepts.put(46L, 3L);
        concepts.put(27L, 4L);
        concepts.put(15L, 5L);
        concepts.put(16L, 6L);
        concepts.put(14L, 7L);
        concepts.put(31L, 8L);
        concepts.put(28L, 9L);
        concepts.put(20L, 10L);
        concepts.put(33L, 11L);
        concepts.put(32L, 12L);
        concepts.put(45L, 13L);
        concepts.put(21L, 14L);
        concepts.put(41L, 15L);
        concepts.put(40L, 16L);
        concepts.put(2L, 17L);
        concepts.put(30L, 18L);
        concepts.put(39L, 19L);
        concepts.put(29L, 20L);
        concepts.put(11L, 21L);
        concepts.put(23L, 22L);
        concepts.put(25L, 23L);
        concepts.put(1L, 24L);
        concepts.put(42L, 25L);
        concepts.put(24L, 26L);
        concepts.put(37L, 27L);
        concepts.put(36L, 28L);
        concepts.put(35L, 29L);
        concepts.put(38L, 30L);
        concepts.put(34L, 31L);
        concepts.put(17L, 32L);
        concepts.put(26L, 33L);
        concepts.put(22L, 34L);
    }
    
    private static Map<Long, Long> accounts = new HashMap<Long, Long>();
    static {
        accounts.put(1L, 1L);
        accounts.put(2L, 2L);
        accounts.put(3L, 3L);
        accounts.put(4L, 4L);
        accounts.put(5L, 5L);
        accounts.put(6L, 6L);
        accounts.put(8L, 7L);
        accounts.put(9L, 8L);
        accounts.put(14L, 9L);
        accounts.put(15L, 10L);
        accounts.put(16L, 11L);
        accounts.put(17L, 12L);
        accounts.put(18L, 13L);
        accounts.put(19L, 14L);
        accounts.put(20L, 15L);
        accounts.put(21L, 16L);
    }
    
    /**
     * Traduce del idConcept de un movimiento a importar a idConcept en BBDD
     * @param oldIdConcept idConcept de un movimiento en un fichero de importar movimientos 
     * @return idConcept de ese concepto en BBDD
     */
    public static long translateConcept(long oldIdConcept){
        return concepts.get(oldIdConcept);
    }
    
    /**
     * Traduce del idAccount de un movimiento a importar a idAccount en BBDD
     * @param oldIdConcept idAccount de un movimiento en un fichero de importar movimientos 
     * @return idAccount de esa cuenta en BBDD
     */
    public static long translateAccount(long oldIdAccount){
        return accounts.get(oldIdAccount);
    }
}
