package es.rchavarria.raccount.frontend.dataExporter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.List;

import es.rchavarria.raccount.model.Account;

public class AccountExporter {

	private FileOutputStream fos;

	public AccountExporter(FileOutputStream fos) {
		this.fos = fos;
	}

	public void export(List<Account> original) throws ExportException {
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

        try {
        	osw = new OutputStreamWriter(fos);
        	bw = new BufferedWriter(osw);

        	//cabecera
        	bw.write("idCuenta;Cuenta;Saldo;Saldo_Contabilizable;Codigo_Cuenta");
        	bw.newLine();
        	
        	for(Account a : original){
        		String id = new Integer(-1).toString(); //no se importa ni exporta
        		String name = a.getName();
        		String balance = NumberFormat.getCurrencyInstance().format(a.getBalance());
        		String visible = a.isAccountable() ? "VERDADERO" : "FALSO";
        		String codeNumber = a.getCodeNumber();
        		
        		bw.write(id + ";" + name + ";" + balance + ";" + visible + ";" + codeNumber);
        		bw.newLine();
        	}
            
        } catch (Exception e) {
            throw new ExportException("Error reading to output stream: " + fos.toString(), e);

        } finally {
        	if(bw != null){
        		try { bw.close(); } 
        		catch (Throwable e1) { }
        	}
        	if(osw != null){
        		try { osw.close(); } 
        		catch (Throwable e1) { }
        	}
        }
	}

}
