package es.rchavarria.raccount.frontend.dataCompleter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.Completer;
import es.rchavarria.raccount.frontend.dataCompleter.CompleterException;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.DoubleMovement;

/**
 * Completa movimientos pidiendo valores al usuario por consola
 * 
 * @author RChavarria
 */
public class DoubleMovementConsoleCompleter implements Completer<DoubleMovement> {
	private static final String DEFAULT_CONCEPT = "34";
	private static final String DEFAULT_ACCOUNT = "1";

	private InputStreamReader isr;
	private BufferedReader br;
	private PrintStream ps;

	public DoubleMovementConsoleCompleter(final OutputStream os, final InputStream is) {
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		ps = new PrintStream(os);
	}

	@Override
	public DoubleMovement complete(final DoubleMovement element, final int count) throws CompleterException {
		ps.println("Completing element " + count + ": ");

		completeMovementDate(element);
		completeDescription(element);
		completeAmount(element);
		completeAccount(element, true);// from
		completeAccount(element, false);// to
		completeConcept(element);

		return shouldComplete() ? element : null;
	}

	private boolean shouldComplete() {
		String DEFAULT_ANSWER = "Y";
		ps.println("Should this movement be imported (Y/N)? [" + DEFAULT_ANSWER + "]");
		String strIn = DEFAULT_ANSWER;
		try {
			strIn = br.readLine();
		} catch (IOException e) {
		}

		if (strIn.length() == 0) {
			strIn = DEFAULT_ANSWER;
		}
		return DEFAULT_ANSWER.toLowerCase().equals(strIn) || DEFAULT_ANSWER.equals(strIn);
	}

	private void completeConcept(final DoubleMovement element) throws CompleterException {
		List<Concept> concepts = null;
		try {
			concepts = new ServiceFacade().getVisibleConceptList();
			ps.println("Concepts list:");
			for (int i = 1; i <= concepts.size(); i++) {
				Concept a = concepts.get(i - 1);
				ps.println(i + "-> " + a.getName() + "(" + a.getIdConcept() + ")");
			}

		} catch (Exception e) {
			String msg = "Error retrieving concepts from database";
			throw new CompleterException(msg, e);
		}

		String strIn = null;
		try {
			int idx = -1;
			do {
				ps.println("Select a concept: [" + DEFAULT_CONCEPT + "]");
				strIn = br.readLine();
				if (strIn.length() == 0) {
					strIn = DEFAULT_CONCEPT;
				}
				idx = NumberFormat.getIntegerInstance().parse(strIn).intValue() - 1;
			} while (idx < 0 || idx >= concepts.size());
			element.setConcept(concepts.get(idx));

		} catch (IOException e) {
			String msg = "Error reading concept";
			throw new CompleterException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing integer: " + strIn;
			throw new CompleterException(msg, e);
		}

	}

	private void completeAccount(final DoubleMovement element, final boolean boolFrom) throws CompleterException {
		List<Account> accounts = null;
		try {
			accounts = new ServiceFacade().getAccountList();
			ps.println("Accounts list:");
			for (int i = 1; i <= accounts.size(); i++) {
				Account a = accounts.get(i - 1);
				ps.println(i + "-> " + a.getName() + "(" + a.getIdAccount() + ") || " + a.getCodeNumber());
			}

		} catch (Exception e) {
			String msg = "Error retrieving accounts from database";
			throw new CompleterException(msg, e);
		}

		String strIn = null;
		try {
			int idx = -1;
			do {
				ps.println("Select an account [" + DEFAULT_ACCOUNT + "]: ");
				strIn = br.readLine();
				if (strIn.length() == 0) {
					strIn = DEFAULT_ACCOUNT;
				}
				idx = NumberFormat.getIntegerInstance().parse(strIn).intValue() - 1;
			} while (idx < 0 || idx >= accounts.size());
			if (boolFrom) {
				element.setAccount(accounts.get(idx));
			} else {
				element.setAccountTo(accounts.get(idx));
			}

		} catch (IOException e) {
			String msg = "Error reading account";
			throw new CompleterException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing integer: " + strIn;
			throw new CompleterException(msg, e);
		}
	}

	private void completeAmount(final DoubleMovement element) throws CompleterException {
		ps.println("Enter an amount [" + element.getAmount() + "]: ");

		String strIn = null;
		try {
			strIn = br.readLine();
			if (strIn.length() > 0) {
				NumberFormat nf = NumberFormat.getInstance();
				Double dbl = nf.parse(strIn).doubleValue();
				element.setAmount(dbl);
			}

		} catch (IOException e) {
			String msg = "Error reading amount";
			throw new CompleterException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing double value: " + strIn;
			throw new CompleterException(msg, e);
		}
	}

	private void completeDescription(final DoubleMovement element) throws CompleterException {
		ps.println("Enter a description [" + element.getDescription() + "]: ");

		String strIn = null;
		try {
			strIn = br.readLine();
			if (strIn.length() > 0) {
				element.setDescription(strIn);
			}

		} catch (IOException e) {
			String msg = "Error reading description";
			throw new CompleterException(msg, e);
		}
	}

	private void completeMovementDate(final DoubleMovement element) throws CompleterException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String outMsg = "Enter movement date (dd-MM-yyyy)";
		if (element.getMovementDate() != null) {
			outMsg += " [" + sdf.format(element.getMovementDate()) + "]";
		}
		ps.println(outMsg + ": ");

		String strIn = null;
		try {
			strIn = br.readLine();
			if (strIn.length() > 0) {
				Date d = sdf.parse(strIn);
				element.setMovementDate(d);
			}

		} catch (IOException e) {
			String msg = "Error reading movement date";
			throw new CompleterException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing movement date: " + strIn;
			throw new CompleterException(msg, e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (isr != null) {
			isr.close();
		}
		if (br != null) {
			br.close();
		}
		if (ps != null) {
			ps.close();
		}
	}
}
