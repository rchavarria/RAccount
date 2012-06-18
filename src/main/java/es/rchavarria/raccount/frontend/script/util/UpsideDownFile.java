package es.rchavarria.raccount.frontend.script.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;

/**
 * Invierte las lineas de un fichero de texto
 * 
 * @author RChavarria
 */
public class UpsideDownFile {

    private final static Logger log = LoggerFactory.getLogger(EditDoubleMovementController.class);

	private String filePath;

	public UpsideDownFile(String filePath) {
		this.filePath = filePath;
	}

	public void execute() throws IOException {
		List<String> lines = new ArrayList<String>();

		File file = new File(filePath);
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null)
				lines.add(line);
			
		} finally {
			if(br != null) br.close();
			if(fr != null) fr.close();
		}
		
		int idx = filePath.lastIndexOf(".");
		String newFilePath = filePath.substring(0, idx + 1) + "udf";
		File newFile = new File(newFilePath);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(newFile);
			bw = new BufferedWriter(fw);
			for(int i = lines.size() - 1; i >= 0; i--){
				String wLine = lines.get(i);
				bw.write(wLine);
				bw.newLine();
			}

		} finally {
			bw.close();
			fw.close();
		}
		
		log.info("File '" + filePath + "' converted to '" + newFilePath + "'");
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new UpsideDownFile("upsidedownfile.data").execute();
	}

}
