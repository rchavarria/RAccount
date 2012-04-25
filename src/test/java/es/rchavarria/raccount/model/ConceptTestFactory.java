package es.rchavarria.raccount.model;

import java.util.ArrayList;
import java.util.List;

public class ConceptTestFactory {

	public static List<Concept> createList() {
		List<Concept> list = new ArrayList<Concept>();
		list.add(create(1, "concept test 1", true));
		list.add(create(2, "concept test 2", true));
		list.add(create(3, "concept test 2", false));
		return list;
	}

	public static Concept create(final int id, final String name, final boolean visible) {
		Concept c = new Concept();
		c.setIdConcept(id);
		c.setName(name);
		c.setVisible(visible);
		return c;
	}

	public static Concept create() {
		return create(2, "concept test 2", true);
	}
}
