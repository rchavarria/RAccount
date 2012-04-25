package es.rchavarria.raccount.frontend.dataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import es.rchavarria.raccount.model.DoubleMovement;

/**
 * Parsea/Importa secuencialmente movimientos de Iberpaja
 * Cada vez que se le pide, va proporcionando un nuevo Movement incompleto
 * 
 * @author RChavarria
 */
public class IbcjMovementSequencialReader implements SequencialReader<DoubleMovement> {

    private static final String SEPARATOR = "\t";
	private SimpleDateFormat sdf;
    private NumberFormat nf;
    private InputStreamReader isr;
	private BufferedReader reader;
    
    public IbcjMovementSequencialReader(InputStream is){
    	isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        sdf = new SimpleDateFormat("dd/MM/yy");
        nf = NumberFormat.getNumberInstance(new Locale("es"));//se importara con los numeros en formato español
    }

    /**
     * Formato del fichero que se parsea: un movimiento esta en 2 o mas lineas
	 * T6000 SUMINISTRO AG
	 * 13/07/10	11/07/10	0448051552	10,00+	1.182,79	Eur	
	 * OPERACION EN EFECTIVO
	 * REGALO BODA ALFREDO JUANA Y FAMILIA
	 * 13/07/10	13/07/10	1844343700	600,00+	1.782,79	Eur	
	 * 
     * @return
     * @throws ReaderException 
     */
    public DoubleMovement next() throws ReaderException {
		String description = null;
		String more = null;
		String[] tokens = null;
		
		try {
			description = reader.readLine();
			if (description == null || description.length() == 0) return null;
			
			more = reader.readLine();
			if(more.indexOf(SEPARATOR) == -1) {
				//no hay tabuladores, la descripcion sigue
				description += " /// " + more;
				more = reader.readLine();
			}
			
			tokens = more.split(SEPARATOR);
			DoubleMovement m = new DoubleMovement();
			m.setMovementDate(sdf.parse(tokens[1]));
			m.setDescription(description);
			Double dblValue = nf.parse(tokens[3]).doubleValue();
			if(tokens[3].indexOf("-") != -1) dblValue *= -1;
			m.setAmount(dblValue);

			return m;
			
		} catch (IOException e) {
			throw new ReaderException("Error reading from file", e);
			
		} catch (ParseException e) {
			String strDate = "Empty date";
			if(tokens != null && tokens.length >= 1)
				strDate = tokens[1];
			String strCurrency = "Empty currency";
			if(tokens != null && tokens.length >= 3)
				strCurrency = tokens[3];
			
			String msg = "Error parsing date: " + strDate + " or currency: " + strCurrency;
			throw new ReaderException(msg, e);
		}
    }
    
    @Override
    protected void finalize() throws Throwable {
    	if(isr != null) isr.close();
    	if(reader != null) reader.close();
    }
}
