package es.rchavarria.raccount.model;

public class Concept {

    public static final String NAME = "name";
    public static final String VISIBLE = "visible";
    public static final String ID_CONCEPT = "idConcept";

    private long idConcept;
    private String name;
    private boolean visible;

    public long getIdConcept() {
        return idConcept;
    }

    public void setIdConcept(final long idConcept) {
        this.idConcept = idConcept;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Concept)) {
            throw new IllegalArgumentException("Trying to compare to a non Concept object");
        }

        Concept c = (Concept) obj;
        return idConcept == c.getIdConcept() && name.equals(c.getName()) && visible == c.isVisible();
    }
}
