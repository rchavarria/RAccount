package es.rchavarria.raccount.frontend.dataImporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.rchavarria.raccount.model.Account;

/**
 * Importa un fichero a objetos Account con la siguiente estructura:
 * 
 * idCuenta;Cuenta;Saldo;Saldo_Contabilizable
 * 1;Cta Saldo Total;16.197,34 €;VERDADERO
 * 2;Ibercaja Monica/Ruben/Arturo/Cesar;0,33 €;FALSO
 * 2;INg de todos;-0,33 €;FALSO;codigo de la cuenta (opcional)
 * [ . . . ]
 * 
 * @author rchavarria
 */
public class AccountImporter {
    
    private InputStream is;
    private NumberFormat nf;
    
    public AccountImporter(InputStream is){
        this.is = is;
        nf = NumberFormat.getInstance(new Locale("es", "ES"));
    }

    public List<Account> doImport() throws ImportException{
        List<Account> accounts = new ArrayList<Account>();
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
        	isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String line = br.readLine(); // primera fila == cabecera
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                Account a = new Account();
                a.setName(tokens[1]);
                
                String numberStr = tokens[2].substring(0, tokens[2].indexOf(" "));
                Double dblValue = nf.parse(numberStr).doubleValue();
                a.setBalance(dblValue);
                a.setAccountable("VERDADERO".equals(tokens[3]) ? true : false);
                if(tokens.length > 4)
                	a.setCodeNumber(tokens[4]);
                
                accounts.add(a);
            }
            
        } catch (IOException e) {
            throw new ImportException("Error reading file: " + is, e);

        } catch (ParseException e) {
            throw new ImportException("Error parsing contents of file: " + is, e);
            
        } finally {
            if(br != null){
                try { br.close(); } 
                catch (Throwable e1) { }
            }
            if(isr != null){
                try { isr.close(); } 
                catch (Throwable e1) { }
            }
        }

        return accounts;
    }
}
