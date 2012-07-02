package es.rchavarria.raccount.bussines.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void testFirstDayOfMonth() {
        int month = 5;
        Date first = DateUtils.getFirstDayOfMonth(month);
        Calendar cal = new GregorianCalendar();
        cal.setTime(first);
        
        assertEquals(month - 1, cal.get(Calendar.MONTH));
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void testLastDayOfMay() {
        Date lastDayOfMay = DateUtils.getLastDayOfMonth(5);
        Calendar cal = new GregorianCalendar();
        cal.setTime(lastDayOfMay);
        
        assertEquals(31, cal.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void testLastDayOfJune() {
        Date lastDayOfJune = DateUtils.getLastDayOfMonth(6);
        Calendar cal = new GregorianCalendar();
        cal.setTime(lastDayOfJune);
        
        assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
    }

}
