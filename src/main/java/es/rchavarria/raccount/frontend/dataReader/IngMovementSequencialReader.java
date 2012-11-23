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
 * Parsea/Importa secuencialmente movimientos de ING Direct
 * Cada vez que se le pide, va proporcionando un nuevo Movement incompleto
 * 
 * @author RChavarria
 */
public class IngMovementSequencialReader implements SequencialReader<DoubleMovement> {

    private SimpleDateFormat sdf;
    private NumberFormat nf;
    private InputStreamReader isr;
	private BufferedReader reader;
    
    public IngMovementSequencialReader(InputStream is){
    	isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        nf = NumberFormat.getNumberInstance(new Locale("es"));//se importara con los numeros en formato español
    }

    /**
     * Formato del fichero que se parsea:
     * fecha     ;fecha     ; descripcion                         ; cantidad	; saldo final
     * 17-08-2010;17-08-2010;Traspaso periódico recibido nómina (SPO FROM 2095782872);28,00;2.201,71
	 * 17-08-2010;17-08-2010;Pago HIPER USERA T73 PIOZ ES;-10,42;2.191,29
	 * 18-08-2010;18-08-2010;Recibo CITILIFE FINANCIAL LIMITED;-26,75;2.164,54
	 * 
     * @return
     * @throws ReaderException 
     */
    public DoubleMovement next() throws ReaderException {
		String line = null;
		String[] tokens = null;
		
		try {
			line = reader.readLine();
			if (line == null) return null;

			tokens = line.split(";");
			DoubleMovement m = new DoubleMovement();
			m.setMovementDate(sdf.parse(tokens[1]));
			m.setDescription(tokens[2]);
			Double dblValue = nf.parse(tokens[3]).doubleValue();
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
