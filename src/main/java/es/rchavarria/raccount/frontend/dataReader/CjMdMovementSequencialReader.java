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
 * Parsea/Importa secuencialmente movimientos de Caja Mandril. El documento de texto desde el que importar se consigue a
 * través de la web: -> Descargar movimientos > Texto Cada vez que se le pide, va proporcionando un nuevo Movement
 * incompleto
 * 
 * @author RChavarria
 */
public class CjMdMovementSequencialReader implements SequencialReader<DoubleMovement> {

    private SimpleDateFormat sdf;
    private NumberFormat nf;
    private InputStreamReader isr;
    private BufferedReader reader;

    public CjMdMovementSequencialReader(final InputStream is) {
        isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        nf = NumberFormat.getNumberInstance(new Locale("es"));// se importara con los numeros en formato español
    }

    /**
     * Formato del fichero que se parsea:
     * "Fecha","Fecha valor","Descripción","Importe","Divisa","Saldo posterior","Divisa"
     * ,"Concepto 1","Concepto 2","Concepto 3","Concepto 4","Concepto 5","Concepto 6"
     * "15/07/2010","15/07/2010","PAGO EN EFECTIVO"
     * ,"-4.500,00","EUR","320,60","EUR","2875","Monica Lopez Salido","MONICA LOPEZ SALIDO"
     * "15/07/2010","15/07/2010","",
     * "12,00","EUR","332,60","EUR","2875","CUOTA DE ALTA / MANTENIMIENTO ANUAL MARCA PROPIA"
     * ,"20382875","10300","20382875383006312725" "15/07/2010","15/07/2010","","12,00","EUR","344,60","EUR","2875",
     * "CUOTA DE ALTA / MANTENIMIENTO ANUAL MARCA PROPIA","20382875","10300","20382875383006312725"
     * 
     * @return
     * @throws ReaderException
     */
    @Override
    public DoubleMovement next() throws ReaderException {
        String line = null;
        String[] tokens = null;

        try {
            line = reader.readLine();
            if (line == null || line.length() == 0) {
                return null;
            }
            line = line.trim();
            line = line.replace("\",\"", ";");
            line = line.replace("\"", "");

            tokens = line.split(";");
            DoubleMovement m = new DoubleMovement();
            m.setMovementDate(sdf.parse(tokens[1]));
            String desc = tokens[2];
            if (desc.length() == 0 || "CARGO COMPRA".equals(desc)) {
                if (tokens.length >= 11) {
                    desc = tokens[11];
                } else {
                    desc = tokens[8];
                }
            }
            m.setDescription(desc);
            Double dblValue = nf.parse(tokens[3]).doubleValue();
            m.setAmount(dblValue);

            return m;

        } catch (IOException e) {
            throw new ReaderException("Error reading from file", e);

        } catch (ParseException e) {
            String strDate = "Empty date";
            if (tokens != null && tokens.length >= 1) {
                strDate = tokens[1];
            }
            String strCurrency = "Empty currency";
            if (tokens != null && tokens.length >= 3) {
                strCurrency = tokens[3];
            }

            String msg = "Error parsing date: " + strDate + " or currency: " + strCurrency;
            throw new ReaderException(msg, e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (isr != null) {
            isr.close();
        }
        if (reader != null) {
            reader.close();
        }
    }
}
