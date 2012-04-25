package es.rchavarria.raccount.frontend.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataExporter.AccountExporter;
import es.rchavarria.raccount.frontend.dataExporter.ConceptExporter;
import es.rchavarria.raccount.frontend.dataExporter.MovementExporter;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.Movement;

public class ExportFromDerby {

	private static final String DEFAULT_EXPORT_PATH = "export";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		String path = null;
		if (args.length < 2) {
			path = readPathFromUser();
		} else {
			path = args[1];
		}

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}

		exportConcepts(dir.getAbsolutePath());
		exportAccounts(dir.getAbsolutePath());
		exportMovements(dir.getAbsolutePath());
	}

	private static String readPathFromUser() {
		System.out.println("Enter export path (" + DEFAULT_EXPORT_PATH + "): ");

		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = DEFAULT_EXPORT_PATH;
		try {
			isr = new InputStreamReader(System.in);
			br = new BufferedReader(isr);
			line = br.readLine();
			if (line.length() == 0) {
				line = DEFAULT_EXPORT_PATH;
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
				}
			}
		}

		return line;
	}

	private static void exportConcepts(final String path) throws Exception {
		String filePath = path + File.separator + "concepts.csv";
		System.out.println("Exporting concepts to file: " + filePath);
		List<Concept> concepts = new ServiceFacade().getVisibleConceptList();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			new ConceptExporter(fos).export(concepts);
			System.out.println(concepts.size() + " concepts exported successfully");
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	private static void exportAccounts(final String path) throws Exception {
		String filePath = path + File.separator + "accounts.csv";
		System.out.println("Exporting accounts to file: " + filePath);
		List<Account> concepts = new ServiceFacade().getAccountabelAccountList();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			new AccountExporter(fos).export(concepts);
			System.out.println(concepts.size() + " accounts exported successfully");
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	private static void exportMovements(final String path) throws Exception {
		String filePath = path + File.separator + "movements.csv";
		System.out.println("Exporting movements to file: " + filePath);
		List<Movement> concepts = new ServiceFacade().getMovementList();

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			new MovementExporter(fos).export(concepts);
			System.out.println(concepts.size() + " movements exported successfully");
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

}
