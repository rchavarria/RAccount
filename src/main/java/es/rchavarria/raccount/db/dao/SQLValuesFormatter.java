package es.rchavarria.raccount.db.dao;

import java.text.SimpleDateFormat;

import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Movement;

/**
 * Formatea adecuadamente entidades en la parte VALUES de las sentencias SQL de inserción
 * de datos
 * 
 * @author rchavarria
 *
 */
public class SQLValuesFormatter {

    /**
     * @param movement
     *      movimiento a formatear
     *      
     * @param account
     *      cuenta asociada al movimiento
     *      
     * @return
     *      string segura para inserciones SQL
     */
    public String format(Movement movement, Account account) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String values = "'" + cleanUpDangerousCharacters(movement.getDescription()) + "', " +
                        "'" + sdf.format(movement.getMovementDate()) + "', " +
                        movement.getAmount() + ", " +
                        movement.getFinalBalance() + ", " +
                        account.getIdAccount() + ", " +
                        movement.getConcept().getIdConcept();
        return values;
    }

    /**
     * Elimina caracteres peligrosos en sentencias SQL
     * 
     * @param string
     *      string a limpiar
     *      
     * @return
     *      un string limpio y seguro para sentencias SQL
     */
    public String cleanUpDangerousCharacters(String string) {
        String clean = string.replace("'", "_");
        return clean;
    }
}
