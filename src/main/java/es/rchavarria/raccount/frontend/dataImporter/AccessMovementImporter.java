package es.rchavarria.raccount.frontend.dataImporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.Movement;

/**
 * Importa un fichero con el siguiente formato a objetos Concept:
 * 
 * idMovimiento;Fecha;Cantidad;Concepto;Notas;Cuenta;Saldo_En_Cuenta 5403;06/08/2010;-4,95000 €;2;comida
 * ikea;1;16.197,34 € 5402;06/08/2010;-4,95000 €;2;comida ikea;3;3.831,39 € 5401;06/08/2010;2000,000 €;46;ingreso
 * sobrante banquete;1;16.202,29 € 5400;06/08/2010;2000,000 €;46;ingreso sobrante banquete;3;3.836,34 €
 * 5399;30/07/2010;1052,03000 €;25;nomina monica julio 2010;1;14.202,29 € 5398;30/07/2010;1052,03000 €;25;nomina monica
 * julio 2010;3;1.836,34 €
 * 
 * @author rchavarria
 */
public class AccessMovementImporter {

	private String file;
	private SimpleDateFormat sdf;

	public AccessMovementImporter(final String file) {
		this.file = file;
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
	}

	public List<Movement> doImport() throws ImportException {
		List<Movement> movements = new ArrayList<Movement>();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			is = getClass().getResourceAsStream(this.file);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String line = br.readLine(); // primera fila == cabecera
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(";");
				Movement m = new Movement();
				m.setMovementDate(this.sdf.parse(tokens[1]));
				Double dblValue = NumberFormat.getCurrencyInstance().parse(tokens[2]).doubleValue();
				m.setAmount(dblValue);
				Long lgValue = NumberFormat.getInstance().parse(tokens[3]).longValue();
				Concept conc = new ServiceFacade().findConcept(Translator.translateConcept(lgValue));
				m.setConcept(conc);
				m.setDescription(tokens[4]);
				lgValue = NumberFormat.getInstance().parse(tokens[5]).longValue();
				Account a = new ServiceFacade().findAccount(Translator.translateAccount(lgValue));
				m.setAccount(a);
				if (tokens.length > 6) {
					dblValue = NumberFormat.getCurrencyInstance().parse(tokens[6]).doubleValue();
					m.setFinalBalance(dblValue);
				}

				movements.add(m);
			}

		} catch (SQLException e) {
			String msg = "Error accesing to the database";
			throw new ImportException(msg, e);
		} catch (FileNotFoundException e) {
			String msg = "File not found: " + this.file;
			throw new ImportException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing data";
			throw new ImportException(msg, e);
		} catch (IOException e) {
			String msg = "Error reading file: " + this.file;
			throw new ImportException(msg, e);
		} catch (BussinessException e) {
			String msg = "Error retrieving data from de database";
			throw new ImportException(msg, e);

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Throwable e1) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (Throwable e1) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Throwable e1) {
				}
			}
		}

		return movements;
	}
}
