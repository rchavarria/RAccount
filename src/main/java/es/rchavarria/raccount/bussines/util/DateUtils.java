package es.rchavarria.raccount.bussines.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * @param month
     *      mes para el que retornar el primer día: 1=enero, 12=diciembre
     *      
     * @return
     *      primer dia del mes, con la hora a cero
     */
    public static Date getFirstDayOfMonth(int month) {
        Calendar cal = new GregorianCalendar();
        cal.set(cal.get(Calendar.YEAR), month - 1, 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * @param month
     *      mes para el que retornar el primer día: 1=enero, 12=diciembre
     *      
     * @return
     *      ultimo dia del mes, ultima hora
     */
    public static Date getLastDayOfMonth(int month) {
        Calendar cal = new GregorianCalendar();
        cal.set(cal.get(Calendar.YEAR), month, 1, 23, 59, 59);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
}
