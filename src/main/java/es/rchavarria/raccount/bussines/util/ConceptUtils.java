package es.rchavarria.raccount.bussines.util;

import es.rchavarria.raccount.model.Concept;

public class ConceptUtils {

	public static boolean isTransfer(Concept concept) {
		return "Traspasos".equals(concept.getName());
	}

}
